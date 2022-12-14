package com.github.minersstudios.msblock.customBlock;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum ToolType {
	HAND("HAND"),
	SWORD("_SWORD"),
	PICKAXE("_PICKAXE"),
	AXE("_AXE"),
	SHOVEL("_SHOVEL"),
	HOE("_HOE"),
	SHEARS("SHEARS");

	private final @NotNull String itemTypeName;

	ToolType(@NotNull String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	/**
	 * @param itemInMainHand item in main hand
	 * @return ToolType from item in main hand
	 */
	public static @NotNull ToolType getToolType(@NotNull ItemStack itemInMainHand) {
		for (ToolType toolType : ToolType.values()) {
			if (itemInMainHand.getType().name().contains(toolType.itemTypeName)) {
				return toolType;
			}
		}
		return HAND;
	}
}
