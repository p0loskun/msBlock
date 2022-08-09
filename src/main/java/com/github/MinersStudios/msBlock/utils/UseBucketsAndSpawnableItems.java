package com.github.MinersStudios.msBlock.utils;

import com.github.MinersStudios.msBlock.Main;
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

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Predicate;

public class UseBucketsAndSpawnableItems {
	private final Player player;
	private final Block block;
	private final ItemStack itemInHand;
	private final Location blockLocation;
	private final BlockFace blockFace;

	/**
	 * Uses a bucket vanillish
	 *
	 * @param player    who uses the bucket
	 * @param block     block at face of clicked block
	 * @param blockFace block face
	 * @param hand      hand
	 */
	public UseBucketsAndSpawnableItems(@Nonnull Player player, @Nonnull Block block, @Nonnull BlockFace blockFace, @Nonnull EquipmentSlot hand) {
		ItemStack itemInHand = player.getInventory().getItem(hand);
		this.player = player;
		this.block = block;
		this.itemInHand = itemInHand;
		this.blockLocation = block.getLocation().add(0.5d, 0.5d, 0.5d);
		this.blockFace = blockFace;
		Material itemMaterial = itemInHand.getType();
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
			default -> {
				if (itemMaterial == Material.LAVA_BUCKET && !block.getType().isSolid()) {
					block.setType(Material.LAVA);
					block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 2.0f, 1.0f);
					Main.getCoreProtectAPI().logPlacement(player.getName(), block.getLocation(), Material.LAVA, block.getBlockData());
					setBucketIfSurvival();
				} else if (itemMaterial == Material.WATER_BUCKET) {
					setWater();
				}
			}
		}
	}

	/**
	 * @return random axolotl color variant
	 */
	private Axolotl.Variant randomVariant() {
		return Axolotl.Variant.values()[new Random().nextInt(Axolotl.Variant.values().length)];
	}

	/**
	 * @return random tropical fish body pattern variant
	 */
	private TropicalFish.Pattern randomPattern() {
		return TropicalFish.Pattern.values()[new Random().nextInt(TropicalFish.Pattern.values().length)];
	}

	/**
	 * @return random tropical fish body color variant
	 */
	private DyeColor randomBodyColor() {
		return DyeColor.values()[new Random().nextInt(DyeColor.values().length)];
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
	 * Uses item frame
	 */
	private void setItemFrame() {
		Location eyeLocation = this.player.getEyeLocation();
		Predicate<Entity> filter = entity -> entity != this.player && entity.getType() != EntityType.DROPPED_ITEM;
		RayTraceResult rayTraceResult = this.player.getWorld().rayTraceEntities(eyeLocation, eyeLocation.getDirection(), 4.5d, 0.1d, filter);
		if (
				rayTraceResult != null
				&& rayTraceResult.getHitEntity() != null
				&& (rayTraceResult.getHitEntity().getType() == EntityType.ITEM_FRAME
				|| rayTraceResult.getHitEntity().getType() == EntityType.PAINTING)
		) return;
		this.block.getWorld().spawn(this.blockLocation, ItemFrame.class, itemFrame -> itemFrame.setFacingDirection(this.blockFace, true));
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
		RayTraceResult rayTraceResult = this.player.getWorld().rayTraceEntities(eyeLocation, eyeLocation.getDirection(), 4.5d, 0.1d, filter);
		if (
				rayTraceResult != null
				&& rayTraceResult.getHitEntity() != null
				&& (rayTraceResult.getHitEntity().getType() == EntityType.ITEM_FRAME
				|| rayTraceResult.getHitEntity().getType() == EntityType.PAINTING)
		) return;
		this.block.getWorld().spawn(this.blockLocation, Painting.class, painting -> painting.setFacingDirection(this.blockFace, true));
		if (this.player.getGameMode() != GameMode.CREATIVE) {
			this.itemInHand.setAmount(this.itemInHand.getAmount() - 1);
		}
	}

	/**
	 * Uses bucket with tropical fish
	 */
	private void setTropicalFish() {
		setWater();
		this.block.getWorld().spawn(this.blockLocation, TropicalFish.class, tropicalFish -> {
			if (this.itemInHand.getItemMeta() instanceof TropicalFishBucketMeta tropicalFishBucketMeta)
				if (tropicalFishBucketMeta.hasVariant()) {
					tropicalFish.setBodyColor(tropicalFishBucketMeta.getBodyColor());
					tropicalFish.setPattern(tropicalFishBucketMeta.getPattern());
					tropicalFish.setPatternColor(tropicalFishBucketMeta.getPatternColor());
				} else {
					tropicalFish.setBodyColor(randomBodyColor());
					tropicalFish.setPattern(randomPattern());
					tropicalFish.setPatternColor(randomBodyColor());
				}
		});
	}

	/**
	 * Uses bucket with axolotl
	 */
	private void setAxolotl() {
		setWater();
		this.block.getWorld().spawn(this.blockLocation, Axolotl.class, axolotl -> {
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
		this.block.getWorld().spawnEntity(this.block.getLocation().add(0.5d, 0.5d, 0.5d), entityType);
	}

	/**
	 * Uses empty bucket
	 */
	private void useEmptyBucket() {
		if (this.block.getType() == Material.LAVA) {
			Main.getCoreProtectAPI().logRemoval(this.player.getName(), this.block.getLocation(), Material.LAVA, this.block.getBlockData());
			this.block.getWorld().playSound(this.block.getLocation(), Sound.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 2.0f, 1.0f);
			this.itemInHand.setType(this.player.getGameMode() == GameMode.SURVIVAL ? Material.LAVA_BUCKET : this.itemInHand.getType());
			this.block.setType(Material.AIR);
		} else {
			Main.getCoreProtectAPI().logRemoval(player.getName(), this.block.getLocation(), Material.WATER, this.block.getBlockData());
			this.block.getWorld().playSound(this.block.getLocation(), Sound.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 2.0f, 1.0f);
			if (this.block.getBlockData() instanceof Waterlogged waterlogged) {
				waterlogged.setWaterlogged(false);
				this.block.setBlockData(waterlogged);
			} else {
				this.block.setType(Material.AIR);
			}
			this.itemInHand.setType(player.getGameMode() == GameMode.SURVIVAL ? Material.WATER_BUCKET : this.itemInHand.getType());
		}
	}

	private void setWater() {
		if (this.block.getBlockData() instanceof Waterlogged waterlogged) {
			waterlogged.setWaterlogged(true);
			this.block.setBlockData(waterlogged);
		} else {
			this.block.setType(Material.WATER);
		}
		this.block.getWorld().playSound(this.block.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 2.0f, 1.0f);
		Main.getCoreProtectAPI().logPlacement(player.getName(), this.block.getLocation(), Material.WATER, this.block.getBlockData());
		setBucketIfSurvival();
	}
}
