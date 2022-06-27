package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public class PlaceCustomBlockListener implements Listener {

    @EventHandler
    public void PlayerInteractEvent(@Nonnull PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        Player player = event.getPlayer();
        if (
                event.getAction() != Action.RIGHT_CLICK_BLOCK
                        || event.getPlayer().getInventory().getItemInMainHand().getType() != Material.PAPER
                        || event.getHand() != EquipmentSlot.HAND
                        || event.getPlayer().getGameMode() == GameMode.ADVENTURE
                        || (event.getClickedBlock().getType().isInteractable() && event.getClickedBlock().getType() != Material.NOTE_BLOCK) && !player.isSneaking()
                        || !BlockUtils.REPLACE.contains(event.getClickedBlock().getRelative(event.getBlockFace()).getType())
        ) return;
        Block replaceableBlock =
                BlockUtils.REPLACE.contains(event.getClickedBlock().getType())
                ? event.getClickedBlock()
                : event.getClickedBlock().getRelative(event.getBlockFace());
        for (Entity nearbyEntity : replaceableBlock.getWorld().getNearbyEntities(replaceableBlock.getLocation().add(0.5d, 0.5d, 0.5d), 0.5d, 0.5d, 0.5d)) {
            if (!(nearbyEntity instanceof Item) && !(nearbyEntity instanceof ItemFrame)) return;
        }
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInMainHand.getItemMeta();
        if (itemMeta == null || !itemMeta.hasCustomModelData()) return;
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(itemMeta.getCustomModelData());
        if(customBlockMaterial == null) return;
        customBlockMaterial.setCustomBlock(replaceableBlock, player);
    }
}
