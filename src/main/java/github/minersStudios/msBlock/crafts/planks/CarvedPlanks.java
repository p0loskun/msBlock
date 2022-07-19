package github.minersStudios.msBlock.crafts.planks;

import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;

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
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                " S ",
                " P ",
                " S "
        );
        shapedRecipe.setIngredient('P', Material.ACACIA_PLANKS);
        shapedRecipe.setIngredient('S', Material.ACACIA_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedBirchPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_BIRCH_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                " S ",
                " P ",
                " S "
        );
        shapedRecipe.setIngredient('P', Material.BIRCH_PLANKS);
        shapedRecipe.setIngredient('S', Material.BIRCH_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedCrimsonPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_CRIMSON_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                " S ",
                " P ",
                " S "
        );
        shapedRecipe.setIngredient('P', Material.CRIMSON_PLANKS);
        shapedRecipe.setIngredient('S', Material.CRIMSON_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedDarkOakPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_DARK_OAK_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                " S ",
                " P ",
                " S "
        );
        shapedRecipe.setIngredient('P', Material.DARK_OAK_PLANKS);
        shapedRecipe.setIngredient('S', Material.DARK_OAK_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedJunglePlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_JUNGLE_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                " S ",
                " P ",
                " S "
        );
        shapedRecipe.setIngredient('P', Material.JUNGLE_PLANKS);
        shapedRecipe.setIngredient('S', Material.JUNGLE_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedOakPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_OAK_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                " S ",
                " P ",
                " S "
        );
        shapedRecipe.setIngredient('P', Material.OAK_PLANKS);
        shapedRecipe.setIngredient('S', Material.OAK_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedSprucePlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_SPRUCE_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                " S ",
                " P ",
                " S "
        );
        shapedRecipe.setIngredient('P', Material.SPRUCE_PLANKS);
        shapedRecipe.setIngredient('S', Material.SPRUCE_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedWarpedPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_WARPED_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                " S ",
                " P ",
                " S "
        );
        shapedRecipe.setIngredient('P', Material.WARPED_PLANKS);
        shapedRecipe.setIngredient('S', Material.WARPED_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftCarvedMangrovePlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.CARVED_MANGROVE_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                " S ",
                " P ",
                " S "
        );
        shapedRecipe.setIngredient('P', Material.MANGROVE_PLANKS);
        shapedRecipe.setIngredient('S', Material.MANGROVE_SLAB);
        return shapedRecipe;
    }
}
