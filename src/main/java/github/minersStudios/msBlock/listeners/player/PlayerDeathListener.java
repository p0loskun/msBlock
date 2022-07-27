package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.utils.BlockUtils;
import github.minersStudios.msBlock.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import javax.annotation.Nonnull;

public class PlayerDeathListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerDeath(@Nonnull PlayerDeathEvent event) {
		Player player = event.getEntity();
		BlockUtils.cancelAllTasksWithThisPlayer(player);
		PlayerUtils.steps.remove(player);
	}
}