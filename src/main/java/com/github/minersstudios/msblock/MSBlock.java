package com.github.minersstudios.msblock;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.minersstudios.msblock.customblock.CustomBlockData;
import com.github.minersstudios.msblock.listeners.block.PacketBlockDigListener;
import com.github.minersstudios.msblock.utils.ConfigCache;
import com.github.minersstudios.mscore.MSPlugin;
import com.github.minersstudios.mscore.utils.MSPluginUtils;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class MSBlock extends MSPlugin {
	private static MSBlock instance;
	private static ConfigCache configCache;
	private static CoreProtectAPI coreProtectAPI;
	private static ProtocolManager protocolManager;

	@Override
	public void enable() {
		instance = this;
		protocolManager = ProtocolLibrary.getProtocolManager();
		coreProtectAPI = CoreProtect.getInstance().getAPI();

		reloadConfigs();

		protocolManager.addPacketListener(new PacketBlockDigListener());
	}

	public static void reloadConfigs() {
		instance.saveResource("blocks/example.yml", true);
		instance.saveDefaultConfig();
		instance.reloadConfig();
		configCache = new ConfigCache();

		configCache.loadBlocks();
		instance.loadedCustoms = true;

		new BukkitRunnable() {
			@Override
			public void run() {
				if (MSPluginUtils.isLoadedCustoms()) {
					for (CustomBlockData customBlockData : configCache.recipeBlocks) {
						customBlockData.registerRecipes();
					}
					configCache.recipeBlocks.clear();
					this.cancel();
				}
			}
		}.runTaskTimer(instance, 0L, 10L);
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
