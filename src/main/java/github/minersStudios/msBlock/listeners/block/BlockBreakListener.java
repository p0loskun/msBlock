package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import javax.annotation.Nonnull;

public class BlockBreakListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockBreak(@Nonnull BlockBreakEvent event) {
		Block block = event.getBlock();
		if (BlockUtils.isWoodenSound(block.getType()))
			CustomBlockMaterial.DEFAULT.playBreakSound(block);
		if (block.getBlockData() instanceof NoteBlock noteBlock) {
			GameMode gameMode = event.getPlayer().getGameMode();
			event.setCancelled(gameMode == GameMode.SURVIVAL);
			if (gameMode == GameMode.CREATIVE)
				CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).playBreakSound(block);
		}
	}
}