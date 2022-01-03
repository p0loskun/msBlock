package github.minersStudios;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import github.minersStudios.listeners.RegEvents;
import github.minersStudios.utils.BlockUtils;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

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

        new BukkitRunnable(){
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(!player.getInventory().getItemInMainHand().getType().toString().contains("_AXE") || player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) return;
                    Block targetBlock = player.getTargetBlock(BlockUtils.TRANSPERENT, 5);
                    if(targetBlock.getType() != Material.NOTE_BLOCK) return;
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, true, false, false));
                }
            }
        }.runTaskTimer(plugin, 50L, 0L);
    }
}
