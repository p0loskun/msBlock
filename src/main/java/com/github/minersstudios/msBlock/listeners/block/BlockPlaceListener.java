package com.github.minersstudios.msBlock.listeners.block;

import com.github.minersstudios.msBlock.enums.CustomBlockMaterial;
import com.github.minersstudios.msBlock.utils.BlockUtils;
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
			CustomBlockMaterial.DEFAULT.playPlaceSound(block);
		}
		if (block.getType() == Material.NOTE_BLOCK) {
			CustomBlockMaterial.DEFAULT.setCustomBlock(block, event.getPlayer(), event.getHand());
		}
	}
}
