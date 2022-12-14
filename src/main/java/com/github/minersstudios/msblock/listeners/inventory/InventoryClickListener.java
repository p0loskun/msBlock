package com.github.minersstudios.msblock.listeners.inventory;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.msblock.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InventoryClickListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClick(@NotNull InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory clickedInventory = event.getClickedInventory();
		ItemStack itemInCursor = event.getCursor();
		ItemStack currentItem = event.getCurrentItem();
		if (
				BlockUtils.IGNORABLE_INVENTORY_TYPES.contains(event.getInventory().getType())
				&& event.isShiftClick()
				&& currentItem != null
				&& PlayerUtils.isItemCustomBlock(currentItem)
		) {
			event.setCancelled(true);
			Bukkit.getScheduler().runTask(Main.getInstance(), player::updateInventory);
		} else if (
				clickedInventory != null
				&& BlockUtils.IGNORABLE_INVENTORY_TYPES.contains(clickedInventory.getType())
				&& itemInCursor != null
				&& PlayerUtils.isItemCustomBlock(itemInCursor)
		) {
			event.setCancelled(true);
			Bukkit.getScheduler().runTask(Main.getInstance(), player::updateInventory);
		}
	}
}
