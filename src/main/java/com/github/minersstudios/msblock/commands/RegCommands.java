package com.github.minersstudios.msblock.commands;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.tabCompleters.TabCommandHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class RegCommands {

	public static void init(@Nonnull Main plugin) {
		Objects.requireNonNull(plugin.getCommand("msblock")).setExecutor(new CommandHandler());
		Objects.requireNonNull(plugin.getCommand("msblock")).setTabCompleter(new TabCommandHandler());
	}
}
