package com.github.minersstudios.msblock.commands;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.tabcompleters.TabCommandHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class RegCommands {

	private RegCommands() {
		throw new IllegalStateException();
	}

	public static void init(@NotNull Main plugin) {
		Objects.requireNonNull(plugin.getCommand("msblock")).setExecutor(new CommandHandler());
		Objects.requireNonNull(plugin.getCommand("msblock")).setTabCompleter(new TabCommandHandler());
	}
}
