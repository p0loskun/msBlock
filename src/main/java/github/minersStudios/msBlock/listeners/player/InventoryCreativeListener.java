package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;

import javax.annotation.Nonnull;

public class InventoryCreativeListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInventoryCreative(@Nonnull InventoryCreativeEvent event) {
        if (!event.getClick().isCreativeAction()) return;
        HumanEntity player = event.getWhoClicked();
        Block clickedBlock = player.getTargetBlockExact(5);
        if (clickedBlock == null || event.getCursor().getType() != Material.NOTE_BLOCK || !(clickedBlock.getBlockData() instanceof NoteBlock noteBlock)) return;
        event.setCancelled(true);
        player.getInventory().setItem(
                event.getSlot(),
                CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).getItemStack()
        );
    }
}
