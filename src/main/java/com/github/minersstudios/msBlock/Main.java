package com.github.minersstudios.msBlock;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.minersstudios.msBlock.crafts.RegCrafts;
import com.github.minersstudios.msBlock.listeners.RegEvents;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.Locale;

public final class Main extends JavaPlugin {
	private static Main instance;
	private static String pluginNameInLowerCase;
	private static CoreProtectAPI coreProtectAPI;
	private static ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		instance = this;
		protocolManager = ProtocolLibrary.getProtocolManager();
		coreProtectAPI = getCoreProtect();
		pluginNameInLowerCase = instance.getName().toLowerCase(Locale.ROOT);
		if (coreProtectAPI != null) {
			coreProtectAPI.testAPI();
		}
		RegEvents.init();
		RegCrafts.init();
	}

	@Nullable
	private CoreProtectAPI getCoreProtect() {
		Plugin coreProtect = getServer().getPluginManager().getPlugin("CoreProtect");
		if (coreProtect == null) return null;
		CoreProtectAPI coreProtectAPI = ((CoreProtect)coreProtect).getAPI();
		return !coreProtectAPI.isEnabled() || coreProtectAPI.APIVersion() < 9 ? null : coreProtectAPI;
	}

	public static Main getInstance() {
		return instance;
	}

	public static String getPluginNameInLowerCase() {
		return pluginNameInLowerCase;
	}

	public static CoreProtectAPI getCoreProtectAPI() {
		return coreProtectAPI;
	}

	public static ProtocolManager getProtocolManager() {
		return protocolManager;
	}
}
