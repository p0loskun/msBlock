package github.minersStudios.msBlock.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.utils.BlockUtils.TRANSPARENT;
import static github.minersStudios.msBlock.utils.BlockUtils.blocks;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerKick(@Nonnull PlayerKickEvent event) {
        Block block = event.getPlayer().getTargetBlock(TRANSPARENT, 5);
        if(blocks.get(block) != null)
            Bukkit.getScheduler().cancelTask(blocks.remove(block));
    }

    @EventHandler
    public void onPlayerLeave(@Nonnull PlayerQuitEvent event){
        Block block = event.getPlayer().getTargetBlock(TRANSPARENT, 5);
        if(blocks.get(block) != null)
            Bukkit.getScheduler().cancelTask(blocks.remove(block));
    }
}
