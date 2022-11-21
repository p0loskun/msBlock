package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.msblock.customBlock.CustomBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import javax.annotation.Nonnull;

public class ExplosionListener implements Listener {

	@EventHandler
	public void onEntityExplode(@Nonnull EntityExplodeEvent event) {
		for (Block block : event.blockList()) {
			if (block.getBlockData() instanceof NoteBlock noteBlock) {
				block.setType(Material.AIR);
				block.getWorld().dropItemNaturally(
						block.getLocation(),
						CustomBlock.getCustomBlock(noteBlock.getInstrument(), noteBlock.getNote(), noteBlock.isPowered()).getItemStack()
				);
			}
		}
	}

	@EventHandler
	public void onBlockExplode(@Nonnull BlockExplodeEvent event) {
		for (Block block : event.blockList()) {
			if (block.getBlockData() instanceof NoteBlock noteBlock) {
				block.setType(Material.AIR);
				block.getWorld().dropItemNaturally(
						block.getLocation(),
						CustomBlock.getCustomBlock(noteBlock.getInstrument(), noteBlock.getNote(), noteBlock.isPowered()).getItemStack()
				);
			}
		}
	}
}
