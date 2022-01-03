package github.minersStudios.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import static github.minersStudios.utils.PlayerUtils.diggers;

public class PlayerKickListener implements Listener {

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        Player player = event.getPlayer();
        if(diggers.get(player) == null) return;
        Bukkit.getScheduler().cancelTask(diggers.remove(player));
    }

}
