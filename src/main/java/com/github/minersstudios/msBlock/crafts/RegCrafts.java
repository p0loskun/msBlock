package com.github.minersstudios.msBlock.crafts;

import com.github.minersstudios.msBlock.crafts.dumbass.HacpalBlock;
import com.github.minersstudios.msBlock.crafts.dumbass.SvinsterBlock;
import com.github.minersstudios.msBlock.crafts.planks.CarvedPlanks;
import com.github.minersstudios.msBlock.crafts.planks.FramedPlanks;
import com.github.minersstudios.msBlock.crafts.planks.VerticalPlanks;

public class RegCrafts {

	public static void init() {
		VerticalPlanks.addRecipes();
		FramedPlanks.addRecipes();
		CarvedPlanks.addRecipes();
		HacpalBlock.addRecipes();
		SvinsterBlock.addRecipes();
	}
}
