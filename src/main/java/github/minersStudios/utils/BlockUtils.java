package github.minersStudios.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.HashSet;

public class BlockUtils {

    public static final ImmutableSet<Material> TRANSPERENT = Sets.immutableEnumSet(
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
            Material.VINE
    );

    public static void UpdateNoteBlock(Location loc) {
        Block block = loc.getBlock().getRelative(BlockFace.UP);
        if (block.getType() == Material.NOTE_BLOCK)
            block.getState().update(true, false);

        Block nextBlock = block.getRelative(BlockFace.UP);
        if (nextBlock.getType() == Material.NOTE_BLOCK)
            UpdateNoteBlock(block.getLocation());
    }

}
