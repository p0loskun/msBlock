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
        return inv.getItemInMainHand().equals(item) ? EquipmentSlot.HAND : inv.getItemInOffHand().equals(item) ? EquipmentSlot.OFF_HAND : null;
    }

    public static Location getInteractionPoint(@NotNull Location location, int maxDistance, boolean ignorePassableBlocks) {
        if (location.getWorld() == null) return null;

        RayTraceResult rayTraceResult = location.getWorld().rayTraceBlocks(
                location,
                location.getDirection(),
                maxDistance,
                FluidCollisionMode.NEVER,
                ignorePassableBlocks
        );
        if (rayTraceResult == null || rayTraceResult.getHitBlock() == null) return null;

        return rayTraceResult.getHitPosition().subtract(rayTraceResult.getHitBlock().getLocation().toVector()).toLocation(location.getWorld());
    }

    public static EntityPlayer parseHuman(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static MovingObjectPositionBlock getMovingObjectPositionBlock(@NotNull Player player, @NotNull Location blockLoc, boolean var3) {
        Vec3D vec3D = new Vec3D(player.getEyeLocation().getX(), player.getEyeLocation().getY(), player.getEyeLocation().getZ());
        BlockPosition blockPosition = new BlockPosition(blockLoc.getBlockX(), blockLoc.getBlockY(), blockLoc.getBlockZ());

        return new MovingObjectPositionBlock(vec3D, parseHuman(player).ct(), blockPosition, var3);
    }

}
