package com.github.minersstudios.msblock.utils;

import com.github.minersstudios.msblock.MSBlock;
import com.github.minersstudios.msblock.customblock.CustomBlockData;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R2.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftInventory;
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

	public static void openShulkerBoxWithoutAnimation(@NotNull Player player, @NotNull ShulkerBox shulkerBox) {
		ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
		Container inventory = ((CraftInventory) shulkerBox.getInventory()).getInventory();

		if (inventory != null && !serverPlayer.isSpectator()) {
			int containerCounter = serverPlayer.nextContainerCounter();
			AbstractContainerMenu container = CraftEventFactory.callInventoryOpenEvent(serverPlayer, new ShulkerBoxMenu(containerCounter, serverPlayer.getInventory(), inventory), false);
			container.setTitle(((MenuProvider) inventory).getDisplayName());

			shulkerBox.getWorld().playSound(shulkerBox.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 0.5f, serverPlayer.level.random.nextFloat() * 0.1F + 0.9F);

			serverPlayer.containerMenu = container;
			if (!serverPlayer.isImmobile()) {
				serverPlayer.connection.send(new ClientboundOpenScreenPacket(container.containerId, container.getType(), container.getTitle()));
			}

			serverPlayer.initMenu(container);
		}
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

	public static @NotNull UseOnContext getUseOnContext(@NotNull Player player, @NotNull Location blockLoc, @NotNull InteractionHand interactionHand) {
		ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
		BlockPos blockPos = new BlockPos(blockLoc.getBlockX(), blockLoc.getBlockY(), blockLoc.getBlockZ());
		BlockHitResult blockHitResult = new BlockHitResult(serverPlayer.getEyePosition(), serverPlayer.getDirection(), blockPos, false);
		return new UseOnContext(serverPlayer, interactionHand, blockHitResult);
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
		for (CustomBlockData customBlockData : MSBlock.getConfigCache().customBlocks.values()) {
			ItemStack customBlockItemStack = customBlockData.craftItemStack();
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
