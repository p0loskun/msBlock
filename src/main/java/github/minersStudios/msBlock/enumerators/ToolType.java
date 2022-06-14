package github.minersStudios.msBlock.enumerators;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Tool type used for custom block dig speed and force tool param
 */
@Nonnull
public enum ToolType {
    HAND("HAND"),
    SWORD("_SWORD"),
    PICKAXE("_PICKAXE"),
    AXE("_AXE"),
    SHOVEL("_SHOVEL"),
    HOE("_HOE"),
    SHEARS("SHEARS");

    private final String itemTypeName;

    ToolType(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    /**
     * @param itemStack item in hand
     *
     * @return ToolType from item in hand
     */
    public static ToolType getToolType(@Nonnull ItemStack itemStack) {
        for(ToolType toolType : ToolType.values()) {
            if(itemStack.getType().name().contains(toolType.itemTypeName)) return toolType;
        }
        return HAND;
    }
}
