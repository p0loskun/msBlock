package com.github.MinersStudios.msBlock.crafts;

import com.github.MinersStudios.msBlock.crafts.dumbass.HacpalBlock;
import com.github.MinersStudios.msBlock.crafts.dumbass.SvinsterBlock;
import com.github.MinersStudios.msBlock.crafts.planks.CarvedPlanks;
import com.github.MinersStudios.msBlock.crafts.planks.FramedPlanks;
import com.github.MinersStudios.msBlock.crafts.planks.VerticalPlanks;

public class RegCrafts {

	public static void init() {
		VerticalPlanks.addRecipes();
		FramedPlanks.addRecipes();
		CarvedPlanks.addRecipes();
		HacpalBlock.addRecipes();
		SvinsterBlock.addRecipes();
	}
}
