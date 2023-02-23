package com.github.minersstudios.msblock.customblock;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum ToolTier {
	HAND("HAND", 0.1f),
	WOOD("WOODEN_", 0.3f),
	STONE("STONE_", 0.45f),
	IRON("IRON_", 0.56f),
	GOLD("GOLDEN_", 0.8f),
	DIAMOND("DIAMOND_", 0.65f),
	NETHERITE("NETHERITE_", 0.8f);

	private final @NotNull String itemTierName;
	private final float speed;

	ToolTier(
			@NotNull String itemTierName,
			float speed
	) {
		this.itemTierName = itemTierName;
		this.speed = speed;
	}

	/**
	 * @param itemInMainHand item in main hand
	 * @return ToolTier from item in main hand
	 */
	public static @NotNull ToolTier getToolTier(@NotNull ItemStack itemInMainHand) {
		if (itemInMainHand.getType() == Material.SHEARS) return ToolTier.NETHERITE;
		for (ToolTier toolTier : ToolTier.values()) {
			if (itemInMainHand.getType().name().contains(toolTier.itemTierName)) {
				return toolTier;
			}
		}
		return HAND;
	}

	public float getSpeed() {
		return speed;
	}
}
