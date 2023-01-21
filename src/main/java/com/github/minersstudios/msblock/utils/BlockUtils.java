package com.github.minersstudios.msblock.utils;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.customblock.CustomBlock;
import com.github.minersstudios.msblock.customblock.NoteBlockData;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BlockUtils {
	public static final Map<Map.Entry<Block, Player>, Integer> blocks = new ConcurrentHashMap<>();
	public static final List<Recipe> CUSTOM_BLOCK_RECIPES = new ArrayList<>();

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
	 * @param location location around which the blocks break
	 */
	public static void removeBlocksAround(@NotNull Location location) {
		Block topBlock = location.clone().add(0.0d, 1.0d, 0.0d).getBlock();
		Block bottomBlock = location.clone().subtract(0.0d, 1.0d, 0.0d).getBlock();
		if (BREAK_ON_BLOCK_PLACE.contains(topBlock.getType())) {
			breakBlock(topBlock);
		}
		if (BREAK_ON_BLOCK_PLACE.contains(bottomBlock.getType())) {
			breakBlock(bottomBlock);
		}
	}

	public static void breakBlock(@NotNull Block block) {
		World world = block.getWorld();
		SoundGroup soundGroup = block.getBlockSoundGroup();
		world.playSound(
				block.getLocation(),
				soundGroup.getBreakSound(),
				soundGroup.getVolume(),
				soundGroup.getPitch()
		);
		world.spawnParticle(
				Particle.BLOCK_CRACK,
				block.getLocation().clone().add(0.5d, 0.25d, 0.5d),
				80, 0.35d, 0.35d, 0.35d,
				block.getBlockData()
		);
		block.breakNaturally();
	}

	public static @NotNull CustomBlock getCustomBlock(@NotNull Instrument instrument, @NotNull Note note, boolean powered) {
		NoteBlockData noteBlockData = new NoteBlockData(instrument, note, powered);
		for (CustomBlock customBlock : Main.getConfigCache().customBlocks.values()) {
			if (customBlock.getNoteBlockData() == null) {
				Map<?, NoteBlockData> map =
						customBlock.getBlockFaceMap() == null
						? customBlock.getBlockAxisMap()
						: customBlock.getBlockFaceMap();
				if (map != null) {
					for (NoteBlockData data : map.values()) {
						if (noteBlockData.isSimilar(data)) {
							customBlock.setNoteBlockData(noteBlockData);
						}
					}
				}
			}
			if (noteBlockData.isSimilar(customBlock.getNoteBlockData())) return customBlock;
		}
		return CustomBlock.DEFAULT;
	}

	public static @NotNull CustomBlock getCustomBlock(int itemCustomModelData) {
		for (CustomBlock customBlock : Main.getConfigCache().customBlocks.values()) {
			if (customBlock.getItemCustomModelData() == itemCustomModelData) {
				return customBlock;
			}
		}
		return CustomBlock.DEFAULT;
	}

	/**
	 * @param player player
	 * @return True if no tasks with player
	 */
	public static boolean hasPlayer(@NotNull Player player) {
		for (Map.Entry<Block, Player> entry : blocks.keySet()) {
			if (entry.getValue() == player) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param block block
	 * @return map entry with block & player
	 */
	public static @Nullable Map.Entry<Block, Player> getEntryByBlock(@NotNull Block block) {
		for (Map.Entry<Block, Player> entry : blocks.keySet()) {
			if (entry.getKey().equals(block)) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * Cancels all block break tasks with block
	 *
	 * @param block block
	 */
	public static void cancelAllTasksWithThisBlock(@NotNull Block block) {
		for (Map.Entry<Block, Player> entry : blocks.keySet()) {
			if (entry.getKey().equals(block)) {
				Bukkit.getScheduler().cancelTask(blocks.remove(entry));
				PlayerUtils.farAway.remove(entry.getValue());
			}
		}
	}

	/**
	 * Cancels all block break tasks with player
	 *
	 * @param player player
	 */
	public static void cancelAllTasksWithThisPlayer(@NotNull Player player) {
		for (Map.Entry<Block, Player> entry : blocks.keySet()) {
			if (entry.getValue().equals(player)) {
				Bukkit.getScheduler().cancelTask(blocks.remove(entry));
				PlayerUtils.farAway.remove(entry.getValue());
			}
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
	 * @param material block material
	 * @return True if material has wood sound
	 */
	public static boolean isWoodenSound(@NotNull Material material) {
		if (material == Material.NOTE_BLOCK) return false;
		return Material.OAK_FENCE.createBlockData().getSoundGroup().equals(material.createBlockData().getSoundGroup());
	}
}
