package com.github.minersstudios.msblock.utils;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.customblock.CustomBlock;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class PlayerUtils {
	public static final Map<Player, Double> steps = new HashMap<>();
	public static final Set<Player> farAway = new HashSet<>();

	private static final ImmutableSet<EntityType> MOB_FILTER = Sets.immutableEnumSet(
			//<editor-fold desc="Ignorable mob types">
			EntityType.DROPPED_ITEM,
			EntityType.ARROW,
			EntityType.SPECTRAL_ARROW,
			EntityType.AREA_EFFECT_CLOUD,
			EntityType.DRAGON_FIREBALL,
			EntityType.EGG,
			EntityType.FISHING_HOOK,
			EntityType.WITHER_SKULL,
			EntityType.TRIDENT,
			EntityType.SNOWBALL,
			EntityType.SMALL_FIREBALL,
			EntityType.FIREBALL,
			EntityType.FIREWORK,
			EntityType.SPLASH_POTION,
			EntityType.THROWN_EXP_BOTTLE,
			EntityType.EXPERIENCE_ORB,
			EntityType.LLAMA_SPIT,
			EntityType.LIGHTNING
			//</editor-fold>
	);

	private PlayerUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Gets interaction location
	 *
	 * @param location    the start location
	 * @param maxDistance the maximum distance
	 * @return interaction Location or null if rayTraceResult == null || hit block == null
	 */
	public static @Nullable Location getInteractionPoint(@NotNull Location location, int maxDistance) {
		if (location.getWorld() == null) return null;
		RayTraceResult rayTraceResult = location.getWorld().rayTraceBlocks(location, location.getDirection(), maxDistance, FluidCollisionMode.NEVER, true);
		return rayTraceResult == null
				|| rayTraceResult.getHitBlock() == null ? null
				: rayTraceResult.getHitPosition().subtract(rayTraceResult.getHitBlock().getLocation().toVector()).toLocation(location.getWorld());
	}

	public static @NotNull ItemActionContext getItemActionContext(@NotNull Player player, @NotNull Location blockLoc, @NotNull EnumHand enumHand) {
		Location playerEyeLoc = player.getEyeLocation();
		Vec3D vec3D = new Vec3D(playerEyeLoc.getX(), playerEyeLoc.getY(), playerEyeLoc.getZ());
		BlockPosition blockPosition = new BlockPosition(blockLoc.getBlockX(), blockLoc.getBlockY(), blockLoc.getBlockZ());
		MovingObjectPositionBlock movingObjectPositionBlock = new MovingObjectPositionBlock(vec3D, ((CraftPlayer) player).getHandle().cA(), blockPosition, false);
		return new ItemActionContext(((CraftPlayer) player).getHandle(), enumHand, movingObjectPositionBlock);
	}

	/**
	 * @param player player
	 * @return target block
	 */
	public static @Nullable Block getTargetBlock(@NotNull Player player) {
		Location eyeLocation = player.getEyeLocation();
		RayTraceResult rayTraceResult = player.getWorld().rayTraceBlocks(eyeLocation, eyeLocation.getDirection(), 4.5d, FluidCollisionMode.NEVER, false);
		return rayTraceResult != null ? rayTraceResult.getHitBlock() : null;
	}

	/**
	 * @param player      player
	 * @param targetBlock target block
	 * @return target entity
	 */
	public static @Nullable Entity getTargetEntity(@NotNull Player player, @Nullable Block targetBlock) {
		Location eyeLocation = player.getEyeLocation();
		Predicate<Entity> filter = entity -> entity != player && !MOB_FILTER.contains(entity.getType());
		RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(eyeLocation, eyeLocation.getDirection(), 4.5d, filter);
		if (rayTraceResult == null) return null;
		Entity targetEntity = rayTraceResult.getHitEntity();
		if (
				targetBlock != null
				&& targetEntity != null
				&& eyeLocation.distance(targetBlock.getLocation()) <= eyeLocation.distance(targetEntity.getLocation())
		) return null;
		return targetEntity;
	}

	/**
	 * @param itemStack item
	 * @return True if item is custom block
	 */
	public static boolean isItemCustomBlock(@NotNull ItemStack itemStack) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		if (itemMeta == null || !itemMeta.hasCustomModelData()) return false;
		for (CustomBlock customBlock : Main.getConfigCache().customBlocks.values()) {
			ItemStack customBlockItemStack = customBlock.craftItemStack();
			ItemMeta customBlockItemMeta = customBlockItemStack.getItemMeta();
			if (
					customBlockItemStack.getType() == itemStack.getType()
					&& customBlockItemMeta.getCustomModelData() == itemMeta.getCustomModelData()
			) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param itemStack item
	 * @return True if item is custom block
	 */
	public static boolean isItemCustomDecor(@NotNull ItemStack itemStack) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		return itemStack.getType() == Material.LEATHER_HORSE_ARMOR && itemMeta != null && itemMeta.hasCustomModelData();
	}
}
