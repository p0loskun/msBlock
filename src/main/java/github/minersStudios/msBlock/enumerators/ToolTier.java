package github.minersStudios.msBlock.enumerators;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Tool tier with speed float used for custom block dig speed
 */
@Nonnull
public enum ToolTier {
    HAND("HAND", 0.1f),
    WOOD("WOODEN_", 0.3f),
    STONE("STONE_", 0.45f),
    IRON("IRON_", 0.56f),
    GOLD("GOLDEN_", 0.8f),
    DIAMOND("DIAMOND_", 0.65f),
    NETHERITE("NETHERITE_", 0.8f);


    /** Tool tier float speed */
    @Getter private final float speed;

    /** Tool tier name */
    private final String itemTierName;

    ToolTier(String itemTierName, float speed) {
        this.itemTierName = itemTierName;
        this.speed = speed;
    }

    /**
     * @param itemStack item in hand
     *
     * @return ToolTier from item in hand
     */
    @Nonnull
    public static ToolTier getToolTier(@Nonnull ItemStack itemStack) {
        for(ToolTier toolTier : ToolTier.values()) {
            if(itemStack.getType().name().contains(toolTier.itemTierName)) return toolTier;
        }
        return HAND;
    }
}
