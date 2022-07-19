package github.minersStudios.msBlock.crafts.planks;

import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;

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
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                "   ",
                "SPS",
                "   "
        );
        shapedRecipe.setIngredient('P', Material.ACACIA_PLANKS);
        shapedRecipe.setIngredient('S', Material.ACACIA_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftFramedBirchPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_BIRCH_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                "   ",
                "SPS",
                "   "
        );
        shapedRecipe.setIngredient('P', Material.BIRCH_PLANKS);
        shapedRecipe.setIngredient('S', Material.BIRCH_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftFramedCrimsonPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_CRIMSON_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                "   ",
                "SPS",
                "   "
        );
        shapedRecipe.setIngredient('P', Material.CRIMSON_PLANKS);
        shapedRecipe.setIngredient('S', Material.CRIMSON_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftFramedDarkOakPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_DARK_OAK_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                "   ",
                "SPS",
                "   "
        );
        shapedRecipe.setIngredient('P', Material.DARK_OAK_PLANKS);
        shapedRecipe.setIngredient('S', Material.DARK_OAK_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftFramedJunglePlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_JUNGLE_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                "   ",
                "SPS",
                "   "
        );
        shapedRecipe.setIngredient('P', Material.JUNGLE_PLANKS);
        shapedRecipe.setIngredient('S', Material.JUNGLE_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftFramedOakPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_OAK_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                "   ",
                "SPS",
                "   "
        );
        shapedRecipe.setIngredient('P', Material.OAK_PLANKS);
        shapedRecipe.setIngredient('S', Material.OAK_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftFramedSprucePlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_SPRUCE_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                "   ",
                "SPS",
                "   "
        );
        shapedRecipe.setIngredient('P', Material.SPRUCE_PLANKS);
        shapedRecipe.setIngredient('S', Material.SPRUCE_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftFramedWarpedPlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_WARPED_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                "   ",
                "SPS",
                "   "
        );
        shapedRecipe.setIngredient('P', Material.WARPED_PLANKS);
        shapedRecipe.setIngredient('S', Material.WARPED_SLAB);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftFramedMangrovePlanks() {
        CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.FRAMED_MANGROVE_PLANKS;
        ItemStack itemStack = customBlockMaterial.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, customBlockMaterial.name()), itemStack);
        shapedRecipe.shape(
                "   ",
                "SPS",
                "   "
        );
        shapedRecipe.setIngredient('P', Material.MANGROVE_PLANKS);
        shapedRecipe.setIngredient('S', Material.MANGROVE_SLAB);
        return shapedRecipe;
    }
}
