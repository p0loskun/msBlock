package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.mscore.MSListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.jetbrains.annotations.NotNull;

@MSListener
public class BlockPistonRetractListener implements Listener {

	@EventHandler
	public void onBlockPistonRetract(@NotNull BlockPistonRetractEvent event) {
		for (Block block : event.getBlocks()) {
			event.setCancelled(block.getType() == Material.NOTE_BLOCK);
		}
	}
}
