package github.minersStudios.listeners;

import github.minersStudios.Main;
import github.minersStudios.listeners.block.*;
import github.minersStudios.listeners.player.InteractWithBlockListener;
import github.minersStudios.listeners.player.PlaceCustomBlockListener;
import org.bukkit.plugin.PluginManager;

public final class RegEvents {

    public RegEvents(){
        PluginManager pluginManager = Main.plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new BlockBreakListener(), Main.plugin);
        pluginManager.registerEvents(new BlockPhysicsListener(), Main.plugin);
        pluginManager.registerEvents(new BlockPlaceListener(), Main.plugin);
        pluginManager.registerEvents(new ExplosionListener(), Main.plugin);
        pluginManager.registerEvents(new NotePlayListener(), Main.plugin);
        pluginManager.registerEvents(new PistonListener(), Main.plugin);
        pluginManager.registerEvents(new InteractWithBlockListener(), Main.plugin);
        pluginManager.registerEvents(new PlaceCustomBlockListener(), Main.plugin);
    }

}
