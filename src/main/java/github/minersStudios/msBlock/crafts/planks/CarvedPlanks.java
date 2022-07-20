package github.minersStudios.msBlock.crafts.planks;

import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.Main.pluginNameInLowerCase;

public class CarvedPlanks {

    public static void addRecipes() {
        Bukkit.addRecipe(craftCarvedAcaciaPlanks());
        Bukkit.addRecipe(craftCarvedBirchPlanks());
        Bukkit.addRecipe(craftCarvedCrimsonPlanks());
        Bukkit.addRecipe(craftCarvedDarkOakPlanks());
        Bukkit.addRecipe(craftCarvedJunglePlanks());
        Bukkit.addRecipe(craftCarvedOakPlanks());
        Bukkit.addRecipe(craftCarvedSprucePlanks());
        Bukkit.addRecipe(craftCarvedWarpedPlanks());
        Bukkit.addRecipe(craftCarvedMangrovePlanks());
    }

    @Nonnull
    public static ShapedRecipe craftCarvedAcaciaPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_ACACIA_PLANKS;
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
                .shape("S", "S")
                .setIngredient('S', Material.ACACIA_SLAB);
        shapedRecipe.setGroup(pluginNameInLowerCase + ":carved_planks");
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedBirchPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_BIRCH_PLANKS;
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
                .shape("S", "S")
                .setIngredient('S', Material.BIRCH_SLAB);
        shapedRecipe.setGroup(pluginNameInLowerCase + ":carved_planks");
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedCrimsonPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_CRIMSON_PLANKS;
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
                .shape("S", "S")
                .setIngredient('S', Material.CRIMSON_SLAB);
        shapedRecipe.setGroup(pluginNameInLowerCase + ":carved_planks");
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedDarkOakPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_DARK_OAK_PLANKS;
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
                .shape("S", "S")
                .setIngredient('S', Material.DARK_OAK_SLAB);
        shapedRecipe.setGroup(pluginNameInLowerCase + ":carved_planks");
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedJunglePlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_JUNGLE_PLANKS;
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
                .shape("S", "S")
                .setIngredient('S', Material.JUNGLE_SLAB);
        shapedRecipe.setGroup(pluginNameInLowerCase + ":carved_planks");
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedOakPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_OAK_PLANKS;
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
                .shape("S", "S")
                .setIngredient('S', Material.OAK_SLAB);
        shapedRecipe.setGroup(pluginNameInLowerCase + ":carved_planks");
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedSprucePlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_SPRUCE_PLANKS;
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
                .shape("S", "S")
                .setIngredient('S', Material.SPRUCE_SLAB);
        shapedRecipe.setGroup(pluginNameInLowerCase + ":carved_planks");
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedWarpedPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_WARPED_PLANKS;
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
                .shape("S", "S")
                .setIngredient('S', Material.WARPED_SLAB);
        shapedRecipe.setGroup(pluginNameInLowerCase + ":carved_planks");
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedMangrovePlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_MANGROVE_PLANKS;
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), customBlockMaterial.getItemStack())
                .shape("S", "S")
                .setIngredient('S', Material.MANGROVE_SLAB);
        shapedRecipe.setGroup(pluginNameInLowerCase + ":carved_planks");
        return shapedRecipe;
    }
}
