package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import javax.annotation.Nonnull;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(@Nonnull BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.ADVENTURE) return;
        Block block = event.getBlock();
        event.setCancelled(block.getType() == Material.NOTE_BLOCK && player.getGameMode() == GameMode.SURVIVAL);
        if (BlockUtils.isWoodenSound(block.getType())) {
            block.getWorld().playSound(block.getLocation(), "custom.block.wood.break", 1.0f, 1.0f);
        } else if (block.getBlockData() instanceof NoteBlock noteBlock && player.getGameMode() == GameMode.CREATIVE) {
            CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).playBreakSound(block);
        }
    }
}
