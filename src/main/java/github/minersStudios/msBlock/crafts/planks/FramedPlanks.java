package github.minersStudios.msBlock.crafts.planks;

import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.Main.pluginNameInLowerCase;

public class FramedPlanks {

	public static void addRecipes() {
		Bukkit.addRecipe(craftFramedAcaciaPlanks());
		Bukkit.addRecipe(craftFramedBirchPlanks());
		Bukkit.addRecipe(craftFramedCrimsonPlanks());
		Bukkit.addRecipe(craftFramedDarkOakPlanks());
		Bukkit.addRecipe(craftFramedJunglePlanks());
		Bukkit.addRecipe(craftFramedOakPlanks());
		Bukkit.addRecipe(craftFramedSprucePlanks());
		Bukkit.addRecipe(craftFramedWarpedPlanks());
		Bukkit.addRecipe(craftFramedMangrovePlanks());
	}

	@Nonnull
	public static ShapedRecipe craftFramedAcaciaPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_ACACIA_PLANKS;
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape("SS")
				.setIngredient('S', Material.ACACIA_SLAB);
		shapedRecipe.setGroup(pluginNameInLowerCase + ":framed_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftFramedBirchPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_BIRCH_PLANKS;
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape("SS")
				.setIngredient('S', Material.BIRCH_SLAB);
		shapedRecipe.setGroup(pluginNameInLowerCase + ":framed_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftFramedCrimsonPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_CRIMSON_PLANKS;
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape("SS")
				.setIngredient('S', Material.CRIMSON_SLAB);
		shapedRecipe.setGroup(pluginNameInLowerCase + ":framed_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftFramedDarkOakPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_DARK_OAK_PLANKS;
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape("SS")
				.setIngredient('S', Material.DARK_OAK_SLAB);
		shapedRecipe.setGroup(pluginNameInLowerCase + ":framed_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftFramedJunglePlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_JUNGLE_PLANKS;
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape("SS")
				.setIngredient('S', Material.JUNGLE_SLAB);
		shapedRecipe.setGroup(pluginNameInLowerCase + ":framed_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftFramedOakPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_OAK_PLANKS;
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape("SS")
				.setIngredient('S', Material.OAK_SLAB);
		shapedRecipe.setGroup(pluginNameInLowerCase + ":framed_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftFramedSprucePlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_SPRUCE_PLANKS;
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape("SS")
				.setIngredient('S', Material.SPRUCE_SLAB);
		shapedRecipe.setGroup(pluginNameInLowerCase + ":framed_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftFramedWarpedPlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_WARPED_PLANKS;
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape("SS")
				.setIngredient('S', Material.WARPED_SLAB);
		shapedRecipe.setGroup(pluginNameInLowerCase + ":framed_planks");
		return shapedRecipe;
	}

	@Nonnull
	public static ShapedRecipe craftFramedMangrovePlanks() {
		CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_MANGROVE_PLANKS;
		ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
				.shape("SS")
				.setIngredient('S', Material.MANGROVE_SLAB);
		shapedRecipe.setGroup(pluginNameInLowerCase + ":framed_planks");
		return shapedRecipe;
	}
}