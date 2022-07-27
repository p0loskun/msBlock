package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
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
						CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).getItemStack()
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
						CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).getItemStack()
				);
			}
		}
	}
}