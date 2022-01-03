package github.minersStudios.enumerators;

import org.bukkit.inventory.ItemStack;

public enum ToolTier
{
    ANY(0, 0.1f),
    WOOD(1, 0.3f),
    STONE(2, 0.45f),
    IRON(3, 0.56f),
    GOLD(1, 0.8f),
    DIAMOND(4, 0.65f),
    NETHERITE(5, 0.8f);

    public final int order;
    public final float speed;

    ToolTier(int order, float speed) {
        this.order = order;
        this.speed = speed;
    }

    public static ToolTier fromItemStack(ItemStack stack) {
        if (stack.getType().toString().contains("WOODEN_")) {
            return WOOD;
        } else if (stack.getType().toString().contains("STONE_")) {
            return STONE;
        } else if (stack.getType().toString().contains("IRON_")) {
            return IRON;
        } else if (stack.getType().toString().contains("GOLDEN_")) {
            return GOLD;
        } else if (stack.getType().toString().contains("DIAMOND_")) {
            return DIAMOND;
        } else if (stack.getType().toString().contains("NETHERITE_")) {
            return NETHERITE;
        }
        return ANY;
    }

}
