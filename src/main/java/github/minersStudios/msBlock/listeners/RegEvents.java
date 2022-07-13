package github.minersStudios.msBlock.listeners;

import github.minersStudios.msBlock.listeners.block.*;
import github.minersStudios.msBlock.listeners.player.*;
import org.bukkit.plugin.PluginManager;

import static github.minersStudios.msBlock.Main.plugin;
import static github.minersStudios.msBlock.Main.protocolManager;

public final class RegEvents {
    public RegEvents(){
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new BlockBreakListener(), plugin);
        pluginManager.registerEvents(new BlockDamageListener(), plugin);
        pluginManager.registerEvents(new BlockPhysicsListener(), plugin);
        pluginManager.registerEvents(new BlockPlaceListener(), plugin);
        pluginManager.registerEvents(new ExplosionListener(), plugin);
        pluginManager.registerEvents(new NotePlayListener(), plugin);

        pluginManager.registerEvents(new PlayerInteractListener(), plugin);
        pluginManager.registerEvents(new PlaceCustomBlockListener(), plugin);
        pluginManager.registerEvents(new PlayerMoveListener(), plugin);
        pluginManager.registerEvents(new InventoryCreativeListener(), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(), plugin);
        protocolManager.addPacketListener(new PacketPlayerBlockDigListener());
    }
}
