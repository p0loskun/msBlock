package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import javax.annotation.Nonnull;

public class BlockPhysicsListener implements Listener {
    @EventHandler
    private void onBlockPhysics(@Nonnull BlockPhysicsEvent event) {
        Block block = event.getBlock(),
                topBlock = block.getRelative(BlockFace.UP);
        if (topBlock.getType() == Material.NOTE_BLOCK || block.getType() == Material.NOTE_BLOCK) {
            BlockUtils.updateNoteBlock(block.getLocation());
            event.setCancelled(true);
        }
    }
}
