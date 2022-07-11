package github.minersStudios.msBlock.crafts.planks;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;

public class VerticalPlanks {

    public static void addRecipes(){
        Bukkit.addRecipe(craftVerticalAcaciaPlanks());
        Bukkit.addRecipe(craftVerticalBirchPlanks());
        Bukkit.addRecipe(craftVerticalCrimsonPlanks());
        Bukkit.addRecipe(craftVerticalDarkOakPlanks());
        Bukkit.addRecipe(craftVerticalJunglePlanks());
        Bukkit.addRecipe(craftVerticalOakPlanks());
        Bukkit.addRecipe(craftVerticalSprucePlanks());
        Bukkit.addRecipe(craftVerticalWarpedPlanks());
    }

    @Nonnull
    public static ShapedRecipe craftVerticalAcaciaPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_ACACIA_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_acacia_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.ACACIA_PLANKS);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftVerticalBirchPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_BIRCH_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_birch_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.BIRCH_PLANKS);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftVerticalCrimsonPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_CRIMSON_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_crimson_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.CRIMSON_PLANKS);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftVerticalDarkOakPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_DARK_OAK_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_dark_oak_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.DARK_OAK_PLANKS);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftVerticalJunglePlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_JUNGLE_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_jungle_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.JUNGLE_PLANKS);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftVerticalOakPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_OAK_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_oak_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.OAK_PLANKS);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftVerticalSprucePlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_SPRUCE_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_spruce_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.SPRUCE_PLANKS);
        return shapedRecipe;
    }

    @Nonnull
    public static ShapedRecipe craftVerticalWarpedPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_WARPED_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_warped_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.WARPED_PLANKS);
        return shapedRecipe;
    }
}
