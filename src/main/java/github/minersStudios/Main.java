package github.minersStudios;

import github.minersStudios.listeners.RegEvents;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static CoreProtectAPI coreProtectAPI = new CoreProtectAPI();

    private CoreProtectAPI getCoreProtect() {
        final Plugin coreProtect = getServer().getPluginManager().getPlugin("CoreProtect");

        if (coreProtect == null) return null;
        CoreProtectAPI CoreProtect = ((CoreProtect)coreProtect).getAPI();
        return (!CoreProtect.isEnabled() || CoreProtect.APIVersion() < 7 ? null : CoreProtect);
    }

    @Override
    public void onEnable() {
        plugin = this;

        coreProtectAPI = getCoreProtect();
        if (coreProtectAPI != null)
            coreProtectAPI.testAPI();

        saveConfig();

        new RegEvents();

        getServer().getConsoleSender().sendMessage("§f-------=============-------");
        getServer().getConsoleSender().sendMessage("    §6msBlock §f| §aВключён!");
        getServer().getConsoleSender().sendMessage("    §6By - MinersStudios");
        getServer().getConsoleSender().sendMessage("§f-------=============-------");
    }
}
