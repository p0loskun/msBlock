package com.github.minersstudios.msblock;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.minersstudios.msblock.commands.RegCommands;
import com.github.minersstudios.msblock.listeners.RegEvents;
import com.github.minersstudios.msblock.utils.ChatUtils;
import com.github.minersstudios.msblock.utils.ConfigCache;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public final class Main extends JavaPlugin {
	private static Main instance;
	private static ConfigCache configCache;
	private static CoreProtectAPI coreProtectAPI;
	private static ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		long time = System.currentTimeMillis();
		this.load();
		if (this.isEnabled()) {
			ChatUtils.log(Level.INFO, ChatColor.GREEN + "Enabled in " + (System.currentTimeMillis() - time) + "ms");
		}
	}

	public void load() {
		instance = this;
		protocolManager = ProtocolLibrary.getProtocolManager();
		coreProtectAPI = getCoreProtect();
		if (coreProtectAPI != null) {
			coreProtectAPI.testAPI();
		}

		reloadConfigs();

		RegEvents.init(this);
		RegCommands.init(this);
	}

	private @Nullable CoreProtectAPI getCoreProtect() {
		Plugin coreProtect = getServer().getPluginManager().getPlugin("CoreProtect");
		if (coreProtect == null) return null;
		CoreProtectAPI api = ((CoreProtect)coreProtect).getAPI();
		return !api.isEnabled() || api.APIVersion() < 9 ? null : api;
	}

	public static void reloadConfigs() {
		instance.saveResource("blocks/example.yml", true);
		instance.saveDefaultConfig();
		instance.reloadConfig();
		configCache = new ConfigCache();
	}

	public static Main getInstance() {
		return instance;
	}

	public static ConfigCache getConfigCache() {
		return configCache;
	}

	public static CoreProtectAPI getCoreProtectAPI() {
		return coreProtectAPI;
	}

	public static ProtocolManager getProtocolManager() {
		return protocolManager;
	}
}
