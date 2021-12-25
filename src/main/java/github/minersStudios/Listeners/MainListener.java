package github.minersStudios.Listeners;

import github.minersStudios.Mechanic.CustomBlock;
import github.minersStudios.Mechanic.CustomBlockMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class MainListener implements Listener {

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e){
        assert e.getClickedBlock() != null;

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        Player player = e.getPlayer();
        PlayerInventory inv = player.getInventory();
        Block block = e.getClickedBlock().getRelative(e.getBlockFace());

        if(!inv.getItemInMainHand().getType().isAir()){
            if(!Objects.requireNonNull(inv.getItemInMainHand().getItemMeta()).hasCustomModelData()) return;
            ItemMeta itemMeta = inv.getItemInMainHand().getItemMeta();

            if(inv.getItemInMainHand().getType().equals(Material.PAPER)){
                CustomBlock customBlock = new CustomBlock(block);
                customBlock.setCustomBlock(
                        itemMeta.getCustomModelData() == 1010 ? CustomBlockMaterial.VERTICAL_ACACIA_PLANKS :
                                itemMeta.getCustomModelData() == 1011 ? CustomBlockMaterial.VERTICAL_BIRCH_PLANKS :
                                        itemMeta.getCustomModelData() == 1012 ? CustomBlockMaterial.VERTICAL_WARPED_PLANKS :
                                                CustomBlockMaterial.VERTICAL_OAK_PLANKS
                        );
            }
        }else{
            Block block1 = e.getClickedBlock();
            CustomBlock customBlock = new CustomBlock(block1);

            if(customBlock.getCustomBlockMaterial() == CustomBlockMaterial.VERTICAL_ACACIA_PLANKS){
                player.sendMessage("acacia");
            } else if (customBlock.getCustomBlockMaterial() == CustomBlockMaterial.VERTICAL_OAK_PLANKS){
                player.sendMessage("oak");
            }

            player.sendMessage(customBlock.getBlock() + "\n" + customBlock.getInstrument() + "\n" + customBlock.getNote() + "\n" + customBlock.isPowered() + "\n" + customBlock.getCustomBlockMaterial());

        }




    }
}
