package github.minersStudios.utils;

import net.minecraft.core.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockUtils {

    public static void UpdateNoteBlock(Location loc) {
        Block block = loc.getBlock().getRelative(BlockFace.UP);
        if (block.getType() == Material.NOTE_BLOCK)
            block.getState().update(true, false);

        Block nextBlock = block.getRelative(BlockFace.UP);
        if (nextBlock.getType() == Material.NOTE_BLOCK)
            UpdateNoteBlock(block.getLocation());
    }

    public static BlockPosition getBlockPosition(Block block){
        return new BlockPosition(block.getX(), block.getY(), block.getZ());
    }

    public static int getBlockEntityId(Block block){
        return ((block.getX() & 0xFFF) << 20 | (block.getZ() & 0xFFF) << 8) | (block.getY() & 0xFF);
    }
}
