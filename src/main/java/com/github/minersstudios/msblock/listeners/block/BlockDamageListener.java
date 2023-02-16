package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.msblock.customblock.CustomBlockData;
import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.mscore.MSListener;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.jetbrains.annotations.NotNull;

@MSListener
public class BlockDamageListener implements Listener {

	@EventHandler
	public void onBlockDamage(@NotNull BlockDamageEvent event) {
		Block block = event.getBlock();
		Location blockLocation = block.getLocation().toCenterLocation();
		if (BlockUtils.isWoodenSound(block.getType())) {
			CustomBlockData.DEFAULT.getSoundGroup().playHitSound(blockLocation);
		}
		if (block.getBlockData() instanceof NoteBlock noteBlock) {
			BlockUtils.getCustomBlock(noteBlock.getInstrument(), noteBlock.getNote(), noteBlock.isPowered()).getSoundGroup().playHitSound(blockLocation);
		}
	}
}
