package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.msblock.enums.CustomBlock;
import com.github.minersstudios.msblock.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.annotation.Nonnull;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onBlockPlace(@Nonnull BlockPlaceEvent event) {
		Block block = event.getBlockPlaced();
		event.setCancelled(block.getType() == Material.NOTE_BLOCK);
		if (BlockUtils.isWoodenSound(block.getType())) {
			CustomBlock.DEFAULT.playPlaceSound(block);
		}
		if (block.getType() == Material.NOTE_BLOCK) {
			CustomBlock.DEFAULT.setCustomBlock(block, event.getPlayer(), event.getHand());
		}
	}
}
