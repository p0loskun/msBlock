package github.minersStudios.msBlock.enumerators;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Tool type used for custom block dig speed and force tool param
 */
@Nonnull
public enum ToolType {
    HAND, SWORD, PICKAXE, AXE, SHOVEL, HOE, SHEARS;

    /**
     * @param itemStack item in hand
     *
     * @return ToolType from item in hand
     */
    public static ToolType getToolType(@Nonnull ItemStack itemStack) {
        if(itemStack.getType().toString().contains("SHEARS")){
            return SHEARS;
        } else if(itemStack.getType().toString().contains("_HOE")){
            return HOE;
        } else if(itemStack.getType().toString().contains("_SHOVEL")){
            return SHOVEL;
        } else if(itemStack.getType().toString().contains("_AXE")){
            return AXE;
        } else if(itemStack.getType().toString().contains("_PICKAXE")){
            return PICKAXE;
        } else if(itemStack.getType().toString().contains("_SWORD")){
            return SWORD;
        }
        return HAND;
    }
}
