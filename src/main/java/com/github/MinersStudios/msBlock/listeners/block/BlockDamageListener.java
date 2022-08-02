package com.github.MinersStudios.msBlock.listeners.block;

import com.github.MinersStudios.msBlock.enums.CustomBlockMaterial;
import com.github.MinersStudios.msBlock.utils.BlockUtils;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import javax.annotation.Nonnull;

public class BlockDamageListener implements Listener {

	@EventHandler
	public void onBlockDamage(@Nonnull BlockDamageEvent event) {
		Block block = event.getBlock();
		if (BlockUtils.isWoodenSound(block.getType())) {
			CustomBlockMaterial.DEFAULT.playHitSound(block);
		}
		if (block.getBlockData() instanceof NoteBlock noteBlock) {
			CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).playHitSound(block);
		}
	}
}
