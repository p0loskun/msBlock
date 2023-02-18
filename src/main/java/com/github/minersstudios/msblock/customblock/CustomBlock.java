package com.github.minersstudios.msblock.customblock;

import com.github.minersstudios.msblock.MSBlock;
import com.github.minersstudios.msblock.events.CustomBlockBreakEvent;
import com.github.minersstudios.msblock.events.CustomBlockPlaceEvent;
import com.github.minersstudios.msblock.utils.BlockUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.craftbukkit.v1_19_R2.block.CraftBlock;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CustomBlock {
	private final @NotNull Block block;
	private final @NotNull Player player;
	private final @NotNull CustomBlockData customBlockData;

	public CustomBlock(
			@NotNull Block block,
			@NotNull Player player,
			@NotNull CustomBlockData customBlockData
	) {
		this.block = block;
		this.player = player;
		this.customBlockData = customBlockData;
	}

	public void setCustomBlock(@NotNull EquipmentSlot hand) {
		this.setCustomBlock(hand, null, null);
	}

	public void setCustomBlock(@NotNull EquipmentSlot hand, @Nullable BlockFace blockFace, @Nullable Axis axis) {
		CustomBlockPlaceEvent customBlockPlaceEvent = new CustomBlockPlaceEvent(this, this.block.getState(), this.player, hand);
		Bukkit.getPluginManager().callEvent(customBlockPlaceEvent);
		if (customBlockPlaceEvent.isCancelled()) return;

		Map<BlockFace, NoteBlockData> blockFaceMap = this.customBlockData.getBlockFaceMap();
		Map<Axis, NoteBlockData> blockAxisMap = this.customBlockData.getBlockAxisMap();
		if (blockFace != null && blockFaceMap != null) {
			this.customBlockData.setNoteBlockData(blockFaceMap.get(blockFace));
		} else if (axis != null && blockAxisMap != null) {
			this.customBlockData.setNoteBlockData(blockAxisMap.get(axis));
		}
		if (this.customBlockData.getNoteBlockData() == null) return;
		Bukkit.getScheduler().runTask(MSBlock.getInstance(), () -> {
			this.block.setType(Material.NOTE_BLOCK);
			NoteBlock noteBlock = this.customBlockData.getNoteBlockData().craftNoteBlock(this.block.getBlockData());
			this.block.setBlockData(noteBlock);

			this.customBlockData.getSoundGroup().playPlaceSound(this.block.getLocation().toCenterLocation());
			MSBlock.getCoreProtectAPI().logPlacement(this.player.getName(), this.block.getLocation(), Material.NOTE_BLOCK, noteBlock);
			BlockUtils.removeBlocksAround(this.block);
			this.player.swingHand(hand);

			if (this.player.getGameMode() != GameMode.SURVIVAL) return;
			ItemStack itemInHand = this.player.getInventory().getItem(hand);
			itemInHand.setAmount(itemInHand.getAmount() - 1);
		});
	}

	public void breakCustomBlock() {
		CustomBlockBreakEvent customBlockBreakEvent = new CustomBlockBreakEvent(this, this.player);
		Bukkit.getPluginManager().callEvent(customBlockBreakEvent);
		if (customBlockBreakEvent.isCancelled()) return;

		Location blockLocation = this.block.getLocation();
		World world = this.block.getWorld();
		ItemStack itemInMainHand = this.player.getInventory().getItemInMainHand();

		if (BlockUtils.getEntryByBlock(this.block) == null) return;
		Bukkit.getScheduler().runTask(MSBlock.getInstance(), () -> BlockUtils.cancelAllTasksWithThisBlock(this.block));
		this.customBlockData.getSoundGroup().playBreakSound(this.block.getLocation().toCenterLocation());
		CraftBlock craftBlock = (CraftBlock) this.block;
		craftBlock.getHandle().levelEvent(2001, craftBlock.getPosition(), net.minecraft.world.level.block.Block.getId(craftBlock.getNMS()));
		MSBlock.getCoreProtectAPI().logRemoval(this.player.getName(), blockLocation, Material.NOTE_BLOCK, this.block.getBlockData());
		this.block.setType(Material.AIR);

		if (
				(!this.customBlockData.isForceTool() || this.customBlockData.getToolType() == ToolType.getToolType(itemInMainHand))
				&& this.customBlockData != CustomBlockData.DEFAULT
		) {
			if (this.customBlockData.isDropsDefaultItem()) {
				world.dropItemNaturally(blockLocation, this.customBlockData.craftItemStack());
			}
			if (this.customBlockData.getExpToDrop() != 0) {
				world.spawn(blockLocation, ExperienceOrb.class).setExperience(this.customBlockData.getExpToDrop());
			}
		} else if (this.customBlockData == CustomBlockData.DEFAULT) {
			world.dropItemNaturally(blockLocation, new ItemStack(Material.NOTE_BLOCK));
		}

		if (
				ToolType.getToolType(itemInMainHand) != ToolType.HAND
				&& itemInMainHand.getItemMeta() instanceof Damageable handItemDamageable
		) {
			handItemDamageable.setDamage(handItemDamageable.getDamage() + 1);
			itemInMainHand.setItemMeta(handItemDamageable);
			if (handItemDamageable.getDamage() > itemInMainHand.getType().getMaxDurability()) {
				itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
				world.playSound(this.player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
			}
		}
	}

	public @NotNull Block getBlock() {
		return this.block;
	}

	public @NotNull Player getPlayer() {
		return this.player;
	}

	public @NotNull CustomBlockData getCustomBlockData() {
		return this.customBlockData;
	}
}
