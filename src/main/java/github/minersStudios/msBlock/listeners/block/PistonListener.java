package github.minersStudios.msBlock.listeners.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import javax.annotation.Nonnull;

public class PistonListener implements Listener {
    @EventHandler
    public void onPistonExtends(@Nonnull BlockPistonExtendEvent event){
        for (Block block : event.getBlocks()) {
            event.setCancelled(block.getType().equals(Material.NOTE_BLOCK));
        }
    }

    @EventHandler
    public void onPistonEvent(@Nonnull BlockPistonRetractEvent event){
        for (Block block : event.getBlocks()) {
            event.setCancelled(block.getType().equals(Material.NOTE_BLOCK));
        }
    }
}
