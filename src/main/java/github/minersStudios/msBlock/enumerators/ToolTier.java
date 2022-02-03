package github.minersStudios.msBlock.enumerators;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Tool tier with speed float used for custom block dig speed
 */
@Nonnull
public enum ToolTier {
    ANY(0.1f),
    WOOD(0.3f),
    STONE(0.45f),
    IRON(0.56f),
    GOLD(0.8f),
    DIAMOND(0.65f),
    NETHERITE(0.8f);


    /** Tool tier float speed */
    @Getter private final float speed;

    ToolTier(float speed) {
        this.speed = speed;
    }

    /**
     * @param itemStack item in hand
     *
     * @return ToolTier from item in hand
     */
    public static ToolTier fromItemStack(@Nonnull ItemStack itemStack) {
        if (itemStack.getType().toString().contains("WOODEN_")) {
            return WOOD;
        } else if (itemStack.getType().toString().contains("STONE_")) {
            return STONE;
        } else if (itemStack.getType().toString().contains("IRON_")) {
            return IRON;
        } else if (itemStack.getType().toString().contains("GOLDEN_")) {
            return GOLD;
        } else if (itemStack.getType().toString().contains("DIAMOND_")) {
            return DIAMOND;
        } else if (itemStack.getType().toString().contains("NETHERITE_")) {
            return NETHERITE;
        }
        return ANY;
    }
}
