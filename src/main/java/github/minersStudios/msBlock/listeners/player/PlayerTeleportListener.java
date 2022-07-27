package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.utils.BlockUtils;
import github.minersStudios.msBlock.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import javax.annotation.Nonnull;

public class PlayerTeleportListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerTeleport(@Nonnull PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		BlockUtils.cancelAllTasksWithThisPlayer(player);
		PlayerUtils.steps.remove(player);
	}
}