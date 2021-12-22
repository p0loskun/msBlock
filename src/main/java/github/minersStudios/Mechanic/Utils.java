package github.minersStudios.Mechanic;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;

public class Utils {
    public static EnumHand parseEnumHand(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.HAND ? EnumHand.a : (equipmentSlot == EquipmentSlot.OFF_HAND ? EnumHand.b : null);
    }

    public static EquipmentSlot getEquipmentSlot(PlayerInventory inv, ItemStack item) {
        return inv.getItemInMainHand().equals(item) ? EquipmentSlot.HAND
                : inv.getItemInOffHand().equals(item) ? EquipmentSlot.OFF_HAND : null;
    }

    public static Location getInteractionPoint(@NotNull Location start, int maxDistance, boolean ignorePassableBlocks) {
        if (start.getWorld() == null) return null;
        RayTraceResult rtr = start.getWorld().rayTraceBlocks(start,
                start.getDirection(), maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
        if (rtr == null || rtr.getHitBlock() == null) return null;
        return rtr.getHitPosition().subtract(rtr.getHitBlock().getLocation().toVector())
                .toLocation(start.getWorld());
    }

    public static EntityPlayer parseHuman(Player p) {
        return ((CraftPlayer) p).getHandle();
    }

    public static Vec3D LocToVec(Location loc) {
        return new Vec3D(loc.getX(), loc.getY(), loc.getZ());
    }

    public static BlockPosition BlockToBlockPos(Location location) {
        return new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }


    public static MovingObjectPositionBlock getMovingObjectPositionBlock(@NotNull Player player, @NotNull Location blockLoc, boolean var3) {
        return new MovingObjectPositionBlock(LocToVec(player.getEyeLocation()), parseHuman(player).ct(), BlockToBlockPos(blockLoc), var3);
    }

}
