package github.minersStudios.msBlock.crafts;

import github.minersStudios.msBlock.crafts.planks.CarvedPlanks;
import github.minersStudios.msBlock.crafts.planks.FramedPlanks;
import github.minersStudios.msBlock.crafts.planks.VerticalPlanks;

public class RegCrafts {

    public static void init() {
        VerticalPlanks.addRecipes();
        FramedPlanks.addRecipes();
        CarvedPlanks.addRecipes();
    }
}
