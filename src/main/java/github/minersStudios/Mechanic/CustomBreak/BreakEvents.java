package github.minersStudios.Mechanic.CustomBreak;

import github.minersStudios.Mechanic.CustomBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public class BreakEvents implements Listener {



    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block blockBraked = event.getBlock();
        if (blockBraked.getType() != Material.NOTE_BLOCK) return;

        event.getPlayer().setNoDamageTicks(1);
        CustomBlock customBlock = new CustomBlock(blockBraked, event.getPlayer());
        customBlock.breakCustomBlock(event);
        event.setDropItems(false);
    }
}
