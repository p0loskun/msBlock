package com.github.minersstudios.msblock.utils;

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

	private static final ImmutableSet<Material> WOOD = Sets.immutableEnumSet(
			//<editor-fold desc="Materials with wooden sound">
			Material.BOOKSHELF,
			Material.PUMPKIN,
			Material.CARVED_PUMPKIN,
			Material.JACK_O_LANTERN,
			Material.MELON,
			Material.CHEST,
			Material.TRAPPED_CHEST,
			Material.CRAFTING_TABLE,
			Material.TORCH,
			Material.WALL_TORCH,
			Material.SOUL_TORCH,
			Material.SOUL_WALL_TORCH,
			Material.REDSTONE_TORCH,
			Material.REDSTONE_WALL_TORCH,
			Material.LOOM,
			Material.COMPOSTER,
			Material.BARREL,
			Material.CARTOGRAPHY_TABLE,
			Material.FLETCHING_TABLE,
			Material.SMITHING_TABLE,
			Material.CAMPFIRE,
			Material.SOUL_CAMPFIRE,
			Material.BEE_NEST,
			Material.BEEHIVE,
			Material.LECTERN,
			Material.LEVER,
			Material.REPEATER,
			Material.COMPARATOR,
			Material.MUSHROOM_STEM,
			Material.RED_MUSHROOM_BLOCK,
			Material.BROWN_MUSHROOM_BLOCK,
			Material.COCOA,
			Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
			Material.HEAVY_WEIGHTED_PRESSURE_PLATE,
			Material.DAYLIGHT_DETECTOR,
			Material.OAK_PLANKS,
			Material.SPRUCE_PLANKS,
			Material.BIRCH_PLANKS,
			Material.JUNGLE_PLANKS,
			Material.ACACIA_PLANKS,
			Material.DARK_OAK_PLANKS,
			Material.MANGROVE_PLANKS,
			Material.CRIMSON_PLANKS,
			Material.WARPED_PLANKS,
			Material.OAK_WOOD,
			Material.SPRUCE_WOOD,
			Material.BIRCH_WOOD,
			Material.JUNGLE_WOOD,
			Material.ACACIA_WOOD,
			Material.DARK_OAK_WOOD,
			Material.MANGROVE_WOOD,
			Material.OAK_LOG,
			Material.SPRUCE_LOG,
			Material.BIRCH_LOG,
			Material.JUNGLE_LOG,
			Material.ACACIA_LOG,
			Material.DARK_OAK_LOG,
			Material.MANGROVE_LOG,
			Material.STRIPPED_OAK_LOG,
			Material.STRIPPED_SPRUCE_LOG,
			Material.STRIPPED_BIRCH_LOG,
			Material.STRIPPED_JUNGLE_LOG,
			Material.STRIPPED_ACACIA_LOG,
			Material.STRIPPED_DARK_OAK_LOG,
			Material.STRIPPED_MANGROVE_LOG,
			Material.STRIPPED_OAK_WOOD,
			Material.STRIPPED_SPRUCE_WOOD,
			Material.STRIPPED_BIRCH_WOOD,
			Material.STRIPPED_JUNGLE_WOOD,
			Material.STRIPPED_ACACIA_WOOD,
			Material.STRIPPED_DARK_OAK_WOOD,
			Material.STRIPPED_MANGROVE_WOOD,
			Material.OAK_SLAB,
			Material.SPRUCE_SLAB,
			Material.BIRCH_SLAB,
			Material.JUNGLE_SLAB,
			Material.ACACIA_SLAB,
			Material.DARK_OAK_SLAB,
			Material.MANGROVE_SLAB,
			Material.CRIMSON_SLAB,
			Material.WARPED_SLAB,
			Material.OAK_STAIRS,
			Material.SPRUCE_STAIRS,
			Material.BIRCH_STAIRS,
			Material.JUNGLE_STAIRS,
			Material.ACACIA_STAIRS,
			Material.DARK_OAK_STAIRS,
			Material.MANGROVE_STAIRS,
			Material.CRIMSON_STAIRS,
			Material.WARPED_STAIRS,
			Material.OAK_FENCE,
			Material.SPRUCE_FENCE,
			Material.BIRCH_FENCE,
			Material.JUNGLE_FENCE,
			Material.ACACIA_FENCE,
			Material.DARK_OAK_FENCE,
			Material.MANGROVE_FENCE,
			Material.CRIMSON_FENCE,
			Material.WARPED_FENCE,
			Material.OAK_FENCE_GATE,
			Material.SPRUCE_FENCE_GATE,
			Material.BIRCH_FENCE_GATE,
			Material.JUNGLE_FENCE_GATE,
			Material.ACACIA_FENCE_GATE,
			Material.DARK_OAK_FENCE_GATE,
			Material.MANGROVE_FENCE_GATE,
			Material.CRIMSON_FENCE_GATE,
			Material.WARPED_FENCE_GATE,
			Material.OAK_SIGN,
			Material.SPRUCE_SIGN,
			Material.BIRCH_SIGN,
			Material.JUNGLE_SIGN,
			Material.ACACIA_SIGN,
			Material.DARK_OAK_SIGN,
			Material.MANGROVE_SIGN,
			Material.CRIMSON_SIGN,
			Material.WARPED_SIGN,
			Material.OAK_BUTTON,
			Material.SPRUCE_BUTTON,
			Material.BIRCH_BUTTON,
			Material.JUNGLE_BUTTON,
			Material.ACACIA_BUTTON,
			Material.DARK_OAK_BUTTON,
			Material.MANGROVE_BUTTON,
			Material.CRIMSON_BUTTON,
			Material.WARPED_BUTTON,
			Material.OAK_PRESSURE_PLATE,
			Material.SPRUCE_PRESSURE_PLATE,
			Material.BIRCH_PRESSURE_PLATE,
			Material.JUNGLE_PRESSURE_PLATE,
			Material.ACACIA_PRESSURE_PLATE,
			Material.DARK_OAK_PRESSURE_PLATE,
			Material.MANGROVE_PRESSURE_PLATE,
			Material.CRIMSON_PRESSURE_PLATE,
			Material.WARPED_PRESSURE_PLATE,
			Material.OAK_DOOR,
			Material.SPRUCE_DOOR,
			Material.BIRCH_DOOR,
			Material.JUNGLE_DOOR,
			Material.ACACIA_DOOR,
			Material.DARK_OAK_DOOR,
			Material.MANGROVE_DOOR,
			Material.CRIMSON_DOOR,
			Material.WARPED_DOOR,
			Material.OAK_TRAPDOOR,
			Material.SPRUCE_TRAPDOOR,
			Material.BIRCH_TRAPDOOR,
			Material.JUNGLE_TRAPDOOR,
			Material.ACACIA_TRAPDOOR,
			Material.DARK_OAK_TRAPDOOR,
			Material.MANGROVE_TRAPDOOR,
			Material.CRIMSON_TRAPDOOR,
			Material.WARPED_TRAPDOOR,
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
			Material.WHITE_BANNER,
			Material.ORANGE_BANNER,
			Material.MAGENTA_BANNER,
			Material.LIGHT_BLUE_BANNER,
			Material.YELLOW_BANNER,
			Material.LIME_BANNER,
			Material.PINK_BANNER,
			Material.GRAY_BANNER,
			Material.LIGHT_GRAY_BANNER,
			Material.CYAN_BANNER,
			Material.PURPLE_BANNER,
			Material.BLUE_BANNER,
			Material.BROWN_BANNER,
			Material.GREEN_BANNER,
			Material.RED_BANNER,
			Material.BLACK_BANNER
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
	public static void removeBlock(@NotNull Location location) {
		World world = location.getWorld();
		Block topBlock = location.clone().add(0.0d, 1.0d, 0.0d).getBlock();
		if (BREAK_ON_BLOCK_PLACE.contains(topBlock.getType())) {
			SoundGroup tobBlockSoundGroup = topBlock.getBlockData().getSoundGroup();
			world.spawnParticle(Particle.BLOCK_CRACK, topBlock.getLocation().clone().add(0.5d, 0.25d, 0.5d), 80, 0.35d, 0.35d, 0.35d, topBlock.getBlockData());
			world.playSound(topBlock.getLocation(), tobBlockSoundGroup.getBreakSound(), tobBlockSoundGroup.getVolume(), tobBlockSoundGroup.getPitch());
			topBlock.breakNaturally();
		}
		Block bottomBlock = location.clone().subtract(0.0d, 1.0d, 0.0d).getBlock();
		if (BREAK_ON_BLOCK_PLACE.contains(bottomBlock.getType())) {
			SoundGroup bottomBlockSoundGroup = bottomBlock.getBlockData().getSoundGroup();
			world.spawnParticle(Particle.BLOCK_CRACK, bottomBlock.getLocation().clone().add(0.5d, 0.25d, 0.5d), 80, 0.35d, 0.35d, 0.35d, bottomBlock.getBlockData());
			world.playSound(bottomBlock.getLocation(), bottomBlockSoundGroup.getBreakSound(), bottomBlockSoundGroup.getVolume(), bottomBlockSoundGroup.getPitch());
			bottomBlock.breakNaturally();
		}
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
		return WOOD.contains(material);
	}
}
