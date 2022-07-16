package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCreativeEvent;

import javax.annotation.Nonnull;

public class InventoryCreativeListener implements Listener {

    @EventHandler
    public void onInventoryCreative(@Nonnull InventoryCreativeEvent event) {
        if (event.getClick() != ClickType.CREATIVE) return;
        Player player = (Player) event.getWhoClicked();
        Block clickedBlock = player.getTargetBlockExact(5);
        if (event.getCursor().getType() != Material.NOTE_BLOCK || clickedBlock == null || !(clickedBlock.getBlockData() instanceof NoteBlock noteBlock)) return;
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
        if (customBlockMaterial != null) {
            player.getInventory().setItem(event.getSlot(), customBlockMaterial.getItemStack());
            event.setCancelled(true);
        }
    }
}
