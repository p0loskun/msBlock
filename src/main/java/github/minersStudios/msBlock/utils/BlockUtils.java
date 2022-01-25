package github.minersStudios.msBlock.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.HashSet;

public class BlockUtils {

    public static final ImmutableSet<Material> TRANSPARENT = Sets.immutableEnumSet(
            Material.AIR,
            Material.WATER,
            Material.LAVA
    );

    public static final HashSet<Material> REPLACE = Sets.newHashSet(
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
            Material.SNOW
    );

    /**
     * Updates the note block and checks if there is a notes block above it
     *
     * @param location block location which will be updated
     */
    public static void UpdateNoteBlock(Location location) {
        Block block = location.getBlock().getRelative(BlockFace.UP);
        if (block.getType() == Material.NOTE_BLOCK)
            block.getState().update(true, false);

        Block nextBlock = block.getRelative(BlockFace.UP);
        if (nextBlock.getType() == Material.NOTE_BLOCK)
            UpdateNoteBlock(block.getLocation());
    }

}
