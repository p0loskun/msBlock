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
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class CreativeCopyBlockListener implements Listener {

    @EventHandler
    public void onCopyBlock(@Nonnull InventoryCreativeEvent event){
        if(event.getClick() != ClickType.CREATIVE) return;
        Player player = (Player) event.getWhoClicked();
        Block clickedBlock = player.getTargetBlockExact(5);
        if(clickedBlock == null) return;
        if(event.getCursor().getType() != Material.NOTE_BLOCK) return;
        NoteBlock noteBlock = (NoteBlock) clickedBlock.getBlockData();
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
        if(customBlockMaterial == null) return;
        event.setCancelled(true);
        if(customBlockMaterial != CustomBlockMaterial.DEFAULT){
            player.getInventory().setItem(event.getSlot(), customBlockMaterial.getItemStack());
        } else {
            player.getInventory().setItem(event.getSlot(), new ItemStack(Material.NOTE_BLOCK));
        }
    }
}
