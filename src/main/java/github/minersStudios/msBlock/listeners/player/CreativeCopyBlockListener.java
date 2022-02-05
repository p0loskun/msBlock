package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.enumerators.CustomBlockMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCreativeEvent;

public class CreativeCopyBlockListener implements Listener {

    @EventHandler
    public void onCopyBlock(InventoryCreativeEvent event){
        if(event.getClick() != ClickType.CREATIVE) return;
        Player player = (Player) event.getWhoClicked();
        Block clickedBlock = player.getTargetBlockExact(5);
        if(clickedBlock == null) return;
        if(event.getCursor().getType() != Material.NOTE_BLOCK) return;
        NoteBlock noteBlock = (NoteBlock) clickedBlock.getBlockData();
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
        if(customBlockMaterial == null) return;
        event.setCancelled(true);
        player.getInventory().setItem(event.getSlot(), customBlockMaterial.getItemStack());
    }
}
