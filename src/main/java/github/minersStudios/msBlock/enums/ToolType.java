package github.minersStudios.msBlock.enums;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

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
	 * @param itemInMainHand item in main hand
	 * @return ToolType from item in main hand
	 */
	@Nonnull
	public static ToolType getToolType(@Nonnull ItemStack itemInMainHand) {
		for (ToolType toolType : ToolType.values())
			if (itemInMainHand.getType().name().contains(toolType.itemTypeName))
				return toolType;
		return HAND;
	}
}