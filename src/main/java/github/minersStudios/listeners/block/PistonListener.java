package github.minersStudios.listeners.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PistonListener implements Listener {

    @EventHandler
    public void onPistonExtends(org.bukkit.event.block.BlockPistonExtendEvent event){
        for (Block blocks : event.getBlocks()) {
            if (blocks.getType().equals(Material.NOTE_BLOCK))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonEvent(org.bukkit.event.block.BlockPistonRetractEvent event){
        for (Block blocks : event.getBlocks()) {
            if (blocks.getType().equals(Material.NOTE_BLOCK))
                event.setCancelled(true);
        }
    }

}
