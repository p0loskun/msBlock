package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.msblock.customBlock.CustomBlock;
import com.github.minersstudios.msblock.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.jetbrains.annotations.NotNull;

public class BlockDamageListener implements Listener {

	@EventHandler
	public void onBlockDamage(@NotNull BlockDamageEvent event) {
		Block block = event.getBlock();
		Location blockLocation = block.getLocation().toCenterLocation();
		if (BlockUtils.isWoodenSound(block.getType())) {
			CustomBlock.DEFAULT.getSoundGroup().playHitSound(blockLocation);
		}
		if (block.getBlockData() instanceof NoteBlock noteBlock) {
			CustomBlock.getCustomBlock(noteBlock.getInstrument(), noteBlock.getNote(), noteBlock.isPowered()).getSoundGroup().playHitSound(blockLocation);
		}
	}
}
