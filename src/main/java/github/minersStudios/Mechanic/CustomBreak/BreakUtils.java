package github.minersStudios.Mechanic.CustomBreak;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BreakUtils {
    public static void addSlowDig(Player player, int amplifier){
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, amplifier, false, false, false));
    }

    public static void removeSlowDig(Player player) {
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
    }

    public static void addFastDig(Player player, int amplifier){
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, amplifier, false, false, false));
    }

    public static void removeFastDig(Player player) {
        player.removePotionEffect(PotionEffectType.FAST_DIGGING);
    }

    public static void sendBreakPacket(int animation, Block block) {
        ((CraftServer) Bukkit.getServer()).getHandle().a(null, block.getX(), block.getY(), block.getZ(), 120,
                WorldServer.f, new PacketPlayOutBlockBreakAnimation(getBlockEntityId(block), getBlockPosition(block), animation));
    }

    public static void sendBreakBlock(Player player, Block block) {
        ((CraftPlayer) player).getHandle().d.a(getBlockPosition(block));
    }

    public static BlockPosition getBlockPosition(Block block){
        return new BlockPosition(block.getX(), block.getY(), block.getZ());
    }

    private static int getBlockEntityId(Block block){
        return ((block.getX() & 0xFFF) << 20 | (block.getZ() & 0xFFF) << 8) | (block.getY() & 0xFF);
    }
}
