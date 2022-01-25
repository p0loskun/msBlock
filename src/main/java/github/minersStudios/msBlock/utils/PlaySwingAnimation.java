package github.minersStudios.msBlock.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.Nonnull;

public class PlaySwingAnimation {

    /**
     * Swings player hand
     *
     * @param player player who will swing hand
     * @param equipmentSlot equipment slot which used for checking what hand will swing
     */
    public PlaySwingAnimation(@Nonnull Player player, @Nonnull EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.HAND) {
            player.swingMainHand();
        } else {
            player.swingOffHand();
        }
    }
}
