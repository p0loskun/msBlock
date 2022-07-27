package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.annotation.Nonnull;

public class BlockPlaceListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockPlace(@Nonnull BlockPlaceEvent event) {
		Block block = event.getBlockPlaced();
		event.setCancelled(block.getType() == Material.NOTE_BLOCK);
		if (BlockUtils.isWoodenSound(block.getType()))
			CustomBlockMaterial.DEFAULT.playPlaceSound(block);
		if (block.getType() == Material.NOTE_BLOCK)
			CustomBlockMaterial.DEFAULT.setCustomBlock(block, event.getPlayer(), event.getHand());
	}
}