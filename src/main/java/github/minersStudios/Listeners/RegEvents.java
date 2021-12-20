package github.minersStudios.Listeners;

import github.minersStudios.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RegEvents {
    public RegEvents(){
        Main plugin = JavaPlugin.getPlugin(Main.class);

        Bukkit.getPluginManager().registerEvents(new MainListener(), plugin);
    }
}
