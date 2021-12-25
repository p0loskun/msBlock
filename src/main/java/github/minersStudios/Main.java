package github.minersStudios;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import github.minersStudios.Listeners.RegEvents;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static ProtocolManager manager;

    private CoreProtectAPI getCoreProtect() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        if (!(plugin instanceof CoreProtect)) {
            return null;
        }

        CoreProtectAPI CoreProtect = ((CoreProtect)plugin).getAPI();
        if (!CoreProtect.isEnabled()){
            return null;
        }

        if (CoreProtect.APIVersion() < 4){
            return null;
        }

        return CoreProtect;
    }

    @Override
    public void onEnable() {
        manager = ProtocolLibrary.getProtocolManager();

        CoreProtectAPI api = getCoreProtect();
        if (api!=null){
            api.testAPI();
        }

        this.saveConfig();

        // Register Events
        new RegEvents();

        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§6msBlock §f| §aВключён!");
        Bukkit.getConsoleSender().sendMessage("§6By - MinersStudios");
        Bukkit.getConsoleSender().sendMessage("");
    }

    @Override
    public void onDisable() {

    }

    public static Main getInstance() { return JavaPlugin.getPlugin(Main.class); }
}
