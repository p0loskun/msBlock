package com.github.MinersStudios.msBlock.crafts.planks;

import com.github.MinersStudios.msBlock.Main;
import com.github.MinersStudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;

public class VerticalPlanks {

	public static void addRecipes() {
		Bukkit.addRecipe(craftVerticalAcaciaPlanks());
		Bukkit.addRecipe(craftVerticalBirchPlanks());
		Bukkit.addRecipe(craftVerticalCrimsonPlanks());
		Bukkit.addRecipe(craftVerticalDarkOakPlanks());
		Bukkit.addRecipe(craftVerticalJunglePlanks());
		Bukkit.addRecipe(craftVerticalOakPlanks());
		Bukkit.addRecipe(craftVerticalSprucePlanks());
		Bukkit.addRecipe(craftVerticalWarpedPlanks());
		Bukkit.addRecipe(craftVerticalMangrovePlanks());
	}

	@Nonnull
	public static ShapedRecipe craftVerticalAcaciaPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.VERTICAL_ACACIA_PLANKS;
		ItemStack itemStack = customBlockMaterial.getItemStack();
		itemStack.setAmount(3);
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), itemStack)
				.shape("P", "P", "P")
				.setIngredient('P', Material.ACACIA_PLANKS);
		shapedRecipe.setGroup(Main.getPluginNameInLowerCase() + ":vertical_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftVerticalBirchPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.VERTICAL_BIRCH_PLANKS;
		ItemStack itemStack = customBlockMaterial.getItemStack();
		itemStack.setAmount(3);
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), itemStack)
				.shape("P", "P", "P")
				.setIngredient('P', Material.BIRCH_PLANKS);
		shapedRecipe.setGroup(Main.getPluginNameInLowerCase() + ":vertical_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftVerticalCrimsonPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.VERTICAL_CRIMSON_PLANKS;
		ItemStack itemStack = customBlockMaterial.getItemStack();
		itemStack.setAmount(3);
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), itemStack)
				.shape("P", "P", "P")
				.setIngredient('P', Material.CRIMSON_PLANKS);
		shapedRecipe.setGroup(Main.getPluginNameInLowerCase() + ":vertical_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftVerticalDarkOakPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.VERTICAL_DARK_OAK_PLANKS;
		ItemStack itemStack = customBlockMaterial.getItemStack();
		itemStack.setAmount(3);
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), itemStack)
				.shape("P", "P", "P")
				.setIngredient('P', Material.DARK_OAK_PLANKS);
		shapedRecipe.setGroup(Main.getPluginNameInLowerCase() + ":vertical_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftVerticalJunglePlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.VERTICAL_JUNGLE_PLANKS;
		ItemStack itemStack = customBlockMaterial.getItemStack();
		itemStack.setAmount(3);
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), itemStack)
				.shape("P", "P", "P")
				.setIngredient('P', Material.JUNGLE_PLANKS);
		shapedRecipe.setGroup(Main.getPluginNameInLowerCase() + ":vertical_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftVerticalOakPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.VERTICAL_OAK_PLANKS;
		ItemStack itemStack = customBlockMaterial.getItemStack();
		itemStack.setAmount(3);
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), itemStack)
				.shape("P", "P", "P")
				.setIngredient('P', Material.OAK_PLANKS);
		shapedRecipe.setGroup(Main.getPluginNameInLowerCase() + ":vertical_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftVerticalSprucePlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.VERTICAL_SPRUCE_PLANKS;
		ItemStack itemStack = customBlockMaterial.getItemStack();
		itemStack.setAmount(3);
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), itemStack)
				.shape("P", "P", "P")
				.setIngredient('P', Material.SPRUCE_PLANKS);
		shapedRecipe.setGroup(Main.getPluginNameInLowerCase() + ":vertical_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftVerticalWarpedPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.VERTICAL_WARPED_PLANKS;
		ItemStack itemStack = customBlockMaterial.getItemStack();
		itemStack.setAmount(3);
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), itemStack)
				.shape("P", "P", "P")
				.setIngredient('P', Material.WARPED_PLANKS);
		shapedRecipe.setGroup(Main.getPluginNameInLowerCase() + ":vertical_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftVerticalMangrovePlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.VERTICAL_MANGROVE_PLANKS;
		ItemStack itemStack = customBlockMaterial.getItemStack();
		itemStack.setAmount(3);
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlockMaterial.name()), itemStack)
				.shape("P", "P", "P")
				.setIngredient('P', Material.MANGROVE_PLANKS);
		shapedRecipe.setGroup(Main.getPluginNameInLowerCase() + ":vertical_planks");
		return shapedRecipe;
	}
}
