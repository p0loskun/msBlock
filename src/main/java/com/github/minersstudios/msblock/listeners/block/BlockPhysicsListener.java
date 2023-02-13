package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.mscore.MSListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.jetbrains.annotations.NotNull;

@MSListener
public class BlockPhysicsListener implements Listener {

	@EventHandler
	private void onBlockPhysics(@NotNull BlockPhysicsEvent event) {
		Block block = event.getBlock();
		if (block.getRelative(BlockFace.UP).getType() == Material.NOTE_BLOCK || block.getType() == Material.NOTE_BLOCK) {
			BlockUtils.updateNoteBlock(block);
			event.setCancelled(true);
		}
	}
}
