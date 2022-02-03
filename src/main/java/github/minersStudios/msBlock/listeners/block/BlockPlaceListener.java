package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.annotation.Nonnull;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(@Nonnull BlockPlaceEvent event){
        if(event.getBlockPlaced().getType() == Material.NOTE_BLOCK){
            event.setCancelled(true);
            event.getPlayer().sendMessage("ꑜ" + ChatColor.DARK_RED + " Простите, но нотные блоки запрещены");
            BlockUtils.UpdateNoteBlock(event.getBlockPlaced().getLocation());
        }
    }
}
