package com.github.minersstudios.msblock.utils;

import com.github.minersstudios.msblock.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.function.Predicate;

public final class UseBucketsAndSpawnableItems {
	private final Player player;
	private final World world;
	private final Block block;
	private final ItemStack itemInHand;
	private final Location blockLocation;
	private final BlockFace blockFace;
	private final SecureRandom random = new SecureRandom();

	/**
	 * Uses a bucket vanillish
	 *
	 * @param player    who uses the bucket
	 * @param block     block at face of clicked block
	 * @param blockFace block face
	 * @param hand      hand
	 */
	public UseBucketsAndSpawnableItems(@NotNull Player player, @NotNull Block block, @NotNull BlockFace blockFace, @NotNull EquipmentSlot hand) {
		this.player = player;
		this.world = player.getWorld();
		this.block = block;
		this.itemInHand = player.getInventory().getItem(hand);
		this.blockLocation = block.getLocation().toCenterLocation();
		this.blockFace = blockFace;
		Material itemMaterial = this.itemInHand.getType();
		switch (itemMaterial) {
			case ITEM_FRAME, GLOW_ITEM_FRAME -> setItemFrame();
			case PAINTING -> setPainting();
			case TADPOLE_BUCKET -> summonPrimitiveEntities(EntityType.TADPOLE);
			case PUFFERFISH_BUCKET -> summonPrimitiveEntities(EntityType.PUFFERFISH);
			case SALMON_BUCKET -> summonPrimitiveEntities(EntityType.SALMON);
			case COD_BUCKET -> summonPrimitiveEntities(EntityType.COD);
			case TROPICAL_FISH_BUCKET -> setTropicalFish();
			case AXOLOTL_BUCKET -> setAxolotl();
			case BUCKET -> useEmptyBucket();
			case LAVA_BUCKET -> setLava();
			case WATER_BUCKET -> setWater();
			default -> {
				 if (Tag.ITEMS_BOATS.isTagged(itemMaterial)) {
					 setBoat();
				 }
			}
		}
	}

	/**
	 * @return random axolotl color variant
	 */
	private @NotNull Axolotl.Variant randomVariant() {
		return Axolotl.Variant.values()[random.nextInt(Axolotl.Variant.values().length)];
	}

	/**
	 * @return random tropical fish body pattern variant
	 */
	private @NotNull TropicalFish.Pattern randomPattern() {
		return TropicalFish.Pattern.values()[random.nextInt(TropicalFish.Pattern.values().length)];
	}

	/**
	 * @return random tropical fish body color variant
	 */
	private @NotNull DyeColor randomBodyColor() {
		return DyeColor.values()[random.nextInt(DyeColor.values().length)];
	}

	/**
	 * Sets empty bucket in hand if player GameMode == survival
	 */
	private void setBucketIfSurvival() {
		if (this.player.getGameMode() == GameMode.SURVIVAL) {
			this.player.getInventory().getItemInMainHand().setType(Material.BUCKET);
		}
	}

	/**
	 * Uses boat
	 */
	private void setBoat() {
		Location eyeLocation = this.player.getEyeLocation();
		Predicate<Entity> filter = entity -> entity != this.player && entity.getType() != EntityType.DROPPED_ITEM;
		RayTraceResult rayTraceEntities = this.world.rayTraceEntities(eyeLocation, eyeLocation.getDirection(), 4.5d, 0.1d, filter);
		RayTraceResult rayTraceBlocks = this.world.rayTraceBlocks(eyeLocation, eyeLocation.getDirection(), 4.5d);
		assert rayTraceBlocks != null;
		Location summonLocation = rayTraceBlocks.getHitPosition().toLocation(this.world);
		for (Entity nearbyEntity : this.world.getNearbyEntities(summonLocation, 0.5d, 0.5d, 0.5d)) {
			if (nearbyEntity.getType() != EntityType.DROPPED_ITEM) return;
		}
		if (!BlockUtils.REPLACE.contains(summonLocation.getBlock().getType())) return;
		if (rayTraceEntities != null && rayTraceEntities.getHitEntity() != null) return;
		Boat.Type boatType = Boat.Type.valueOf(itemInHand.getType().name().split("_")[0]);
		summonLocation.setYaw(eyeLocation.getYaw());
		if (Tag.ITEMS_CHEST_BOATS.isTagged(itemInHand.getType())) {
			this.world.spawn(summonLocation, ChestBoat.class, chestBoat -> chestBoat.setBoatType(boatType));
		} else {
			this.world.spawn(summonLocation, Boat.class, chestBoat -> chestBoat.setBoatType(boatType));
		}
		if (this.player.getGameMode() != GameMode.CREATIVE) {
			this.itemInHand.setAmount(this.itemInHand.getAmount() - 1);
		}
	}

