package github.minersStudios.msBlock.crafts.planks;

import github.minersStudios.msBlock.enumerators.CustomBlockMaterial;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import static github.minersStudios.msBlock.Main.plugin;

public class VerticalPlanks {

    public VerticalPlanks(){
        craftVerticalAcaciaPlanks();
        craftVerticalBirchPlanks();
        craftVerticalCrimsonPlanks();
        craftVerticalDarkOakPlanks();
        craftVerticalJunglePlanks();
        craftVerticalOakPlanks();
        craftVerticalSprucePlanks();
        craftVerticalWarpedPlanks();
    }

    private static void craftVerticalAcaciaPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_ACACIA_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_acacia_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.ACACIA_PLANKS);
        plugin.getServer().addRecipe(shapedRecipe);
    }

    private static void craftVerticalBirchPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_BIRCH_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_birch_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.BIRCH_PLANKS);
        plugin.getServer().addRecipe(shapedRecipe);
    }

    private static void craftVerticalCrimsonPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_CRIMSON_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_crimson_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.CRIMSON_PLANKS);
        plugin.getServer().addRecipe(shapedRecipe);
    }

    private static void craftVerticalDarkOakPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_DARK_OAK_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_dark_oak_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.DARK_OAK_PLANKS);
        plugin.getServer().addRecipe(shapedRecipe);
    }

    private static void craftVerticalJunglePlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_JUNGLE_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_jungle_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.JUNGLE_PLANKS);
        plugin.getServer().addRecipe(shapedRecipe);
    }

    private static void craftVerticalOakPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_OAK_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_oak_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.OAK_PLANKS);
        plugin.getServer().addRecipe(shapedRecipe);
    }

    private static void craftVerticalSprucePlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_SPRUCE_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_spruce_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.SPRUCE_PLANKS);
        plugin.getServer().addRecipe(shapedRecipe);
    }

    private static void craftVerticalWarpedPlanks(){
        ItemStack itemStack = CustomBlockMaterial.VERTICAL_WARPED_PLANKS.getItemStack();
        itemStack.setAmount(3);
        ShapedRecipe shapedRecipe = new ShapedRecipe(NamespacedKey.minecraft("vertical_warped_planks"), itemStack);
        shapedRecipe.shape(
                " P ",
                " P ",
                " P "
        );
        shapedRecipe.setIngredient('P', Material.WARPED_PLANKS);
        plugin.getServer().addRecipe(shapedRecipe);
    }
}
