package com.github.minersstudios.msblock.listeners.player;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.enums.CustomBlock;
import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.msblock.utils.PlayerUtils;
import com.github.minersstudios.msblock.utils.UseBucketsAndSpawnableItems;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.context.ItemActionContext;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.type.Slab;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
	private static Block blockAtFace;
	private static Location interactionPoint;
	private static ItemActionContext itemActionContext;
	private static EnumHand enumHand;
	private static ItemStack itemInHand;
	private static Player player;
	private static GameMode gameMode;
	private static EquipmentSlot hand;
	private static net.minecraft.world.item.ItemStack nmsItem;

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
		if (event.getClickedBlock() == null || event.getHand() == null) return;
		Block clickedBlock = event.getClickedBlock();
		hand = event.getHand();
		player = event.getPlayer();
		gameMode = player.getGameMode();
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
		event.setCancelled(event.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock.getType() == Material.NOTE_BLOCK);
		if (PlayerUtils.isItemCustomDecor(itemInMainHand)) return;
		if (hand != EquipmentSlot.HAND && PlayerUtils.isItemCustomBlock(itemInMainHand)) {
			hand = EquipmentSlot.HAND;
		}
		itemInHand = player.getInventory().getItem(hand);
		if (
				event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& clickedBlock.getType() == Material.NOTE_BLOCK
				&& !itemInHand.getType().isAir()
				&& (event.getHand() == EquipmentSlot.HAND || hand == EquipmentSlot.OFF_HAND)
				&& !PlayerUtils.isItemCustomBlock(itemInHand)
				&& gameMode != GameMode.ADVENTURE
				&& gameMode != GameMode.SPECTATOR
		) {
			blockAtFace = clickedBlock.getRelative(event.getBlockFace());
			for (Entity nearbyEntity : clickedBlock.getWorld().getNearbyEntities(blockAtFace.getLocation().clone().toCenterLocation(), 0.5d, 0.5d, 0.5d)) {
				if (nearbyEntity.getType() != EntityType.DROPPED_ITEM && itemInHand.getType().isSolid() && !Tag.DOORS.isTagged(itemInHand.getType())) return;
			}
			nmsItem = CraftItemStack.asNMSCopy(itemInHand);
			enumHand = hand == EquipmentSlot.HAND ? EnumHand.a : EnumHand.b;
			interactionPoint = PlayerUtils.getInteractionPoint(player.getEyeLocation(), 8);
			itemActionContext = new ItemActionContext(
					((CraftPlayer) player).getHandle(),
					enumHand,
					PlayerUtils.getMovingObjectPositionBlock(player, blockAtFace.getLocation())
			);
			if (interactionPoint != null) {
				useItemInHand(event);
			}
		}
		if (
				event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& PlayerUtils.isItemCustomBlock(itemInHand)
				&& gameMode != GameMode.ADVENTURE
				&& gameMode != GameMode.SPECTATOR
				&& (event.getHand() == EquipmentSlot.HAND || hand == EquipmentSlot.OFF_HAND)
				&& BlockUtils.REPLACE.contains(clickedBlock.getRelative(event.getBlockFace()).getType())
		) {
			if (
					((clickedBlock.getType().isInteractable()
					&& !Tag.STAIRS.isTagged(clickedBlock.getType()))
					&& clickedBlock.getType() != Material.NOTE_BLOCK)
					&& !player.isSneaking()
			) return;
			Block replaceableBlock =
					BlockUtils.REPLACE.contains(clickedBlock.getType()) ? clickedBlock
							: clickedBlock.getRelative(event.getBlockFace());
			for (Entity nearbyEntity : replaceableBlock.getWorld().getNearbyEntities(replaceableBlock.getLocation().toCenterLocation(), 0.5d, 0.5d, 0.5d)) {
				if (nearbyEntity.getType() != EntityType.DROPPED_ITEM && nearbyEntity.getType() != EntityType.ITEM_FRAME) return;
			}
			ItemMeta itemMeta = itemInHand.getItemMeta();
			if (itemMeta == null || !itemMeta.hasCustomModelData()) return;
			CustomBlock.getCustomBlock(itemMeta.getCustomModelData()).setCustomBlock(replaceableBlock, player, hand);
		}
	}

	private static void useItemInHand(@Nonnull PlayerInteractEvent event) {
		BlockFace blockFace = event.getBlockFace();
		BlockData materialBlockData = BlockUtils.getBlockDataByMaterial(itemInHand.getType());
		if (BlockUtils.BUCKETS_AND_SPAWNABLE_ITEMS.contains(itemInHand.getType())) {
			new UseBucketsAndSpawnableItems(player, blockAtFace, blockFace, hand);
		}
		if (Tag.SLABS.isTagged(itemInHand.getType())) {
			boolean placeDouble = true;
			Material itemMaterial = itemInHand.getType();
			if (blockAtFace.getType() != itemMaterial) {
				useOn();
				placeDouble = false;
			}
			if (!(blockAtFace.getBlockData() instanceof Slab slab)) return;
			if (placeDouble && blockAtFace.getType() == itemMaterial) {
				slab.setType(Slab.Type.DOUBLE);
				blockAtFace.getWorld().playSound(
						blockAtFace.getLocation(),
						slab.getSoundGroup().getPlaceSound(),
						SoundCategory.BLOCKS,
						slab.getSoundGroup().getVolume(),
						slab.getSoundGroup().getPitch()
				);
				if (gameMode == GameMode.SURVIVAL) {
					itemInHand.setAmount(itemInHand.getAmount() - 1);
				}
			} else if (
					blockFace == BlockFace.DOWN
							|| interactionPoint.getY() > 0.5d
							&& interactionPoint.getY() < 1.0d
							&& blockAtFace.getType() == itemMaterial
			) {
				slab.setType(Slab.Type.TOP);
			} else if (
					blockFace == BlockFace.UP
							|| interactionPoint.getY() < 0.5d
							&& interactionPoint.getY() > 0.0d
							&& blockAtFace.getType() == itemMaterial
			) {
				slab.setType(Slab.Type.BOTTOM);
			}
			blockAtFace.setBlockData(slab);
		}
		if (!BlockUtils.REPLACE.contains(blockAtFace.getType())) return;
		if (materialBlockData instanceof FaceAttachable) {
			useOn();
			FaceAttachable faceAttachable = (FaceAttachable) blockAtFace.getBlockData();
			switch (blockFace) {
				case UP -> faceAttachable.setAttachedFace(FaceAttachable.AttachedFace.FLOOR);
				case DOWN -> faceAttachable.setAttachedFace(FaceAttachable.AttachedFace.CEILING);
				default -> faceAttachable.setAttachedFace(FaceAttachable.AttachedFace.WALL);
			}
			if (faceAttachable instanceof Directional directional && faceAttachable.getAttachedFace() == FaceAttachable.AttachedFace.WALL) {
				directional.setFacing(blockFace);
				blockAtFace.setBlockData(directional);
				return;
			}
			blockAtFace.setBlockData(faceAttachable);
		} else if (
				materialBlockData instanceof Directional directionalMaterial
				&& (directionalMaterial.getFaces().contains(blockFace) || Tag.STAIRS.isTagged(itemInHand.getType()) || Tag.TRAPDOORS.isTagged(itemInHand.getType()))
				&& !BlockUtils.IGNORABLE_MATERIALS.contains(itemInHand.getType())
		) {
			useOn();
			if (!(blockAtFace.getBlockData() instanceof Directional directional)) return;
			if (!(directional instanceof Bisected bisected)) {
				directional.setFacing(blockFace);
			} else {
				bisected.setHalf(
						blockFace == BlockFace.UP ? Bisected.Half.BOTTOM
						: blockFace == BlockFace.DOWN ? Bisected.Half.TOP
						: interactionPoint.getY() < 0.5d && interactionPoint.getY() >= 0.0d ? Bisected.Half.BOTTOM
						: Bisected.Half.TOP
				);
				blockAtFace.setBlockData(bisected);
				return;
			}
			blockAtFace.setBlockData(directional);
		} else if (!blockAtFace.getType().isSolid() && blockAtFace.getType() != itemInHand.getType()) {
			useOn();
		}
	}

	private static void useOn() {
		nmsItem.useOn(itemActionContext, enumHand);
		if (!itemInHand.getType().isBlock()) return;
		BlockData blockData = blockAtFace.getBlockData();
		Main.getCoreProtectAPI().logPlacement(player.getName(), blockAtFace.getLocation(), itemInHand.getType(), blockData);
		SoundGroup soundGroup = blockData.getSoundGroup();
		blockAtFace.getWorld().playSound(
				blockAtFace.getLocation(),
				soundGroup.getPlaceSound(),
				SoundCategory.BLOCKS,
				soundGroup.getVolume(),
				soundGroup.getPitch()
		);
	}
}