	/**
	 * Uses item frame
	 */
	private void setItemFrame() {
		Location eyeLocation = this.player.getEyeLocation();
		Predicate<Entity> filter = entity -> entity != this.player && entity.getType() != EntityType.DROPPED_ITEM;
		RayTraceResult rayTraceResult = this.world.rayTraceEntities(eyeLocation, eyeLocation.getDirection(), 4.5d, 0.1d, filter);
		if (
				rayTraceResult != null
				&& rayTraceResult.getHitEntity() != null
				&& (rayTraceResult.getHitEntity().getType() == EntityType.ITEM_FRAME
				|| rayTraceResult.getHitEntity().getType() == EntityType.PAINTING)
		) return;
		this.world.spawn(this.blockLocation, ItemFrame.class, itemFrame -> itemFrame.setFacingDirection(this.blockFace, true));
		if (this.player.getGameMode() != GameMode.CREATIVE) {
			this.itemInHand.setAmount(this.itemInHand.getAmount() - 1);
		}
	}

	/**
	 * Uses painting
	 */
	private void setPainting() {
		Location eyeLocation = this.player.getEyeLocation();
		Predicate<Entity> filter = entity -> entity != this.player && entity.getType() != EntityType.DROPPED_ITEM;
		RayTraceResult rayTraceResult = this.world.rayTraceEntities(eyeLocation, eyeLocation.getDirection(), 4.5d, 0.1d, filter);
		if (
				rayTraceResult != null
				&& rayTraceResult.getHitEntity() != null
				&& (rayTraceResult.getHitEntity().getType() == EntityType.ITEM_FRAME
				|| rayTraceResult.getHitEntity().getType() == EntityType.PAINTING)
		) return;
		this.world.spawn(this.blockLocation, Painting.class, painting -> painting.setFacingDirection(this.blockFace, true));
		if (this.player.getGameMode() != GameMode.CREATIVE) {
			this.itemInHand.setAmount(this.itemInHand.getAmount() - 1);
		}
	}

	/**
	 * Uses bucket with tropical fish
	 */
	private void setTropicalFish() {
		setWater();
		this.world.spawn(this.blockLocation, TropicalFish.class, tropicalFish -> {
			if (this.itemInHand.getItemMeta() instanceof TropicalFishBucketMeta tropicalFishBucketMeta) {
				if (tropicalFishBucketMeta.hasVariant()) {
					tropicalFish.setBodyColor(tropicalFishBucketMeta.getBodyColor());
					tropicalFish.setPattern(tropicalFishBucketMeta.getPattern());
					tropicalFish.setPatternColor(tropicalFishBucketMeta.getPatternColor());
				} else {
					tropicalFish.setBodyColor(randomBodyColor());
					tropicalFish.setPattern(randomPattern());
					tropicalFish.setPatternColor(randomBodyColor());
				}
			}
		});
	}

	/**
	 * Uses bucket with axolotl
	 */
	private void setAxolotl() {
		setWater();
		this.world.spawn(this.blockLocation, Axolotl.class, axolotl -> {
			if (this.itemInHand.getItemMeta() instanceof AxolotlBucketMeta axolotlBucketMeta) {
				axolotl.setVariant(axolotlBucketMeta.hasVariant() ? axolotlBucketMeta.getVariant() : randomVariant());
			}
		});
	}

	/**
	 * Uses bucket with Puffer fish / Salmon / Cod
	 */
	private void summonPrimitiveEntities(EntityType entityType) {
		setWater();
		this.world.spawnEntity(this.block.getLocation().add(0.5d, 0.5d, 0.5d), entityType);
	}

	/**
	 * Uses empty bucket
	 */
	private void useEmptyBucket() {
		if (this.block.getType() == Material.LAVA) {
			Main.getCoreProtectAPI().logRemoval(this.player.getName(), this.block.getLocation(), Material.LAVA, this.block.getBlockData());
			this.world.playSound(this.block.getLocation(), Sound.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 2.0f, 1.0f);
			this.itemInHand.setType(this.player.getGameMode() == GameMode.SURVIVAL ? Material.LAVA_BUCKET : this.itemInHand.getType());
			this.block.setType(Material.AIR);
		} else {
			Main.getCoreProtectAPI().logRemoval(player.getName(), this.block.getLocation(), Material.WATER, this.block.getBlockData());
			this.world.playSound(this.block.getLocation(), Sound.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 2.0f, 1.0f);
			if (this.block.getBlockData() instanceof Waterlogged waterlogged) {
				waterlogged.setWaterlogged(false);
				this.block.setBlockData(waterlogged);
			} else {
				this.block.setType(Material.AIR);
			}
			this.itemInHand.setType(player.getGameMode() == GameMode.SURVIVAL ? Material.WATER_BUCKET : this.itemInHand.getType());
		}
	}

	private void setLava() {
		if (this.block.getType().isSolid()) return;
		this.block.setType(Material.LAVA);
		this.world.playSound(this.block.getLocation(), Sound.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 2.0f, 1.0f);
		Main.getCoreProtectAPI().logPlacement(this.player.getName(), this.block.getLocation(), Material.LAVA, this.block.getBlockData());
		setBucketIfSurvival();
	}

	private void setWater() {
		if (this.block.getBlockData() instanceof Waterlogged waterlogged) {
			waterlogged.setWaterlogged(true);
			this.block.setBlockData(waterlogged);
		} else {
			this.block.setType(Material.WATER);
		}
		this.world.playSound(this.block.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 2.0f, 1.0f);
		Main.getCoreProtectAPI().logPlacement(player.getName(), this.block.getLocation(), Material.WATER, this.block.getBlockData());
		setBucketIfSurvival();
	}
}
