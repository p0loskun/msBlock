package github.minersStudios.msBlock.listeners.block;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static github.minersStudios.msBlock.utils.PlayerUtils.diggers;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() != GameMode.SURVIVAL) return;
        event.setCancelled(event.getBlock().getType() == Material.NOTE_BLOCK);
        if(diggers.get(player) != null)
            Bukkit.getScheduler().cancelTask(diggers.remove(player));
    }
}
