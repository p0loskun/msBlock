package github.minersStudios.msBlock.listeners.block;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.utils.BlockUtils.blocks;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(@Nonnull BlockBreakEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        Block block = event.getBlock();
        event.setCancelled(block.getType() == Material.NOTE_BLOCK);
        if(blocks.get(block) != null)
            Bukkit.getScheduler().cancelTask(blocks.remove(block));
    }
}
