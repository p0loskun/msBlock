package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import javax.annotation.Nonnull;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(@Nonnull PlayerTeleportEvent event) {
        BlockUtils.cancelAllTasksWithThisPlayer(event.getPlayer());
    }
}
