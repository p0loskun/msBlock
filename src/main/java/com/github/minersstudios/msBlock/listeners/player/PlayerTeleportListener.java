package com.github.minersstudios.msBlock.listeners.player;

import com.github.minersstudios.msBlock.Main;
import com.github.minersstudios.msBlock.utils.BlockUtils;
import com.github.minersstudios.msBlock.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import javax.annotation.Nonnull;

public class PlayerTeleportListener implements Listener {

	@EventHandler
	public void onPlayerTeleport(@Nonnull PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
			BlockUtils.cancelAllTasksWithThisPlayer(player);
			PlayerUtils.steps.remove(player);
		});
	}
}
