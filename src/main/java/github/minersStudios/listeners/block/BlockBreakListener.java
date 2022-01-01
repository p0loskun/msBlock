package github.minersStudios.listeners.block;

import github.minersStudios.objects.CustomBlock;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block blockBraked = event.getBlock();
        if (blockBraked.getType() != Material.NOTE_BLOCK || event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;

        event.setCancelled(true);
        CustomBlock customBlock = new CustomBlock(blockBraked, event.getPlayer());
        customBlock.breakCustomBlock(event);
    }
}
