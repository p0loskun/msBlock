package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.mscore.MSListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.jetbrains.annotations.NotNull;

@MSListener
public class BlockPistonExtendListener implements Listener {

	@EventHandler
	public void onBlockPistonExtend(@NotNull BlockPistonExtendEvent event) {
		for (Block block : event.getBlocks()) {
			event.setCancelled(block.getType() == Material.NOTE_BLOCK);
		}
	}
}
