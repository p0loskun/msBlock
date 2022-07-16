package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.annotation.Nonnull;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(@Nonnull BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if (BlockUtils.isWoodenSound(block.getType())) {
            SoundGroup soundGroup = block.getBlockData().getSoundGroup();
            block.getWorld().playSound(block.getLocation(), "custom.block.wood.place", soundGroup.getVolume(), soundGroup.getPitch());
        }
        if (block.getType() != Material.NOTE_BLOCK) return;
        event.setCancelled(true);
        CustomBlockMaterial.DEFAULT.setCustomBlock(block, event.getPlayer());
    }
}
