package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.utils.PlayerUtils.steps;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(@Nonnull PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getTargetBlockExact(5),
                bottomBlock = player.getLocation().subtract(0.0d, 0.5d, 0.0d).getBlock();
        if (!player.hasPotionEffect(PotionEffectType.SLOW_DIGGING) && block != null && block.getType() == Material.NOTE_BLOCK)
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, true, false, false));

        boolean isRequiredMaterial = bottomBlock.getType() == Material.NOTE_BLOCK || BlockUtils.isWoodenSound(bottomBlock.getType());
        if (
                isRequiredMaterial
                && event.getTo() != null
                && !player.isSneaking()
                && bottomBlock.getType().isSolid()
        ) {
            Location from = event.getFrom().clone(),
                    to = event.getTo().clone();
            from.setY(0);
            to.setY(0);
            double distance = from.distance(to);
            if (distance == 0.0d) return;
            double fullDistance = steps.containsKey(player) ? steps.get(player) + distance : 1.0d;
            steps.put(player, fullDistance > 1.25d ? 0.0d : fullDistance);
            if (fullDistance > 1.25d) {
                if (bottomBlock.getBlockData() instanceof NoteBlock noteBlock) {
                    CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).playStepSound(bottomBlock);
                } else {
                    bottomBlock.getWorld().playSound(bottomBlock.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.step", 0.3f, 0.9f);
                }
            }
        } else if (!isRequiredMaterial) {
            steps.remove(player);
        }
    }
}
