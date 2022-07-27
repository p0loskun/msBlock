package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.utils.BlockUtils;
import github.minersStudios.msBlock.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import javax.annotation.Nonnull;

public class PlayerGameModeChangeListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerGameModeChange(@Nonnull PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		BlockUtils.cancelAllTasksWithThisPlayer(player);
		PlayerUtils.steps.remove(player);
	}
}