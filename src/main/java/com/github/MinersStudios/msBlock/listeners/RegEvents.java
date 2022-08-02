package com.github.MinersStudios.msBlock.listeners;

import com.github.MinersStudios.msBlock.Main;
import com.github.MinersStudios.msBlock.listeners.block.*;
import com.github.MinersStudios.msBlock.listeners.player.*;
import org.bukkit.plugin.PluginManager;

public final class RegEvents {

	public static void init() {
		PluginManager pluginManager = Main.getInstance().getServer().getPluginManager();

		pluginManager.registerEvents(new BlockBreakListener(), Main.getInstance());
		pluginManager.registerEvents(new BlockDamageListener(), Main.getInstance());
		pluginManager.registerEvents(new BlockPhysicsListener(), Main.getInstance());
		pluginManager.registerEvents(new BlockPlaceListener(), Main.getInstance());
		pluginManager.registerEvents(new ExplosionListener(), Main.getInstance());
		pluginManager.registerEvents(new NotePlayListener(), Main.getInstance());
		Main.getProtocolManager().addPacketListener(new PacketBlockDigListener());

		pluginManager.registerEvents(new PlayerInteractListener(), Main.getInstance());
		pluginManager.registerEvents(new PlayerMoveListener(), Main.getInstance());
		pluginManager.registerEvents(new InventoryCreativeListener(), Main.getInstance());
		pluginManager.registerEvents(new PlayerQuitListener(), Main.getInstance());
		pluginManager.registerEvents(new PlayerGameModeChangeListener(), Main.getInstance());
		pluginManager.registerEvents(new PlayerTeleportListener(), Main.getInstance());
		pluginManager.registerEvents(new PlayerDeathListener(), Main.getInstance());
	}
}
