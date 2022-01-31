package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.enumerators.CustomBlockMaterial;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFrameInteractListener implements Listener {

    @EventHandler
    public void onPutInItemFrameCustomBlock(PlayerInteractEntityEvent event){
        if (!(event.getRightClicked() instanceof ItemFrame)) return;
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType().isAir()) return;
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand().clone();
        ItemMeta itemMeta = itemInMainHand.getItemMeta();
        if(itemMeta == null || !itemMeta.hasCustomModelData() || CustomBlockMaterial.getCustomBlockMaterialByCMD(itemMeta.getCustomModelData()) == null) return;
        event.setCancelled(true);
        ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
        itemFrame.setCustomName(itemMeta.getDisplayName());
        itemMeta.setDisplayName(null);
        itemInMainHand.setItemMeta(itemMeta);
        itemFrame.setItem(itemInMainHand);
        if(player.getGameMode() != GameMode.CREATIVE) player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
    }

    @EventHandler
    public void onHangingBreakByEntity(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof ItemFrame) || !(event.getDamager() instanceof Player && ((Player) event.getDamager()).getGameMode() != GameMode.CREATIVE || event.getDamager() instanceof Projectile)) return;
        if(event.getDamager() instanceof Projectile && !(((Projectile) event.getDamager()).getShooter() instanceof Player)) return;
        if(event.getDamager() instanceof Projectile) event.getDamager().remove();
        ItemFrame itemFrame = (ItemFrame) event.getEntity();
        if(itemFrame.getItem().getType().isAir()) return;
        ItemStack itemInFrame = itemFrame.getItem().clone();
        ItemMeta itemMeta = itemInFrame.getItemMeta();
        if(itemMeta == null || !itemMeta.hasCustomModelData() || CustomBlockMaterial.getCustomBlockMaterialByCMD(itemMeta.getCustomModelData()) == null) return;
        event.setCancelled(true);
        itemMeta.setDisplayName(itemFrame.getName());
        itemInFrame.setItemMeta(itemMeta);
        itemFrame.setItem(null);
        itemFrame.setCustomName(null);
        itemFrame.getWorld().playSound(itemFrame.getLocation(), Sound.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        itemFrame.getWorld().dropItemNaturally(itemFrame.getLocation(), itemInFrame);
    }
}
