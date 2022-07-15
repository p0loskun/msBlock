package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import javax.annotation.Nonnull;

public class PlayerGameModeChangeListener implements Listener {

    @EventHandler
    public void onPlayerGameModeChange(@Nonnull PlayerGameModeChangeEvent event) {
        BlockUtils.cancelAllTasksWithThisPlayer(event.getPlayer());
    }
}
