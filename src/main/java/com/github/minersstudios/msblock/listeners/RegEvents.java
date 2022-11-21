package com.github.minersstudios.msblock.listeners;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.listeners.block.*;
import com.github.minersstudios.msblock.listeners.inventory.*;
import com.github.minersstudios.msblock.listeners.player.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nonnull;

public final class RegEvents {

	private RegEvents() {
		throw new IllegalStateException("Utility class");
	}

	public static void init(@Nonnull Main plugin) {
		PluginManager pluginManager = Bukkit.getPluginManager();

		pluginManager.registerEvents(new BlockBreakListener(), plugin);
		pluginManager.registerEvents(new BlockDamageListener(), plugin);
		pluginManager.registerEvents(new BlockPhysicsListener(), plugin);
		pluginManager.registerEvents(new BlockPlaceListener(), plugin);
		pluginManager.registerEvents(new ExplosionListener(), plugin);
		pluginManager.registerEvents(new NotePlayListener(), plugin);
		Main.getProtocolManager().addPacketListener(new PacketBlockDigListener());

		pluginManager.registerEvents(new PrepareItemCraftListener(), plugin);
		pluginManager.registerEvents(new InventoryCreativeListener(), plugin);
		pluginManager.registerEvents(new InventoryClickListener(), plugin);

		pluginManager.registerEvents(new PlayerInteractListener(), plugin);
		pluginManager.registerEvents(new PlayerMoveListener(), plugin);
		pluginManager.registerEvents(new PlayerQuitListener(), plugin);
		pluginManager.registerEvents(new PlayerGameModeChangeListener(), plugin);
		pluginManager.registerEvents(new PlayerTeleportListener(), plugin);
		pluginManager.registerEvents(new PlayerDeathListener(), plugin);
	}
}
