package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import javax.annotation.Nonnull;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(@Nonnull PlayerDeathEvent event) {
        BlockUtils.cancelAllTasksWithThisPlayer(event.getEntity());
    }
}
