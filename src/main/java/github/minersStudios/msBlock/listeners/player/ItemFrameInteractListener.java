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

import javax.annotation.Nonnull;

public class ItemFrameInteractListener implements Listener {

    @EventHandler
    public void onPutInItemFrameCustomBlock(@Nonnull PlayerInteractEntityEvent event){
        if (!(event.getRightClicked() instanceof ItemFrame)) return;
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType().isAir()) return;
        ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
        if(!itemFrame.getItem().getType().isAir()) return;
        ItemStack originalItemInMainHand = player.getInventory().getItemInMainHand(),
                itemInMainHand = originalItemInMainHand.clone();
        ItemMeta itemMeta = itemInMainHand.getItemMeta(),
                originalItemMeta = originalItemInMainHand.getItemMeta();
        if(originalItemMeta == null || itemMeta == null || !itemMeta.hasCustomModelData() || CustomBlockMaterial.getCustomBlockMaterial(itemMeta.getCustomModelData()) == null) return;
        event.setCancelled(true);
        originalItemMeta.setDisplayName(originalItemMeta.getDisplayName() + "");
        originalItemInMainHand.setItemMeta(originalItemMeta);
        itemFrame.setCustomName(itemMeta.getDisplayName());
        itemMeta.setDisplayName(null);
        itemInMainHand.setItemMeta(itemMeta);
        itemFrame.setItem(itemInMainHand);
        if(player.getGameMode() != GameMode.CREATIVE) player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
    }

    @EventHandler
    public void onDamageByEntity(@Nonnull EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof ItemFrame) || !(event.getDamager() instanceof Player && ((Player) event.getDamager()).getGameMode() != GameMode.CREATIVE || event.getDamager() instanceof Projectile)) return;
        if(event.getDamager() instanceof Projectile && !(((Projectile) event.getDamager()).getShooter() instanceof Player)) return;
        if(event.getDamager() instanceof Projectile) event.getDamager().remove();
        ItemFrame itemFrame = (ItemFrame) event.getEntity();
        if(itemFrame.getItem().getType().isAir()) return;
        ItemStack itemInFrame = itemFrame.getItem().clone();
        ItemMeta itemMeta = itemInFrame.getItemMeta();
        if(itemMeta == null || !itemMeta.hasCustomModelData() || CustomBlockMaterial.getCustomBlockMaterial(itemMeta.getCustomModelData()) == null) return;
        event.setCancelled(true);
        itemMeta.setDisplayName(itemFrame.getName());
        itemInFrame.setItemMeta(itemMeta);
        itemFrame.setItem(null);
        itemFrame.setCustomName(null);
        itemFrame.getWorld().playSound(itemFrame.getLocation(), Sound.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        itemFrame.getWorld().dropItemNaturally(itemFrame.getLocation(), itemInFrame);
    }
}
