package com.github.minersstudios.msblock.utils;

import com.github.minersstudios.mscore.collections.ConcurrentDualMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R2.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R2.event.CraftEventFactory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BlockUtils {
	public static final ConcurrentDualMap<Block, Player, Integer> blocks = new ConcurrentDualMap<>();

	public static final ImmutableSet<Material> REPLACE = Sets.immutableEnumSet(
			//<editor-fold desc="Replace materials">
			Material.AIR,
			Material.CAVE_AIR,
			Material.VOID_AIR,
			Material.WATER,
			Material.LAVA,
			Material.GRASS,
			Material.FERN,
			Material.SEAGRASS,
			Material.TALL_GRASS,
			Material.LARGE_FERN,
			Material.TALL_SEAGRASS,
			Material.VINE,
			Material.SNOW,
			Material.FIRE
			//</editor-fold>
	);

	public static final ImmutableSet<InventoryType> IGNORABLE_INVENTORY_TYPES = Sets.immutableEnumSet(
			//<editor-fold desc="Ignorable inventory types">
			InventoryType.CARTOGRAPHY,
			InventoryType.BREWING,
			InventoryType.BEACON,
			InventoryType.BLAST_FURNACE,
			InventoryType.FURNACE,
			InventoryType.SMOKER,
			InventoryType.GRINDSTONE,
			InventoryType.STONECUTTER,
			InventoryType.SMITHING,
			InventoryType.LOOM,
			InventoryType.MERCHANT,
			InventoryType.ENCHANTING
			//</editor-fold>
	);

	public static final ImmutableSet<EntityType> IGNORABLE_ENTITIES = Sets.immutableEnumSet(
			//<editor-fold desc="Entities to be ignored when placing a block on their location">
			EntityType.DROPPED_ITEM,
			EntityType.ITEM_FRAME,
			EntityType.GLOW_ITEM_FRAME,
			EntityType.LIGHTNING,
			EntityType.LLAMA_SPIT,
			EntityType.EXPERIENCE_ORB,
			EntityType.THROWN_EXP_BOTTLE,
			EntityType.EGG,
			EntityType.SPLASH_POTION,
			EntityType.FIREWORK,
			EntityType.FIREBALL,
			EntityType.FISHING_HOOK,
			EntityType.SMALL_FIREBALL,
			EntityType.SNOWBALL,
			EntityType.TRIDENT,
			EntityType.WITHER_SKULL,
			EntityType.DRAGON_FIREBALL,
			EntityType.AREA_EFFECT_CLOUD,
			EntityType.ARROW,
			EntityType.SPECTRAL_ARROW,
			EntityType.ENDER_PEARL,
			EntityType.EVOKER_FANGS,
			EntityType.LEASH_HITCH
			//</editor-fold>
	);

	public static final ImmutableSet<Material> IGNORABLE_MATERIALS = Sets.immutableEnumSet(
			//<editor-fold desc="Ignorable materials">
			Material.ANVIL,
			Material.CHIPPED_ANVIL,
			Material.DAMAGED_ANVIL,
			Material.LECTERN,
			Material.HOPPER,
			Material.DISPENSER,
			Material.DROPPER,
			Material.OBSERVER,
			Material.PISTON,
			Material.STICKY_PISTON,
			Material.COMPARATOR,
			Material.REPEATER,
			Material.WHITE_GLAZED_TERRACOTTA,
			Material.ORANGE_GLAZED_TERRACOTTA,
			Material.MAGENTA_GLAZED_TERRACOTTA,
			Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
			Material.YELLOW_GLAZED_TERRACOTTA,
			Material.LIME_GLAZED_TERRACOTTA,
			Material.PINK_GLAZED_TERRACOTTA,
			Material.GRAY_GLAZED_TERRACOTTA,
			Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
			Material.CYAN_GLAZED_TERRACOTTA,
			Material.PURPLE_GLAZED_TERRACOTTA,
			Material.BLUE_GLAZED_TERRACOTTA,
			Material.BROWN_GLAZED_TERRACOTTA,
			Material.GREEN_GLAZED_TERRACOTTA,
			Material.RED_GLAZED_TERRACOTTA,
			Material.BLACK_GLAZED_TERRACOTTA,
			Material.WHITE_BED,
			Material.ORANGE_BED,
			Material.MAGENTA_BED,
			Material.LIGHT_BLUE_BED,
			Material.YELLOW_BED,
			Material.LIME_BED,
			Material.PINK_BED,
			Material.GRAY_BED,
			Material.LIGHT_GRAY_BED,
			Material.CYAN_BED,
			Material.PURPLE_BED,
			Material.BLUE_BED,
			Material.BROWN_BED,
			Material.GREEN_BED,
			Material.RED_BED,
			Material.BLACK_BED,
			Material.STONECUTTER,
			Material.CHEST,
			Material.TRAPPED_CHEST,
			Material.ENDER_CHEST,
			Material.BARREL,
			Material.BLAST_FURNACE,
			Material.SMOKER,
			Material.LOOM,
			Material.FURNACE,
			Material.BEEHIVE,
			Material.BEE_NEST,
			Material.END_PORTAL_FRAME
			//</editor-fold>
	);

	private static final ImmutableSet<Material> BREAK_ON_BLOCK_PLACE = Sets.immutableEnumSet(
			//<editor-fold desc="Materials that will break on block place">
			Material.TALL_GRASS,
			Material.LARGE_FERN,
			Material.TALL_SEAGRASS
			//</editor-fold>
	);

	public static final ImmutableSet<Material> SPAWNABLE_ITEMS = Sets.immutableEnumSet(
			//<editor-fold desc="Non-block buckets and spawnable items">
			Material.BUCKET,
			Material.LAVA_BUCKET,
			Material.WATER_BUCKET,
			Material.AXOLOTL_BUCKET,
			Material.TROPICAL_FISH_BUCKET,
			Material.COD_BUCKET,
			Material.SALMON_BUCKET,
			Material.PUFFERFISH_BUCKET,
			Material.TADPOLE_BUCKET,
			Material.PAINTING,
			Material.ITEM_FRAME,
			Material.GLOW_ITEM_FRAME,
			Material.OAK_BOAT,
			Material.SPRUCE_BOAT,
			Material.BIRCH_BOAT,
			Material.JUNGLE_BOAT,
			Material.ACACIA_BOAT,
			Material.DARK_OAK_BOAT,
			Material.MANGROVE_BOAT,
			Material.OAK_CHEST_BOAT,
			Material.SPRUCE_CHEST_BOAT,
			Material.BIRCH_CHEST_BOAT,
			Material.JUNGLE_CHEST_BOAT,
			Material.ACACIA_CHEST_BOAT,
			Material.DARK_OAK_CHEST_BOAT,
			Material.MANGROVE_CHEST_BOAT
			//</editor-fold>
	);

	private static final SoundGroup WOOD_SOUND_GROUP = Material.OAK_FENCE.createBlockData().getSoundGroup();

	private BlockUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Updates the note block and checks if there is a notes block above it
	 *
	 * @param block block which will be updated
	 */
	public static void updateNoteBlock(@NotNull Block block) {
		Block nextBlock = block.getRelative(BlockFace.UP);
		if (block.getType() == Material.NOTE_BLOCK) {
			block.getState().update(true, false);
		}
		if (nextBlock.getType() == Material.NOTE_BLOCK) {
			updateNoteBlock(nextBlock);
		}
	}

	/**
	 * Breaks top/bottom block
	 *
	 * @param centreBlock block around which the blocks break
	 */
	public static void removeBlocksAround(@NotNull Block centreBlock) {
		CraftBlock topBlock = (CraftBlock) centreBlock.getRelative(BlockFace.UP);
		CraftBlock bottomBlock = (CraftBlock) centreBlock.getRelative(BlockFace.DOWN);
		if (BREAK_ON_BLOCK_PLACE.contains(topBlock.getType())) {
			topBlock.getHandle().destroyBlock(topBlock.getPosition(), true);
		}
		if (BREAK_ON_BLOCK_PLACE.contains(bottomBlock.getType())) {
			bottomBlock.getHandle().destroyBlock(bottomBlock.getPosition(), true);
		}
	}

	public static boolean destroyBlock(@NotNull ServerPlayerGameMode serverPlayerGameMode, @NotNull ServerPlayer serverPlayer, @NotNull BlockPos pos) {
		BlockState iBlockData = serverPlayerGameMode.level.getBlockState(pos);
		Block bblock = CraftBlock.at(serverPlayerGameMode.level, pos);
		BlockBreakEvent event = new BlockBreakEvent(bblock, serverPlayer.getBukkitEntity());
		boolean isSwordNoBreak = !serverPlayer.getMainHandItem().getItem().canAttackBlock(iBlockData, serverPlayerGameMode.level, pos, serverPlayer);

		if (serverPlayerGameMode.level.getBlockEntity(pos) == null && !isSwordNoBreak) {
			serverPlayer.connection.send(new ClientboundBlockUpdatePacket(pos, Blocks.AIR.defaultBlockState()));
		}

		event.setCancelled(isSwordNoBreak);
		BlockState nmsData = serverPlayerGameMode.level.getBlockState(pos);
		net.minecraft.world.level.block.Block nmsBlock = nmsData.getBlock();
		net.minecraft.world.item.ItemStack itemInMainHand = serverPlayer.getItemBySlot(EquipmentSlot.MAINHAND);

		if (
				serverPlayer.isCreative()
				|| bblock.getType() == Material.NOTE_BLOCK
		) {
			event.setDropItems(false);
		}

		if (
				!event.isCancelled()
				&& !serverPlayerGameMode.isCreative()
				&& serverPlayer.hasCorrectToolForDrops(nmsBlock.defaultBlockState())
		) {
			event.setExpToDrop(nmsBlock.getExpDrop(nmsData, serverPlayerGameMode.level, pos, itemInMainHand, true));
		}

		if (event.isCancelled()) {
			if (isSwordNoBreak) return false;

			serverPlayer.connection.send(new ClientboundBlockUpdatePacket(serverPlayerGameMode.level, pos));

			for (Direction dir : Direction.values()) {
				serverPlayer.connection.send(new ClientboundBlockUpdatePacket(serverPlayerGameMode.level, pos.relative(dir)));
			}

			if (!serverPlayerGameMode.captureSentBlockEntities) {
				BlockEntity tileEntity = serverPlayerGameMode.level.getBlockEntity(pos);
				if (tileEntity != null) {
					serverPlayer.connection.send(Objects.requireNonNull(tileEntity.getUpdatePacket()));
				}
			} else {
				serverPlayerGameMode.capturedBlockEntity = true;
			}

			return false;
		}


		iBlockData = serverPlayerGameMode.level.getBlockState(pos);
		if (iBlockData.isAir()) {
			return false;
		} else {
			BlockEntity tileEntity = serverPlayerGameMode.level.getBlockEntity(pos);
			net.minecraft.world.level.block.Block block = iBlockData.getBlock();
			if (
					!(block instanceof GameMasterBlock)
					|| serverPlayer.canUseGameMasterBlocks()
					|| block instanceof CommandBlock && serverPlayer.isCreative()
					&& serverPlayer.getBukkitEntity().hasPermission("minecraft.commandblock")
			) {
				if (serverPlayer.blockActionRestricted(serverPlayerGameMode.level, pos, serverPlayerGameMode.getGameModeForPlayer())) {
					return false;
				} else {
					org.bukkit.block.BlockState state = bblock.getState();
					serverPlayerGameMode.level.captureDrops = new ArrayList<>();
					block.playerWillDestroy(serverPlayerGameMode.level, pos, iBlockData, serverPlayer);

					boolean flag = serverPlayerGameMode.level.removeBlock(pos, false);
					if (flag) {
						block.destroy(serverPlayerGameMode.level, pos, iBlockData);
					}

					net.minecraft.world.item.ItemStack mainHandStack = null;
					boolean isCorrectTool = false;
					if (!serverPlayerGameMode.isCreative()) {
						net.minecraft.world.item.ItemStack itemStack = serverPlayer.getMainHandItem();
						net.minecraft.world.item.ItemStack itemStack1 = itemStack.copy();
						boolean flag1 = serverPlayer.hasCorrectToolForDrops(iBlockData);
						mainHandStack = itemStack1;
						isCorrectTool = flag1;

						itemStack.mineBlock(serverPlayerGameMode.level, iBlockData, pos, serverPlayer);
						if (flag && flag1 && event.isDropItems()) {
							block.playerDestroy(serverPlayerGameMode.level, serverPlayer, pos, iBlockData, tileEntity, itemStack1);
						}
					}

					List<ItemEntity> itemsToDrop = serverPlayerGameMode.level.captureDrops;
					serverPlayerGameMode.level.captureDrops = null;
					if (event.isDropItems()) {
						CraftEventFactory.handleBlockDropItemEvent(bblock, state, serverPlayer, itemsToDrop);
					}

					if (flag) {
						iBlockData.getBlock().popExperience(serverPlayerGameMode.level, pos, event.getExpToDrop(), serverPlayer);
					}

					if (
							mainHandStack != null
							&& flag
							&& isCorrectTool
							&& event.isDropItems()
							&& block instanceof BeehiveBlock
							&& tileEntity instanceof BeehiveBlockEntity beehiveBlockEntity
					) {
						CriteriaTriggers.BEE_NEST_DESTROYED.trigger(serverPlayer, iBlockData, mainHandStack, beehiveBlockEntity.getOccupantCount());
					}
				}
				return true;
			} else {
				serverPlayerGameMode.level.sendBlockUpdated(pos, iBlockData, iBlockData, 3);
				return false;
			}
		}
	}

	/**
	 * @param player player
	 * @return False if no tasks with player
	 */
	public static boolean hasPlayer(@NotNull Player player) {
		return blocks.containsSecondaryKey(player);
	}

	/**
	 * @param block block
	 * @return False if no tasks with block
	 */
	public static boolean hasBlock(@NotNull Block block) {
		return blocks.containsPrimaryKey(block);
	}

	/**
	 * Cancels all block break tasks with block
	 *
	 * @param block block
	 */
	public static void cancelAllTasksWithThisBlock(@NotNull Block block) {
		if (blocks.containsPrimaryKey(block)) {
			Integer taskId = blocks.removeByPrimaryKey(block);
			assert taskId != null;
			PlayerUtils.farAway.remove(blocks.getSecondaryKey(block));
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}

	/**
	 * Cancels all block break tasks with player
	 *
	 * @param player player
	 */
	public static void cancelAllTasksWithThisPlayer(@NotNull Player player) {
		if (blocks.containsSecondaryKey(player)) {
			Integer taskId = blocks.removeBySecondaryKey(player);
			assert taskId != null;
			PlayerUtils.farAway.remove(player);
			Bukkit.getScheduler().cancelTask(taskId);
		}
	}

	public static @Nullable BlockData getBlockDataByMaterial(@NotNull Material material) {
		return switch (material) {
			case REDSTONE -> Material.REDSTONE_WIRE.createBlockData();
			case STRING -> Material.TRIPWIRE.createBlockData();
			default -> material.isBlock() ? material.createBlockData() : null;
		};
	}

	/**
	 * @param blockData block material
	 * @return True if material has wood sound
	 */
	public static boolean isWoodenSound(@NotNull BlockData blockData) {
		return blockData.getMaterial() != Material.NOTE_BLOCK
				&& WOOD_SOUND_GROUP.equals(blockData.getSoundGroup());
	}
}
