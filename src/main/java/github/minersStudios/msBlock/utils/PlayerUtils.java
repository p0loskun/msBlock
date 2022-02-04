package github.minersStudios.msBlock.utils;

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

public class PlayerUtils {

    /**
     * Converts bukkit EquipmentSlot to NMS EnumHand
     *
     * @param equipmentSlot equipment slot which will be converted to NMS EnumHand
     *
     * @return NMS EnumHand (a == MainHand, b == OffHand)
     */
    @Nonnull
    public static EnumHand convertEnumHand(@Nullable EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.HAND ? EnumHand.a : EnumHand.b;
    }

    /**
     * Gets equipment slot by checking in which hand is custom block
     *
     * @param playerInventory player inventory
     * @param item custom block item
     *
     * @return EquipmentSlot
     */
    @Nonnull
    public static EquipmentSlot getEquipmentSlot(@Nonnull PlayerInventory playerInventory, @Nonnull ItemStack item) {
        return playerInventory.getItemInMainHand().equals(item) ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
    }

    /**
     * Gets interaction location
     *
     * @param location the start location
     * @param maxDistance the maximum distance
     *
     * @return interaction Location or null if rayTraceResult == null || hit block == null
     */
    @Nullable
    public static Location getInteractionPoint(@Nonnull Location location, int maxDistance) {
        if (location.getWorld() == null) return null;
        RayTraceResult rayTraceResult = location.getWorld().rayTraceBlocks(
                location,
                location.getDirection(),
                maxDistance,
                FluidCollisionMode.NEVER,
                true
        );
        if (rayTraceResult == null || rayTraceResult.getHitBlock() == null) return null;

        return rayTraceResult.getHitPosition().subtract(rayTraceResult.getHitBlock().getLocation().toVector()).toLocation(location.getWorld());
    }

    /**
     * Converts bukkit player to NMS player
     *
     * @param player bukkit player who converts to NMS player
     *
     * @return NMS player
     */
    @Nonnull
    public static EntityPlayer convertPlayer(@Nonnull Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    /**
     * Gets moving object position block by player and block at face location
     *
     * @param player used to get Vec3D & MovingObjectPositionBlock(EnumDirection var3)
     * @param blockLoc used to get BlockPosition
     *
     * @return moving object position block
     */
    @Nonnull
    public static MovingObjectPositionBlock getMovingObjectPositionBlock(@Nonnull Player player, @Nonnull Location blockLoc) {
        Vec3D vec3D = new Vec3D(player.getEyeLocation().getX(), player.getEyeLocation().getY(), player.getEyeLocation().getZ());
        BlockPosition blockPosition = new BlockPosition(blockLoc.getBlockX(), blockLoc.getBlockY(), blockLoc.getBlockZ());

        return new MovingObjectPositionBlock(vec3D, convertPlayer(player).ct(), blockPosition, false);
    }

}
