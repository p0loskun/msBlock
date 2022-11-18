package com.github.minersstudios.msblock.listeners.player;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.enums.CustomBlock;
import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.msblock.utils.PlayerUtils;
import com.github.minersstudios.msblock.utils.UseBucketsAndSpawnableItems;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.item.context.ItemActionContext;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Slab;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public class PlayerInteractListener implements Listener {
	private Block blockAtFace;
	private Location interactionPoint;
	private ItemActionContext itemActionContext;
	private EnumHand enumHand;
	private ItemStack itemInHand;
	private Player player;
	private GameMode gameMode;
	private EquipmentSlot hand;
	private net.minecraft.world.item.ItemStack nmsItem;

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
		if (
				event.getClickedBlock() == null
				|| event.getHand() == null
				|| event.getAction() != Action.RIGHT_CLICK_BLOCK
		) return;
		Block clickedBlock = event.getClickedBlock();
		this.hand = event.getHand();
		this.player = event.getPlayer();
		this.gameMode = this.player.getGameMode();
		ItemStack itemInMainHand = this.player.getInventory().getItemInMainHand();

		event.setCancelled(clickedBlock.getType() == Material.NOTE_BLOCK);

		if (PlayerUtils.isItemCustomDecor(itemInMainHand)) return;
		if (this.hand != EquipmentSlot.HAND && PlayerUtils.isItemCustomBlock(itemInMainHand)) {
			this.hand = EquipmentSlot.HAND;
		}
		this.itemInHand = this.player.getInventory().getItem(hand);

		if (
				clickedBlock.getType() == Material.NOTE_BLOCK
				&& !this.itemInHand.getType().isAir()
				&& (hand == EquipmentSlot.HAND || hand == EquipmentSlot.OFF_HAND)
				&& !PlayerUtils.isItemCustomBlock(this.itemInHand)
				&& this.gameMode != GameMode.ADVENTURE
				&& this.gameMode != GameMode.SPECTATOR
		) {
			this.blockAtFace = clickedBlock.getRelative(event.getBlockFace());
			this.nmsItem = CraftItemStack.asNMSCopy(this.itemInHand);
			this.enumHand = this.hand == EquipmentSlot.HAND ? EnumHand.a : EnumHand.b;
			this.interactionPoint = PlayerUtils.getInteractionPoint(this.player.getEyeLocation(), 8);
			this.itemActionContext = PlayerUtils.getItemActionContext(this.player, this.blockAtFace.getLocation(), this.enumHand);
			if (this.interactionPoint != null) {
				useItemInHand(event);
			}
		}
		if (
				PlayerUtils.isItemCustomBlock(this.itemInHand)
				&& (event.getHand() == EquipmentSlot.HAND || this.hand == EquipmentSlot.OFF_HAND)
				&& BlockUtils.REPLACE.contains(clickedBlock.getRelative(event.getBlockFace()).getType())
				&& this.gameMode != GameMode.ADVENTURE
				&& this.gameMode != GameMode.SPECTATOR
		) {
			if (
					((clickedBlock.getType().isInteractable()
					&& !Tag.STAIRS.isTagged(clickedBlock.getType()))
					&& clickedBlock.getType() != Material.NOTE_BLOCK)
					&& !this.player.isSneaking()
			) return;
			Block replaceableBlock =
					BlockUtils.REPLACE.contains(clickedBlock.getType()) ? clickedBlock
							: clickedBlock.getRelative(event.getBlockFace());
			for (Entity nearbyEntity : replaceableBlock.getWorld().getNearbyEntities(replaceableBlock.getLocation().toCenterLocation(), 0.5d, 0.5d, 0.5d)) {
				if (!BlockUtils.IGNORABLE_ENTITIES.contains(nearbyEntity.getType())) return;
			}
			ItemMeta itemMeta = this.itemInHand.getItemMeta();
			if (itemMeta == null || !itemMeta.hasCustomModelData()) return;
			CustomBlock
					.getCustomBlock(itemMeta.getCustomModelData())
					.setCustomBlock(replaceableBlock, this.player, this.hand);
		}
	}

	private void useItemInHand(@Nonnull PlayerInteractEvent event) {
		BlockFace blockFace = event.getBlockFace();
		BlockData materialBlockData = BlockUtils.getBlockDataByMaterial(this.itemInHand.getType());
		if (BlockUtils.SPAWNABLE_ITEMS.contains(this.itemInHand.getType())) {
			new UseBucketsAndSpawnableItems(this.player, this.blockAtFace, blockFace, this.hand);
		}
		if (Tag.SLABS.isTagged(this.itemInHand.getType())) {
			boolean placeDouble = true;
			Material itemMaterial = this.itemInHand.getType();
			if (this.blockAtFace.getType() != itemMaterial) {
				useOn();
				placeDouble = false;
			}
			if (!(this.blockAtFace.getBlockData() instanceof Slab slab)) return;
			if (placeDouble && this.blockAtFace.getType() == itemMaterial) {
				slab.setType(Slab.Type.DOUBLE);
				SoundGroup soundGroup = slab.getSoundGroup();
				this.blockAtFace.getWorld().playSound(
						this.blockAtFace.getLocation(),
						soundGroup.getPlaceSound(),
						SoundCategory.BLOCKS,
						soundGroup.getVolume(),
						soundGroup.getPitch()
				);
				if (this.gameMode == GameMode.SURVIVAL) {
					this.itemInHand.setAmount(this.itemInHand.getAmount() - 1);
				}
			} else if (
					blockFace == BlockFace.DOWN
					|| this.interactionPoint.getY() > 0.5d
					&& this.interactionPoint.getY() < 1.0d
					&& this.blockAtFace.getType() == itemMaterial
			) {
				slab.setType(Slab.Type.TOP);
			} else if (
					blockFace == BlockFace.UP
					|| this.interactionPoint.getY() < 0.5d
					&& this.interactionPoint.getY() > 0.0d
					&& this.blockAtFace.getType() == itemMaterial
			) {
				slab.setType(Slab.Type.BOTTOM);
			}
			this.blockAtFace.setBlockData(slab);
		}
		if (!BlockUtils.REPLACE.contains(this.blockAtFace.getType())) return;
		if (materialBlockData instanceof FaceAttachable) {
			useOn();
			FaceAttachable faceAttachable = (FaceAttachable) this.blockAtFace.getBlockData();
			switch (blockFace) {
				case UP -> faceAttachable.setAttachedFace(FaceAttachable.AttachedFace.FLOOR);
				case DOWN -> faceAttachable.setAttachedFace(FaceAttachable.AttachedFace.CEILING);
				default -> faceAttachable.setAttachedFace(FaceAttachable.AttachedFace.WALL);
			}
			if (faceAttachable instanceof Directional directional && faceAttachable.getAttachedFace() == FaceAttachable.AttachedFace.WALL) {
				directional.setFacing(blockFace);
				this.blockAtFace.setBlockData(directional);
				return;
			}
			this.blockAtFace.setBlockData(faceAttachable);
		} else if (materialBlockData instanceof Orientable) {
			useOn();
			if (!(this.blockAtFace.getBlockData() instanceof Orientable orientable)) return;
			orientable.setAxis(this.getAxis());
			this.blockAtFace.setBlockData(orientable);
		} else if (
				materialBlockData instanceof Directional directionalMaterial
				&& (
						directionalMaterial.getFaces().contains(blockFace)
						|| Tag.STAIRS.isTagged(itemInHand.getType())
						|| Tag.TRAPDOORS.isTagged(itemInHand.getType())
				) && !BlockUtils.IGNORABLE_MATERIALS.contains(itemInHand.getType())
		) {
			useOn();
			if (!(this.blockAtFace.getBlockData() instanceof Directional directional)) return;
			if (!(directional instanceof Bisected bisected)) {
				directional.setFacing(blockFace);
			} else {
				bisected.setHalf(
						this.interactionPoint.getY() == 1.0d
						|| this.interactionPoint.getY() < 0.5d
						&& this.interactionPoint.getY() > 0.0d
						? Bisected.Half.BOTTOM
						: Bisected.Half.TOP
				);
				this.blockAtFace.setBlockData(bisected);
				return;
			}
			this.blockAtFace.setBlockData(directional);
		} else if (!this.blockAtFace.getType().isSolid() && this.blockAtFace.getType() != this.itemInHand.getType()) {
			useOn();
		}
	}

	private void useOn() {
		if (
				this.nmsItem.useOn(this.itemActionContext, this.enumHand) == EnumInteractionResult.e
				|| !this.itemInHand.getType().isBlock()
		) return;
		BlockData blockData = this.itemInHand.getType().createBlockData();
		Main.getCoreProtectAPI().logPlacement(this.player.getName(), this.blockAtFace.getLocation(), this.itemInHand.getType(), blockData);
		SoundGroup soundGroup = blockData.getSoundGroup();
		this.blockAtFace.getWorld().playSound(
				this.blockAtFace.getLocation(),
				soundGroup.getPlaceSound(),
				SoundCategory.BLOCKS,
				soundGroup.getVolume(),
				soundGroup.getPitch()
		);
	}

	@Nonnull
	private Axis getAxis() {
		if (this.interactionPoint.getX() == 0.0 || this.interactionPoint.getX() == 1.0) {
			return Axis.X;
		} else if (this.interactionPoint.getY() == 0.0 || this.interactionPoint.getY() == 1.0) {
			return Axis.Y;
		}
		return Axis.Z;
	}
}
