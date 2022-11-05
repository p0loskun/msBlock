package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.msblock.enums.CustomBlock;
import com.github.minersstudios.msblock.utils.BlockUtils;
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
			CustomBlock.DEFAULT.playHitSound(block);
		}
		if (block.getBlockData() instanceof NoteBlock noteBlock) {
			CustomBlock.getCustomBlock(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).playHitSound(block);
		}
	}
}
