package github.minersStudios.msBlock.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class BlockUtils {

    public static final Map<Block, Integer> blocks = new HashMap<>();

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

    /**
     * Updates the note block and checks if there is a notes block above it
     *
     * @param location block location which will be updated
     */
    public static void updateNoteBlock(@Nonnull Location location) {
        Block block = location.getBlock().getRelative(BlockFace.UP), nextBlock = block.getRelative(BlockFace.UP);
        if (block.getType() == Material.NOTE_BLOCK) {
            block.getState().update(true, false);
        }
        if (nextBlock.getType() == Material.NOTE_BLOCK) {
            updateNoteBlock(block.getLocation());
        }
    }

    /**
     * Breaks top/bottom block
     *
     * @param location location around which the blocks break
     */
    public static void removeBlock(@Nonnull Location location) {
        Block topBlock = location.clone().add(0.0f, 1.0f, 0.0f).getBlock(),
                bottomBlock = location.clone().add(0.0f, -1.0f, 0.0f).getBlock();
        if(BREAK_ON_BLOCK_PLACE.contains(topBlock.getType())){
            topBlock.breakNaturally();
        }
        if(BREAK_ON_BLOCK_PLACE.contains(bottomBlock.getType())){
            bottomBlock.breakNaturally();
        }
    }
}
