package github.minersStudios.Listeners;

import github.minersStudios.Main;
import github.minersStudios.Mechanic.CustomBreak.BreakEvents;
import github.minersStudios.Mechanic.Events;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RegEvents {
    public RegEvents(){
        Main plugin = JavaPlugin.getPlugin(Main.class);
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new MainListener(), plugin);
        pluginManager.registerEvents(new Events(), plugin);
        pluginManager.registerEvents(new BreakEvents(), plugin);
    }
}
