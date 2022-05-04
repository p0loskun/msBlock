package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.enumerators.CustomBlockMaterial;
import github.minersStudios.msBlock.objects.CustomBlock;
import github.minersStudios.msBlock.utils.BlockUtils;
import github.minersStudios.msBlock.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.annotation.Nonnull;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(@Nonnull BlockPlaceEvent event){
        Block block = event.getBlockPlaced();
        if(block.getType() == Material.NOTE_BLOCK){
            event.setCancelled(true);
            CustomBlock customBlock = new CustomBlock(block, event.getPlayer());
            CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.DEFAULT;
            customBlock.setCustomBlock(customBlockMaterial);
            BlockUtils.removeBlock(block.getLocation());
        }
    }
}
