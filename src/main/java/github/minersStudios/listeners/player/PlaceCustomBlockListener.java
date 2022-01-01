package github.minersStudios.listeners.player;

import github.minersStudios.enumerators.CustomBlockMaterial;
import github.minersStudios.objects.CustomBlock;
import github.minersStudios.utils.PlaySwingAnimation;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceCustomBlockListener implements Listener {

    @EventHandler
    public void PlayerInteractEvent(org.bukkit.event.player.PlayerInteractEvent event) {
        assert event.getClickedBlock() != null;
        if (
                event.getAction() != Action.RIGHT_CLICK_BLOCK ||
                event.getPlayer().getInventory().getItemInMainHand().getType() != Material.PAPER ||
                event.getHand() != EquipmentSlot.HAND
        ) return;
        Block clickedBlock = event.getClickedBlock(),
                blockAtFace = clickedBlock.getRelative(event.getBlockFace());

        for (Entity nearbyEntity : clickedBlock.getWorld().getNearbyEntities(blockAtFace.getLocation().add(0.5d, 0.5d, 0.5d), 0.5d, 0.5d, 0.5d))
            if(!(nearbyEntity instanceof Item)) return;

        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInMainHand.getItemMeta();
        assert itemMeta != null;

        if (!itemMeta.hasCustomModelData()) return;
        if(clickedBlock.getType() != Material.NOTE_BLOCK) new PlaySwingAnimation(player, event.getHand());

        CustomBlock customBlock = new CustomBlock(blockAtFace, player);
        customBlock.setCustomBlock(CustomBlockMaterial.getCustomBlockMaterialByCMD(itemInMainHand.getItemMeta().getCustomModelData()));
    }

}
