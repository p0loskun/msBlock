package com.github.minersstudios.msblock.commands;

import com.github.minersstudios.msblock.MSBlock;
import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.mscore.utils.ChatUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ReloadCommand {

	public static boolean runCommand(@NotNull CommandSender sender) {
		long time = System.currentTimeMillis();
		HandlerList.unregisterAll(MSBlock.getInstance());
		Iterator<Recipe> crafts = Bukkit.recipeIterator();
		while (crafts.hasNext()) {
			Recipe recipe = crafts.next();
			if (recipe instanceof ShapedRecipe shapedRecipe && shapedRecipe.getKey().getNamespace().equals("msblock")) {
				Bukkit.removeRecipe(shapedRecipe.getKey());
			}
		}
		BlockUtils.CUSTOM_BLOCK_RECIPES.clear();
		MSBlock.getInstance().load();
		if (MSBlock.getInstance().isEnabled()) {
			return ChatUtils.sendFine(sender, Component.text("Плагин был успешно перезагружён за " + (System.currentTimeMillis() - time) + "ms"));
		}
		return ChatUtils.sendError(sender, Component.text("Плагин был перезагружён неудачно"));
	}
}
