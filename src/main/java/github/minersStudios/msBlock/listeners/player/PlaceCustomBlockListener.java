package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.enumerators.CustomBlockMaterial;
import github.minersStudios.msBlock.objects.CustomBlock;
import github.minersStudios.msBlock.utils.BlockUtils;
import github.minersStudios.msBlock.utils.PlayerUtils;
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
        assert event.getClickedBlock() != null;
        Player player = event.getPlayer();
        if (
                event.getAction() != Action.RIGHT_CLICK_BLOCK
                || event.getPlayer().getInventory().getItemInMainHand().getType() != Material.PAPER
                || event.getHand() != EquipmentSlot.HAND
                || event.getPlayer().getGameMode() == GameMode.ADVENTURE
                || (event.getClickedBlock().getType().isInteractable() && event.getClickedBlock().getType() != Material.NOTE_BLOCK) && !player.isSneaking()
                || !BlockUtils.REPLACE.contains(event.getClickedBlock().getRelative(event.getBlockFace()).getType())
        ) return;
        Block replaceableBlock = BlockUtils.REPLACE.contains(event.getClickedBlock().getType()) ? event.getClickedBlock() : event.getClickedBlock().getRelative(event.getBlockFace());
        if(
                replaceableBlock.getType() == Material.TALL_GRASS && replaceableBlock.getLocation().clone().add(0.0f, -1.0f, 0.0f).getBlock().getType() == Material.TALL_GRASS
                || replaceableBlock.getType() == Material.TALL_SEAGRASS && replaceableBlock.getLocation().clone().add(0.0f, -1.0f, 0.0f).getBlock().getType() == Material.TALL_SEAGRASS
        ) {
            replaceableBlock.getLocation().clone().add(0.0f, -1.0f, 0.0f).getBlock().breakNaturally();
        }
        for (Entity nearbyEntity : replaceableBlock.getWorld().getNearbyEntities(replaceableBlock.getLocation().add(0.5d, 0.5d, 0.5d), 0.5d, 0.5d, 0.5d))
            if(!(nearbyEntity instanceof Item) && !(nearbyEntity instanceof ItemFrame)) return;
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInMainHand.getItemMeta();
        if (itemMeta == null || !itemMeta.hasCustomModelData()) return;
        if(replaceableBlock.getType() != Material.NOTE_BLOCK) PlayerUtils.playSwingAnimation(player, event.getHand());
        CustomBlock customBlock = new CustomBlock(replaceableBlock, player);
        customBlock.setCustomBlock(CustomBlockMaterial.getCustomBlockMaterialByCMD(itemInMainHand.getItemMeta().getCustomModelData()));
    }
}
