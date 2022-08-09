package com.github.minersstudios.msBlock.crafts.dumbass;

import com.github.minersstudios.msBlock.Main;
import com.github.minersstudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;

public class HacpalBlock {

	public static void addRecipes() {
		Bukkit.addRecipe(craftHacpalBlock());
	}

	@Nonnull
	public static ShapedRecipe craftHacpalBlock() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.HACPAL_BLOCK;
		return new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape(
						"WWW",
						"WKW",
						"WWW"
				).setIngredient('K', Material.KELP)
				.setIngredient('W', Material.WHITE_WOOL);
	}
}
