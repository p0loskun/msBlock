package com.github.minersstudios.msblock.listeners.inventory;

import com.github.minersstudios.msblock.MSBlock;
import com.github.minersstudios.msblock.utils.CustomBlockUtils;
import com.github.minersstudios.mscore.MSListener;
import com.github.minersstudios.mscore.utils.MSBlockUtils;
import com.github.minersstudios.mscore.utils.MSDecorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@MSListener
public class InventoryClickListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClick(@NotNull InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory clickedInventory = event.getClickedInventory();
		ItemStack itemInCursor = event.getCursor();
		ItemStack currentItem = event.getCurrentItem();
		if (
				CustomBlockUtils.IGNORABLE_INVENTORY_TYPES.contains(event.getInventory().getType())
				&& event.isShiftClick()
				&& MSBlockUtils.isCustomBlock(currentItem)
		) {
			event.setCancelled(true);
			Bukkit.getScheduler().runTask(MSBlock.getInstance(), player::updateInventory);
		} else if (
				clickedInventory != null
				&& CustomBlockUtils.IGNORABLE_INVENTORY_TYPES.contains(clickedInventory.getType())
				&& MSDecorUtils.isCustomDecor(itemInCursor)
		) {
			event.setCancelled(true);
			Bukkit.getScheduler().runTask(MSBlock.getInstance(), player::updateInventory);
		}
	}
}
