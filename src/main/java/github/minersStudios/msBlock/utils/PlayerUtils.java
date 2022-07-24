package github.minersStudios.msBlock.utils;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.RayTraceResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class PlayerUtils {
    public static final Map<Player, Double> steps = new HashMap<>();
    public static final Set<Player> farAway = new HashSet<>();

    /**
     * Gets interaction location
     *
     * @param location    the start location
     * @param maxDistance the maximum distance
     * @return interaction Location or null if rayTraceResult == null || hit block == null
     */
    @Nullable
    public static Location getInteractionPoint(@Nonnull Location location, int maxDistance) {
        if (location.getWorld() == null) return null;
        RayTraceResult rayTraceResult = location.getWorld().rayTraceBlocks(location, location.getDirection(), maxDistance, FluidCollisionMode.NEVER, true);
        return rayTraceResult == null || rayTraceResult.getHitBlock() == null ? null : rayTraceResult.getHitPosition().subtract(rayTraceResult.getHitBlock().getLocation().toVector()).toLocation(location.getWorld());
    }

    /**
     * Converts bukkit player to NMS player
     *
     * @param player bukkit player who converts to NMS player
     * @return NMS player
     */
    @Nonnull
    public static EntityPlayer convertPlayer(@Nonnull Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    /**
     * Gets moving object position block by player and block at face location
     *
     * @param player   used to get Vec3D & MovingObjectPositionBlock(EnumDirection var3)
     * @param blockLoc used to get BlockPosition
     * @return moving object position block
     */
    @Nonnull
    public static MovingObjectPositionBlock getMovingObjectPositionBlock(@Nonnull Player player, @Nonnull Location blockLoc) {
        Location playerEyeLoc = player.getEyeLocation();
        return new MovingObjectPositionBlock(new Vec3D(playerEyeLoc.getX(), playerEyeLoc.getY(), playerEyeLoc.getZ()), convertPlayer(player).cw(), new BlockPosition(blockLoc.getBlockX(), blockLoc.getBlockY(), blockLoc.getBlockZ()), false);
    }

    /**
     * Swings hand/offhand
     *
     * @param player player
     * @param equipmentSlot hand
     */
    public static void swingHand(@Nonnull Player player, @Nonnull EquipmentSlot equipmentSlot){
        if (equipmentSlot == EquipmentSlot.HAND) {
            player.swingMainHand();
        } else {
            player.swingOffHand();
        }
    }

    /**
     * @param player player
     * @return target block
     */
    @Nullable
    public static Block getTargetBlock(@Nonnull Player player) {
        Location eyeLocation = player.getEyeLocation();
        RayTraceResult rayTraceResult = player.getWorld().rayTraceBlocks(eyeLocation, eyeLocation.getDirection(), 4.5d, FluidCollisionMode.NEVER, false);
        return rayTraceResult != null ? rayTraceResult.getHitBlock() : null;
    }

    /**
     * @param player player
     * @return target entity
     */
    @Nullable
    public static Entity getTargetEntity(@Nonnull Player player) {
        Location eyeLocation = player.getEyeLocation();
        Predicate<Entity> filter = entity -> !entity.equals(player);
        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(eyeLocation, eyeLocation.getDirection(), 4.5d, filter);
        return rayTraceResult != null ? rayTraceResult.getHitEntity() : null;
    }
}
