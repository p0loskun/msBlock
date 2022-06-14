package github.minersStudios.msBlock.listeners.block;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

public class BlockDamageListener implements Listener {
    @EventHandler
    public void onBlockDamage(@Nonnull BlockDamageEvent event) {
        Player player = event.getPlayer();
        if (event.getBlock().getType() == Material.NOTE_BLOCK) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, true, false, false));
        } else if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }
    }
}
