package com.github.minersstudios.msblock.listeners.inventory;

import com.github.minersstudios.msblock.utils.PlayerUtils;
import com.github.minersstudios.mscore.MSListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@MSListener
public class PrepareItemCraftListener implements Listener {

	@EventHandler
	public void onPrepareItemCraft(@NotNull PrepareItemCraftEvent event) {
		for (ItemStack itemStack : event.getInventory().getMatrix()) {
			if (itemStack != null && PlayerUtils.isItemCustomBlock(itemStack)) {
				event.getInventory().setResult(new ItemStack(Material.AIR));
			}
		}
	}
}
