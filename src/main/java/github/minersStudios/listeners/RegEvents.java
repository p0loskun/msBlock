package github.minersStudios.listeners;

import github.minersStudios.listeners.block.*;
import github.minersStudios.listeners.player.*;
import org.bukkit.plugin.PluginManager;

import static github.minersStudios.Main.plugin;
import static github.minersStudios.Main.protocolManager;

public final class RegEvents {

    public RegEvents(){
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new BlockBreakListener(), plugin);
        pluginManager.registerEvents(new BlockDamageListener(), plugin);
        pluginManager.registerEvents(new BlockPhysicsListener(), plugin);
        pluginManager.registerEvents(new BlockPlaceListener(), plugin);
        pluginManager.registerEvents(new ExplosionListener(), plugin);
        pluginManager.registerEvents(new NotePlayListener(), plugin);
        pluginManager.registerEvents(new PistonListener(), plugin);
        pluginManager.registerEvents(new InteractWithBlockListener(), plugin);
        pluginManager.registerEvents(new PlaceCustomBlockListener(), plugin);
        pluginManager.registerEvents(new PlayerKickListener(), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(), plugin);

        protocolManager.addPacketListener(new PacketBreakListener());
    }

}
