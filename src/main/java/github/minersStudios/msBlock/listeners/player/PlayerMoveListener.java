package github.minersStudios.msBlock.listeners.player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(@Nonnull PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getTargetBlockExact(5);
        if (!player.hasPotionEffect(PotionEffectType.SLOW_DIGGING) && block != null && block.getType() == Material.NOTE_BLOCK)
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, true, false, false));
    }
}
