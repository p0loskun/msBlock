package github.minersStudios.msBlock.listeners.block;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
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
            SoundGroup soundGroup = block.getBlockData().getSoundGroup();
            block.getWorld().playSound(block.getLocation(), "custom." + soundGroup.getBreakSound().getKey().getKey(), soundGroup.getVolume(), soundGroup.getPitch());
        } else if (block.getType() == Material.NOTE_BLOCK && player.getGameMode() == GameMode.CREATIVE) {
            NoteBlock noteBlock = (NoteBlock) block.getBlockData();
            CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
            if (customBlockMaterial != null)
                block.getWorld().playSound(block.getLocation(), customBlockMaterial.getSoundBreak(), 1.0f, 0.9f);
        }
    }
}
