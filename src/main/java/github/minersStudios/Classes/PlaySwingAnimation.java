package github.minersStudios.Classes;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import javax.annotation.Nonnull;

public class PlaySwingAnimation {
    public void swingHand(@Nonnull Player player, @Nonnull EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case HAND:
                player.swingMainHand();
                break;
            case OFF_HAND:
                player.swingOffHand();
                break;
        }
    }
}
