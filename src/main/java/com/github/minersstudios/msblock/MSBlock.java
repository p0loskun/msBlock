package com.github.minersstudios.msblock;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.minersstudios.msblock.listeners.block.PacketBlockDigListener;
import com.github.minersstudios.msblock.utils.ConfigCache;
import com.github.minersstudios.mscore.MSPlugin;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

public final class MSBlock extends MSPlugin {
	private static MSBlock instance;
	private static ConfigCache configCache;
	private static CoreProtectAPI coreProtectAPI;
	private static ProtocolManager protocolManager;

	@Override
	public void enable() {
		instance = this;
		protocolManager = ProtocolLibrary.getProtocolManager();
		coreProtectAPI = getCoreProtect();
		if (coreProtectAPI != null) {
			coreProtectAPI.testAPI();
		}

		reloadConfigs();

		protocolManager.addPacketListener(new PacketBlockDigListener());
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

	public static MSBlock getInstance() {
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
