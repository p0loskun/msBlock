package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import javax.annotation.Nonnull;

public class BlockDamageListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockDamage(@Nonnull BlockDamageEvent event) {
		Block block = event.getBlock();
		if (BlockUtils.isWoodenSound(block.getType()))
			CustomBlockMaterial.DEFAULT.playHitSound(block);
		if (block.getBlockData() instanceof NoteBlock noteBlock)
			CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).playHitSound(block);
	}
}