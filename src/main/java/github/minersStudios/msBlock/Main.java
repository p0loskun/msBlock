package github.minersStudios.msBlock;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import github.minersStudios.msBlock.crafts.RegCrafts;
import github.minersStudios.msBlock.listeners.RegEvents;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public final class Main extends JavaPlugin {

    public static Main plugin;
    public static CoreProtectAPI coreProtectAPI = new CoreProtectAPI();
    public static ProtocolManager protocolManager;

    @Nullable
    private CoreProtectAPI getCoreProtect() {
        final Plugin coreProtect = getServer().getPluginManager().getPlugin("CoreProtect");

        if (coreProtect == null) return null;
        CoreProtectAPI CoreProtect = ((CoreProtect)coreProtect).getAPI();
        return (!CoreProtect.isEnabled() || CoreProtect.APIVersion() < 9 ? null : CoreProtect);
    }

    @Override
    public void onEnable() {
        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        coreProtectAPI = getCoreProtect();
        if (coreProtectAPI != null) coreProtectAPI.testAPI();
        new RegEvents();
        new RegCrafts();
    }
}
