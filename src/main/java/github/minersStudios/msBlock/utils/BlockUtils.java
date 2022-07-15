package github.minersStudios.msBlock.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class BlockUtils {
    public static final Map<Object[], Integer> blocks = new HashMap<>();

    public static final ImmutableSet<Material> REPLACE = Sets.immutableEnumSet(
            Material.AIR,
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
    );

    private static final ImmutableSet<Material> BREAK_ON_BLOCK_PLACE = Sets.immutableEnumSet(
            Material.TALL_GRASS,
            Material.LARGE_FERN,
            Material.TALL_SEAGRASS
    );

    private static final ImmutableSet<Material> WOOD = Sets.immutableEnumSet(
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
            Material.WARPED_TRAPDOOR
    );


    /**
     * Updates the note block and checks if there is a notes block above it
     *
     * @param block block which will be updated
     */
    public static void updateNoteBlock(@Nonnull Block block) {
        Block nextBlock = block.getRelative(BlockFace.UP);
        if (block.getType() == Material.NOTE_BLOCK)
            block.getState().update(true, false);
        if (nextBlock.getType() == Material.NOTE_BLOCK)
            updateNoteBlock(nextBlock);
    }

    /**
     * Breaks top/bottom block
     *
     * @param location location around which the blocks break
     */
    public static void removeBlock(@Nonnull Location location) {
        Block topBlock = location.clone().add(0.0f, 1.0f, 0.0f).getBlock(),
                bottomBlock = location.clone().add(0.0f, -1.0f, 0.0f).getBlock();
        World world = topBlock.getWorld();
        if (BREAK_ON_BLOCK_PLACE.contains(topBlock.getType())) {
            SoundGroup tobBlockSoundGroup = topBlock.getBlockData().getSoundGroup();
            world.spawnParticle(Particle.BLOCK_CRACK, topBlock.getLocation().clone().add(0.5d, 0.25d, 0.5d), 80, 0.35d, 0.35d, 0.35d, topBlock.getBlockData());
            world.playSound(topBlock.getLocation(), tobBlockSoundGroup.getBreakSound(), tobBlockSoundGroup.getVolume(), tobBlockSoundGroup.getPitch());
            topBlock.breakNaturally();
        }
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
    public static boolean notHasPlayer(@Nonnull Player player) {
        for (Object[] objects : blocks.keySet())
            if (objects[1] == player)
                return false;
        return true;
    }

    /**
     * @param location block location
     * @return Object[] (Block[0] Player[1])
     */
    @Nullable
    public static Object[] getObjectByLocation(@Nonnull Location location) {
        for (Object[] objects : blocks.keySet()) {
            Location objectLocation = (Location) objects[0];
            if (
                    objectLocation.getX() == location.getX()
                            && objectLocation.getY() == location.getY()
                            && objectLocation.getZ() == location.getZ()
            ) return objects;
        }
        return null;
    }

    /**
     * Cancels all block break tasks with block at certain location
     *
     * @param location block location
     */
    public static void cancelAllTasksWithThisBlock(@Nonnull Location location) {
        for (Object[] objects : blocks.keySet()) {
            Location objectLocation = (Location) objects[0];
            if (
                    objectLocation.getX() == location.getX()
                            && objectLocation.getY() == location.getY()
                            && objectLocation.getZ() == location.getZ()
            ) Bukkit.getScheduler().cancelTask(blocks.remove(objects));
        }
    }

    /**
     * Cancels all block break tasks with player
     *
     * @param player player
     */
    public static void cancelAllTasksWithThisPlayer(@Nonnull Player player) {
        for (Object[] objects : blocks.keySet())
            if (objects[1] == player)
                Bukkit.getScheduler().cancelTask(blocks.remove(objects));
    }

    /**
     * @param material block material
     * @return True if material has wood sound
     */
    public static boolean isWoodenSound(@Nonnull Material material){
        return WOOD.contains(material);
    }
}
