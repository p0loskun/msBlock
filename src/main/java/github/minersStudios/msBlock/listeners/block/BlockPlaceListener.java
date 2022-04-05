package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.ChatColor;
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
            event.getPlayer().sendMessage("ꑜ" + ChatColor.DARK_RED + " Простите, но нотные блоки не разрешено устанавливать");
            BlockUtils.UpdateNoteBlock(block.getLocation());
        }
    }
}
