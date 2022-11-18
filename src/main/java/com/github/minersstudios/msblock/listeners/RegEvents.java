package com.github.minersstudios.msblock.listeners;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.listeners.block.*;
import com.github.minersstudios.msblock.listeners.inventory.*;
import com.github.minersstudios.msblock.listeners.player.*;
import org.bukkit.plugin.PluginManager;

public final class RegEvents {

	private RegEvents() {
		throw new IllegalStateException("Utility class");
	}

	public static void init() {
		PluginManager pluginManager = Main.getInstance().getServer().getPluginManager();

		pluginManager.registerEvents(new BlockBreakListener(), Main.getInstance());
		pluginManager.registerEvents(new BlockDamageListener(), Main.getInstance());
		pluginManager.registerEvents(new BlockPhysicsListener(), Main.getInstance());
		pluginManager.registerEvents(new BlockPlaceListener(), Main.getInstance());
		pluginManager.registerEvents(new ExplosionListener(), Main.getInstance());
		pluginManager.registerEvents(new NotePlayListener(), Main.getInstance());
		Main.getProtocolManager().addPacketListener(new PacketBlockDigListener());

		pluginManager.registerEvents(new PrepareItemCraftListener(), Main.getInstance());
		pluginManager.registerEvents(new InventoryCreativeListener(), Main.getInstance());
		pluginManager.registerEvents(new InventoryClickListener(), Main.getInstance());

		pluginManager.registerEvents(new PlayerInteractListener(), Main.getInstance());
		pluginManager.registerEvents(new PlayerMoveListener(), Main.getInstance());
		pluginManager.registerEvents(new PlayerQuitListener(), Main.getInstance());
		pluginManager.registerEvents(new PlayerGameModeChangeListener(), Main.getInstance());
		pluginManager.registerEvents(new PlayerTeleportListener(), Main.getInstance());
		pluginManager.registerEvents(new PlayerDeathListener(), Main.getInstance());
	}
}
