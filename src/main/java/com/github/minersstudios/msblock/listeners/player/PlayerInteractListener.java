package com.github.minersstudios.msblock.listeners.player;

import com.github.minersstudios.msblock.MSBlock;
import com.github.minersstudios.msblock.customblock.CustomBlock;
import com.github.minersstudios.msblock.customblock.CustomBlockData;
import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.msblock.utils.PlayerUtils;
import com.github.minersstudios.msblock.utils.UseBucketsAndSpawnableItems;
import com.github.minersstudios.mscore.MSListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.data.type.Slab;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack;
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
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@MSListener
public class PlayerInteractListener implements Listener {
	private Block blockAtFace;
	private Location interactionPoint;
	private UseOnContext useOnContext;
	private InteractionHand interactionHand;
	private ItemStack itemInHand;
	private Player player;
	private GameMode gameMode;
	private net.minecraft.world.item.ItemStack nmsItem;
	private CustomBlockData clickedCustomBlock;

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteract(@NotNull PlayerInteractEvent event) {
		if (
				event.getClickedBlock() == null
				|| event.getHand() == null
				|| event.getAction() != Action.RIGHT_CLICK_BLOCK
		) return;
		Block clickedBlock = event.getClickedBlock();
		BlockFace blockFace = event.getBlockFace();
		EquipmentSlot hand = event.getHand();
		this.player = event.getPlayer();
		this.gameMode = this.player.getGameMode();
		ItemStack itemInMainHand = this.player.getInventory().getItemInMainHand();

		if (
				clickedBlock.getType() == Material.NOTE_BLOCK
				|| PlayerUtils.isItemCustomBlock(event.getPlayer().getInventory().getItemInMainHand())
		) {
			event.setCancelled(true);
		}

		if (PlayerUtils.isItemCustomDecor(itemInMainHand)) return;
		if (hand != EquipmentSlot.HAND && PlayerUtils.isItemCustomBlock(itemInMainHand)) {
			hand = EquipmentSlot.HAND;
		}
		this.itemInHand = this.player.getInventory().getItem(hand);
		this.interactionPoint = PlayerUtils.getInteractionPoint(this.player.getEyeLocation(), 8);

		if (
				clickedBlock.getBlockData() instanceof NoteBlock noteBlock
				&& !this.itemInHand.getType().isAir()
				&& (hand == EquipmentSlot.HAND || hand == EquipmentSlot.OFF_HAND)
				&& !PlayerUtils.isItemCustomBlock(this.itemInHand)
				&& this.gameMode != GameMode.ADVENTURE
				&& this.gameMode != GameMode.SPECTATOR
		) {

			this.clickedCustomBlock = BlockUtils.getCustomBlock(noteBlock.getInstrument(), noteBlock.getNote(), noteBlock.isPowered());
			this.blockAtFace = clickedBlock.getRelative(blockFace);
			this.nmsItem = CraftItemStack.asNMSCopy(this.itemInHand);
			this.interactionHand =
					hand == EquipmentSlot.HAND
					? InteractionHand.MAIN_HAND
					: InteractionHand.OFF_HAND;
			this.useOnContext = PlayerUtils.getUseOnContext(this.player, this.blockAtFace.getLocation(), this.interactionHand);
			if (this.interactionPoint != null) {
				useItemInHand(event);
			}
		}

		if (
				PlayerUtils.isItemCustomBlock(this.itemInHand)
				&& (event.getHand() == EquipmentSlot.HAND || hand == EquipmentSlot.OFF_HAND)
				&& BlockUtils.REPLACE.contains(clickedBlock.getRelative(blockFace).getType())
				&& this.gameMode != GameMode.ADVENTURE
				&& this.gameMode != GameMode.SPECTATOR
				&& this.interactionPoint != null
		) {
			if (
					((clickedBlock.getType().isInteractable()
					&& !Tag.STAIRS.isTagged(clickedBlock.getType()))
					&& clickedBlock.getType() != Material.NOTE_BLOCK)
					&& !this.player.isSneaking()
			) return;
			Block replaceableBlock =
					BlockUtils.REPLACE.contains(clickedBlock.getType()) ? clickedBlock
							: clickedBlock.getRelative(blockFace);
			for (Entity nearbyEntity : replaceableBlock.getWorld().getNearbyEntities(replaceableBlock.getLocation().toCenterLocation(), 0.5d, 0.5d, 0.5d)) {
				if (!BlockUtils.IGNORABLE_ENTITIES.contains(nearbyEntity.getType())) return;
			}
			ItemMeta itemMeta = this.itemInHand.getItemMeta();
			if (itemMeta == null || !itemMeta.hasCustomModelData()) return;
			CustomBlockData customBlockData = BlockUtils.getCustomBlock(itemMeta.getCustomModelData());
			Set<BlockFace> blockFaces = customBlockData.getFaces();
			Set<Axis> blockAxes = customBlockData.getAxes();
			CustomBlock customBlock = new CustomBlock(replaceableBlock, this.player, customBlockData);
			if (blockFaces != null) {
				if (customBlockData.getPlacingType() == CustomBlockData.PlacingType.BY_BLOCK_FACE) {
					customBlock.setCustomBlock(hand, blockFace, null);
				} else if (customBlockData.getPlacingType() == CustomBlockData.PlacingType.BY_EYE_POSITION) {
					customBlock.setCustomBlock(hand, this.getBlockFaceByEyes(blockFaces), null);
				}
			} else if (blockAxes != null) {
				if (customBlockData.getPlacingType() == CustomBlockData.PlacingType.BY_BLOCK_FACE) {
					customBlock.setCustomBlock(hand, null, this.getAxis());
				} else if (customBlockData.getPlacingType() == CustomBlockData.PlacingType.BY_EYE_POSITION) {
					customBlock.setCustomBlock(hand, null, this.getAxisByEyes(blockAxes));
				}
			} else {
				customBlock.setCustomBlock(hand);
			}
		}
	}

