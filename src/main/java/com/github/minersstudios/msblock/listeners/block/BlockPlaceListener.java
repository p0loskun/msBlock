package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.msblock.customblock.CustomBlock;
import com.github.minersstudios.msblock.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onBlockPlace(@NotNull BlockPlaceEvent event) {
		Block block = event.getBlockPlaced();
		event.setCancelled(block.getType() == Material.NOTE_BLOCK);
		if (BlockUtils.isWoodenSound(block.getType())) {
			CustomBlock.DEFAULT.getSoundGroup().playPlaceSound(block.getLocation().toCenterLocation());
		}
		if (block.getType() == Material.NOTE_BLOCK) {
			CustomBlock.DEFAULT.setCustomBlock(block, event.getPlayer(), event.getHand());
		}
	}
}
