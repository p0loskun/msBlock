package com.github.minersstudios.msblock;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.minersstudios.msblock.listeners.block.PacketBlockDigListener;
import com.github.minersstudios.msblock.utils.ConfigCache;
import com.github.minersstudios.mscore.MSPlugin;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
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
		coreProtectAPI = this.getCoreProtect();
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
		configCache.loadBlocks();
	}

	@Contract(pure = true)
	public static @NotNull MSBlock getInstance() {
		return instance;
	}

	@Contract(pure = true)
	public static @NotNull ConfigCache getConfigCache() {
		return configCache;
	}

	@Contract(pure = true)
	public static @NotNull CoreProtectAPI getCoreProtectAPI() {
		return coreProtectAPI;
	}

	@Contract(pure = true)
	public static @NotNull ProtocolManager getProtocolManager() {
		return protocolManager;
	}
}
