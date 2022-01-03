package github.minersStudios.utils;

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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PlayerUtils {

    public static final Map<Player, Integer> diggers = new HashMap<>();

    public static EnumHand parseEnumHand(@Nullable EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.HAND ? EnumHand.a : (equipmentSlot == EquipmentSlot.OFF_HAND ? EnumHand.b : null);
    }

    public static EquipmentSlot getEquipmentSlot(@Nonnull PlayerInventory playerInventory, @Nullable ItemStack item) {
        return playerInventory.getItemInMainHand().equals(item) ? EquipmentSlot.HAND : playerInventory.getItemInOffHand().equals(item) ? EquipmentSlot.OFF_HAND : null;
    }

    public static Location getInteractionPoint(@Nonnull Location location, int maxDistance, boolean ignorePassableBlocks) {
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

    public static EntityPlayer parseHuman(@Nonnull Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static MovingObjectPositionBlock getMovingObjectPositionBlock(@Nonnull Player player, @Nonnull Location blockLoc, boolean var3) {
        Vec3D vec3D = new Vec3D(player.getEyeLocation().getX(), player.getEyeLocation().getY(), player.getEyeLocation().getZ());
        BlockPosition blockPosition = new BlockPosition(blockLoc.getBlockX(), blockLoc.getBlockY(), blockLoc.getBlockZ());

        return new MovingObjectPositionBlock(vec3D, parseHuman(player).ct(), blockPosition, var3);
    }

}
