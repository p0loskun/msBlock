package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.mscore.MSListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

@MSListener
public class EntityExplodeListener implements Listener {

	@EventHandler
	public void onEntityExplode(@NotNull EntityExplodeEvent event) {
		for (Block block : event.blockList()) {
			if (block.getBlockData() instanceof NoteBlock noteBlock) {
				block.setType(Material.AIR);
				block.getWorld().dropItemNaturally(
						block.getLocation(),
						BlockUtils.getCustomBlock(noteBlock.getInstrument(), noteBlock.getNote(), noteBlock.isPowered()).craftItemStack()
				);
			}
		}
	}
}
