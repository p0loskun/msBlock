package github.minersStudios.enumerators;

import org.bukkit.inventory.ItemStack;

public enum ToolType {
    HAND,
    SWORD,
    PICKAXE,
    AXE,
    SHOVEL,
    HOE,
    SHEARS;

    public static ToolType getTool(ItemStack stack)
    {
        if(stack.getType().toString().contains("SHEARS")){
            return SHEARS;
        } else if(stack.getType().toString().contains("_HOE")){
            return HOE;
        } else if(stack.getType().toString().contains("_SHOVEL")){
            return SHOVEL;
        } else if(stack.getType().toString().contains("_AXE")){
            return AXE;
        } else if(stack.getType().toString().contains("_PICKAXE")){
            return PICKAXE;
        } else if(stack.getType().toString().contains("_SWORD")){
            return SWORD;
        }
        return HAND;
    }
}
