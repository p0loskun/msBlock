package com.github.minersstudios.msblock.listeners.player;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.msblock.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerTeleportListener implements Listener {

	@EventHandler
	public void onPlayerTeleport(@NotNull PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
			BlockUtils.cancelAllTasksWithThisPlayer(player);
			PlayerUtils.steps.remove(player);
		});
	}
}
