package github.minersStudios.msBlock.listeners.block;

import com.google.common.collect.Sets;
import github.minersStudios.msBlock.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.HashSet;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        HashSet<Block> blockList = Sets.newHashSet(event.blockList());
        blockList.stream()
                .filter(block -> block.getType() == Material.NOTE_BLOCK)
                .forEach(block -> {
                    Main.coreProtectAPI.logRemoval("#tnt", block.getLocation(), Material.NOTE_BLOCK, block.getBlockData());
                    event.blockList().remove(block);
                    block.setType(Material.AIR);
                });
    }

}
