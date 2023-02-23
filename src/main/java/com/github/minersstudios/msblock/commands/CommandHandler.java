package com.github.minersstudios.msblock.commands;

import com.github.minersstudios.msblock.MSBlock;
import com.github.minersstudios.mscore.MSCommand;
import com.github.minersstudios.mscore.MSCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@MSCommand(command = "msblock")
public class CommandHandler implements MSCommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull ... args) {
		if (args.length > 0) {
			String utilsCommand = args[0].toLowerCase(Locale.ROOT);
			if ("reload".equalsIgnoreCase(utilsCommand)) {
				return ReloadCommand.runCommand(sender);
			}
			if ("give".equalsIgnoreCase(utilsCommand)) {
				return GiveCommand.runCommand(sender, args);
			}
		}
		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull ... args) {
		List<String> completions = new ArrayList<>();
		switch (args.length) {
			case 1 -> {
				completions.add("reload");
				completions.add("give");
			}
			case 2 -> {
				for (Player player : Bukkit.getOnlinePlayers()) {
					completions.add(player.getName());
				}
			}
			case 3 -> completions.addAll(MSBlock.getConfigCache().customBlocks.primaryKeySet());
		}
		return completions;
	}
}
