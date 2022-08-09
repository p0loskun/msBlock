package com.github.minersstudios.msBlock.listeners.player;

import com.github.minersstudios.msBlock.Main;
import com.github.minersstudios.msBlock.utils.BlockUtils;
import com.github.minersstudios.msBlock.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import javax.annotation.Nonnull;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onPlayerDeath(@Nonnull PlayerDeathEvent event) {
		Player player = event.getEntity();
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
			BlockUtils.cancelAllTasksWithThisPlayer(player);
			PlayerUtils.steps.remove(player);
		});
	}
}
