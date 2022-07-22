package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.annotation.Nonnull;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(@Nonnull BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        event.setCancelled(player.getInventory().getItemInMainHand().getType() == Material.PAPER || block.getType() == Material.NOTE_BLOCK);
        if (BlockUtils.isWoodenSound(block.getType()))
            block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.place", 1.0f, 0.9f);
        if (block.getType() == Material.NOTE_BLOCK)
            CustomBlockMaterial.DEFAULT.setCustomBlock(block, player, event.getHand());
    }
}