	private void useItemInHand(@NotNull PlayerInteractEvent event) {
		BlockFace blockFace = event.getBlockFace();
		BlockData materialBlockData = BlockUtils.getBlockDataByMaterial(this.itemInHand.getType());
		if (BlockUtils.SPAWNABLE_ITEMS.contains(this.itemInHand.getType())) {
			new UseBucketsAndSpawnableItems(this.player, this.blockAtFace, blockFace, this.itemInHand);
		} else if (Tag.SLABS.isTagged(this.itemInHand.getType())) {
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

		Set<Material> placeableMaterials = this.clickedCustomBlock.getPlaceableMaterials();
		if (placeableMaterials != null && placeableMaterials.contains(this.itemInHand.getType())) {
			blockAtFace.setType(this.itemInHand.getType());
		}
	}

	private void useOn() {
		if (
				this.nmsItem.useOn(this.useOnContext, this.interactionHand) == InteractionResult.FAIL
				|| !this.itemInHand.getType().isBlock()
		) return;
		BlockData blockData = this.itemInHand.getType().createBlockData();
		MSBlock.getCoreProtectAPI().logPlacement(this.player.getName(), this.blockAtFace.getLocation(), this.itemInHand.getType(), blockData);
		SoundGroup soundGroup = blockData.getSoundGroup();
		this.blockAtFace.getWorld().playSound(
				this.blockAtFace.getLocation(),
				soundGroup.getPlaceSound(),
				SoundCategory.BLOCKS,
				soundGroup.getVolume(),
				soundGroup.getPitch()
		);
	}

	private @NotNull Axis getAxis() {
		if (this.interactionPoint.getX() == 0.0 || this.interactionPoint.getX() == 1.0) {
			return Axis.X;
		} else if (this.interactionPoint.getY() == 0.0 || this.interactionPoint.getY() == 1.0) {
			return Axis.Y;
		}
		return Axis.Z;
	}

	private BlockFace getBlockFaceByEyes(@NotNull Set<BlockFace> blockFaces) {
		float pitch = this.player.getLocation().getPitch();
		if (!(pitch >= -45.0f) && blockFaces.contains(BlockFace.DOWN)) {
			return BlockFace.DOWN;
		} else if (!(pitch <= 45.0f) && blockFaces.contains(BlockFace.UP)) {
			return BlockFace.UP;
		}
		return getBlockFaceByYaw();
	}

	private BlockFace getBlockFaceByYaw() {
		BlockFace[] faces = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
		return faces[Math.round(this.player.getLocation().getYaw() / 90f) & 0x3];
	}

	private Axis getAxisByEyes(@NotNull Set<Axis> axes) {
		float pitch = this.player.getLocation().getPitch();
		if (!(pitch >= -45.0f && pitch <= 45.0f) && axes.contains(Axis.Y)) {
			return Axis.Y;
		}
		return switch (this.getBlockFaceByYaw()) {
			case NORTH, SOUTH -> Axis.X;
			default -> Axis.Z;
		};
	}
}
