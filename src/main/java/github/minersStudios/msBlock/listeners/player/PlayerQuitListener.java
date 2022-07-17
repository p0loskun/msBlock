package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.utils.BlockUtils;
import github.minersStudios.msBlock.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nonnull;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(@Nonnull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        BlockUtils.cancelAllTasksWithThisPlayer(player);
        PlayerUtils.steps.remove(player);
    }
}
