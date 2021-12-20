package github.minersStudios.Classes;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import github.minersStudios.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class PlaySwingAnimation {
    public PlaySwingAnimation(Player player){
        PacketContainer minePacket = Main.manager.createPacket(PacketType.Play.Server.ANIMATION);
        minePacket.getEntityModifier(player.getWorld()).write(0, player);

        Bukkit.getServer().getOnlinePlayers().forEach((p) -> {
            try {
                Main.manager.sendServerPacket(player, minePacket);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot send packet.", e);
            }
        });
    }
}
