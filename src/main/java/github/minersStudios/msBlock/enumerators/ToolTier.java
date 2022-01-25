package github.minersStudios.msBlock.enumerators;

import org.bukkit.inventory.ItemStack;

public enum ToolTier
{
    ANY(0.1f),
    WOOD(0.3f),
    STONE(0.45f),
    IRON(0.56f),
    GOLD(0.8f),
    DIAMOND(0.65f),
    NETHERITE(0.8f);

    public final float speed;

    ToolTier(float speed) {
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
