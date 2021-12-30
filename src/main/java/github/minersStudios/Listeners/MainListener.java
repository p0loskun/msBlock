package github.minersStudios.Listeners;

import github.minersStudios.Mechanic.CustomBlock;
import github.minersStudios.Mechanic.CustomBlockMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class MainListener implements Listener {

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        assert e.getClickedBlock() != null;
        if (
                e.getAction() != Action.RIGHT_CLICK_BLOCK ||
                        e.getPlayer().getInventory().getItemInMainHand().getType() != Material.PAPER ||
                        e.getHand() != EquipmentSlot.HAND
        ) return;

        Location locb = e.getClickedBlock().getRelative(e.getBlockFace()).getLocation().add(0.5D, 0.5D, 0.5D);
        for (Entity ignored : locb.getChunk().getWorld().getNearbyEntities(locb, 0.5, 0.5, 0.5)) return;

        Player player = e.getPlayer();
        PlayerInventory inv = player.getInventory();
        Block block = e.getClickedBlock().getRelative(e.getBlockFace());

        ItemStack itemInMainHand = inv.getItemInMainHand();
        ItemMeta itemMeta = itemInMainHand.getItemMeta();
        assert itemMeta != null;

        if (!itemMeta.hasCustomModelData()) return;
        CustomBlock customBlock = new CustomBlock(block, player);
        player.sendMessage("a");
        switch (itemMeta.getCustomModelData()) {
            case 1000:
                customBlock.setCustomBlock(CustomBlockMaterial.VERTICAL_ACACIA_PLANKS);
                break;
            case 1001:
                customBlock.setCustomBlock(CustomBlockMaterial.VERTICAL_BIRCH_PLANKS);
                break;
            case 1002:
                customBlock.setCustomBlock(CustomBlockMaterial.VERTICAL_CRIMSON_PLANKS);
                break;
        }
    }
}
