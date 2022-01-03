package github.minersStudios;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import github.minersStudios.listeners.RegEvents;
import github.minersStudios.listeners.block.PacketBreakListener;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static CoreProtectAPI coreProtectAPI = new CoreProtectAPI();
    public static ProtocolManager protocolManager;

    private CoreProtectAPI getCoreProtect() {
        final Plugin coreProtect = getServer().getPluginManager().getPlugin("CoreProtect");

        if (coreProtect == null) return null;
        CoreProtectAPI CoreProtect = ((CoreProtect)coreProtect).getAPI();
        return (!CoreProtect.isEnabled() || CoreProtect.APIVersion() < 7 ? null : CoreProtect);
    }

    @Override
    public void onEnable() {
        protocolManager = ProtocolLibrary.getProtocolManager();
        plugin = this;

        coreProtectAPI = getCoreProtect();
        if (coreProtectAPI != null) coreProtectAPI.testAPI();

        new RegEvents();
    }
}
