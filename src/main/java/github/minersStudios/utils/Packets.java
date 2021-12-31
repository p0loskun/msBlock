package github.minersStudios.utils;

import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import static github.minersStudios.utils.BlockUtils.*;

public class Packets {

    public static void sendBreakPacket(int animation, Block block) {
        ((CraftServer) Bukkit.getServer()).getHandle().a(null, block.getX(), block.getY(), block.getZ(), 120,
                WorldServer.f, new PacketPlayOutBlockBreakAnimation(getBlockEntityId(block), getBlockPosition(block), animation));
    }

    public static void sendBreakBlock(Player player, Block block) {
        ((CraftPlayer) player).getHandle().d.a(getBlockPosition(block));
    }


}
