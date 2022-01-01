package github.minersStudios.listeners.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static github.minersStudios.utils.BlockUtils.UpdateNoteBlock;

public class BlockPhysicsListener implements Listener {

    @EventHandler
    private void onBlockPhysics(org.bukkit.event.block.BlockPhysicsEvent event) {
        Block block = event.getBlock(),
                topBlock = block.getRelative(BlockFace.UP);

        if (topBlock.getType() == Material.NOTE_BLOCK) {
            UpdateNoteBlock(block.getLocation());
            event.setCancelled(true);
        }
        if (block.getType() == Material.NOTE_BLOCK) {
            UpdateNoteBlock(block.getLocation());
            event.setCancelled(true);
        }
    }

}
