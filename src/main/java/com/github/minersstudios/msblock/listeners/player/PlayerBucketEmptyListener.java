package com.github.minersstudios.msblock.listeners.player;

import com.github.minersstudios.msblock.utils.PlayerUtils;
import com.github.minersstudios.mscore.MSListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.jetbrains.annotations.NotNull;

@MSListener
public class PlayerBucketEmptyListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerBucketEmpty(@NotNull PlayerBucketEmptyEvent event) {
		if (
				event.getBlock().getType() == Material.NOTE_BLOCK
				|| PlayerUtils.isItemCustomBlock(event.getPlayer().getInventory().getItemInMainHand())
		) {
			event.setCancelled(true);
		}
	}
}
