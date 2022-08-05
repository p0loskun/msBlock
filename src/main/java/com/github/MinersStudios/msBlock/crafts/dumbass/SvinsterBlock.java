package com.github.MinersStudios.msBlock.crafts.dumbass;

import com.github.MinersStudios.msBlock.Main;
import com.github.MinersStudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;

public class SvinsterBlock {

	public static void addRecipes() {
		Bukkit.addRecipe(craftSvinsterBlock());
	}

	@Nonnull
	public static ShapedRecipe craftSvinsterBlock() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.SVINSTER_BLOCK;
		return new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape(
						"PPP",
						"PDP",
						"PPP"
				).setIngredient('P', Material.PORKCHOP)
				.setIngredient('D', Material.BROWN_DYE);
	}
}
