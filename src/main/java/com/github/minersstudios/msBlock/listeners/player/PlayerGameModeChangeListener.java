package com.github.minersstudios.msBlock.listeners.player;

import com.github.minersstudios.msBlock.Main;
import com.github.minersstudios.msBlock.utils.BlockUtils;
import com.github.minersstudios.msBlock.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import javax.annotation.Nonnull;

public class PlayerGameModeChangeListener implements Listener {

	@EventHandler
	public void onPlayerGameModeChange(@Nonnull PlayerGameModeChangeEvent event) {
		Player player = event.getPlayer();
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
			BlockUtils.cancelAllTasksWithThisPlayer(player);
			PlayerUtils.steps.remove(player);
		});
	}
}
