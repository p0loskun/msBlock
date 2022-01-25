package github.minersStudios.msBlock.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static github.minersStudios.msBlock.utils.PlayerUtils.diggers;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(diggers.get(player) == null) return;
        Bukkit.getScheduler().cancelTask(diggers.remove(player));
    }

}
