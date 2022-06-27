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
            if(block.getType() == Material.NOTE_BLOCK) {
                NoteBlock noteBlock = (NoteBlock) block.getBlockData();
                CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
                block.setType(Material.AIR);
                if(customBlockMaterial != null)
                    block.getWorld().dropItemNaturally(block.getLocation(), customBlockMaterial.getItemStack());
            }
        }
    }

    @EventHandler
    public void onBlockExplode(@Nonnull BlockExplodeEvent event) {
        for (Block block : event.blockList()) {
            if(block.getType() == Material.NOTE_BLOCK) {
                NoteBlock noteBlock = (NoteBlock) block.getBlockData();
                CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
                block.setType(Material.AIR);
                if(customBlockMaterial != null)
                    block.getWorld().dropItemNaturally(block.getLocation(), customBlockMaterial.getItemStack());
            }
        }
    }
}
