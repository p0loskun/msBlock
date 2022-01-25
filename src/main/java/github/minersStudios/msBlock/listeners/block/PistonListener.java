package github.minersStudios.msBlock.listeners.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class PistonListener implements Listener {

    @EventHandler
    public void onPistonExtends(BlockPistonExtendEvent event){
        for (Block blocks : event.getBlocks()) {
            if (blocks.getType().equals(Material.NOTE_BLOCK))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonEvent(BlockPistonRetractEvent event){
        for (Block blocks : event.getBlocks()) {
            if (blocks.getType().equals(Material.NOTE_BLOCK))
                event.setCancelled(true);
        }
    }

}
