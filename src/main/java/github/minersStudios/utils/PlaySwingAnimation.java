package github.minersStudios.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.Nonnull;

public class PlaySwingAnimation {
    public PlaySwingAnimation(@Nonnull Player player, @Nonnull EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.HAND) {
            player.swingMainHand();
        } else {
            player.swingOffHand();
        }
    }
}
