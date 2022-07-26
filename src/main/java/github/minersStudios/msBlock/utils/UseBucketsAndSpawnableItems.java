package github.minersStudios.msBlock.utils;

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

import static github.minersStudios.msBlock.Main.coreProtectAPI;

public class UseBucketsAndSpawnableItems {
    private static Player player;
    private static Block block;
    private static ItemStack itemInHand;
    private static Location blockLocation;
    private static BlockFace blockFace;

    /**
     * Uses a bucket vanillish
     *
     * @param player who uses the bucket
     * @param block  block at face of clicked block
     * @param blockFace block face
     * @param hand hand
     */
    public UseBucketsAndSpawnableItems(@Nonnull Player player, @Nonnull Block block, @Nonnull BlockFace blockFace, @Nonnull EquipmentSlot hand) {
        ItemStack itemInHand = player.getInventory().getItem(hand);
        if (itemInHand == null) return;

        UseBucketsAndSpawnableItems.player = player;
        UseBucketsAndSpawnableItems.block = block;
        UseBucketsAndSpawnableItems.itemInHand = itemInHand;
        UseBucketsAndSpawnableItems.blockLocation = block.getLocation().add(0.5d, 0.5d, 0.5d);
        UseBucketsAndSpawnableItems.blockFace = blockFace;

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
                    coreProtectAPI.logPlacement(player.getName(), block.getLocation(), Material.LAVA, block.getBlockData());
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
    private static Axolotl.Variant randomVariant() {
        return Axolotl.Variant.values()[new Random().nextInt(Axolotl.Variant.values().length)];
    }

    /**
     * @return random tropical fish body pattern variant
     */
    private static TropicalFish.Pattern randomPattern() {
        return TropicalFish.Pattern.values()[new Random().nextInt(TropicalFish.Pattern.values().length)];
    }

    /**
     * @return random tropical fish body color variant
     */
    private static DyeColor randomBodyColor() {
        return DyeColor.values()[new Random().nextInt(DyeColor.values().length)];
    }

    /**
     * Sets empty bucket in hand if player GameMode == survival
     */
    private static void setBucketIfSurvival() {
        if (player.getGameMode() == GameMode.SURVIVAL)
            player.getInventory().getItemInMainHand().setType(Material.BUCKET);
    }

    /**
     * Uses item frame
     */
    private static void setItemFrame() {
        Location eyeLocation = player.getEyeLocation();
        Predicate<Entity> filter = entity -> entity != player && entity.getType() != EntityType.DROPPED_ITEM;
        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(eyeLocation, eyeLocation.getDirection(), 4.5d, 0.1d, filter);
        if (rayTraceResult != null && rayTraceResult.getHitEntity() != null)
            if (rayTraceResult.getHitEntity().getType() == EntityType.ITEM_FRAME || rayTraceResult.getHitEntity().getType() == EntityType.PAINTING) return;
        block.getWorld().spawn(blockLocation, ItemFrame.class, itemFrame -> itemFrame.setFacingDirection(blockFace, true));
        if (player.getGameMode() != GameMode.CREATIVE)
            itemInHand.setAmount(itemInHand.getAmount() - 1);
    }

    /**
     * Uses painting
     */
    private static void setPainting() {
        Location eyeLocation = player.getEyeLocation();
        Predicate<Entity> filter = entity -> entity != player && entity.getType() != EntityType.DROPPED_ITEM;
        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(eyeLocation, eyeLocation.getDirection(), 4.5d, 0.1d, filter);
        if (rayTraceResult != null && rayTraceResult.getHitEntity() != null)
            if (rayTraceResult.getHitEntity().getType() == EntityType.ITEM_FRAME || rayTraceResult.getHitEntity().getType() == EntityType.PAINTING) return;
        block.getWorld().spawn(blockLocation, Painting.class, painting -> painting.setFacingDirection(blockFace, true));
        if (player.getGameMode() != GameMode.CREATIVE)
            itemInHand.setAmount(itemInHand.getAmount() - 1);
    }

    /**
     * Uses bucket with tropical fish
     */
    private static void setTropicalFish() {
        setWater();
        block.getWorld().spawn(blockLocation, TropicalFish.class, tropicalFish -> {
            if (itemInHand.getItemMeta() instanceof TropicalFishBucketMeta tropicalFishBucketMeta)
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
    private static void setAxolotl() {
        setWater();
        block.getWorld().spawn(blockLocation, Axolotl.class, axolotl -> {
            if (itemInHand.getItemMeta() instanceof AxolotlBucketMeta axolotlBucketMeta)
                axolotl.setVariant(axolotlBucketMeta.hasVariant() ? axolotlBucketMeta.getVariant() : randomVariant());
        });
    }

    /**
     * Uses bucket with Puffer fish / Salmon / Cod
     */
    private static void summonPrimitiveEntities(EntityType entityType) {
        setWater();
        block.getWorld().spawnEntity(block.getLocation().add(0.5d, 0.5d, 0.5d), entityType);
    }

    /**
     * Uses empty bucket
     */
    private static void useEmptyBucket() {
        if (block.getType() == Material.LAVA) {
            coreProtectAPI.logRemoval(player.getName(), block.getLocation(), Material.LAVA, block.getBlockData());
            block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 2.0f, 1.0f);
            itemInHand.setType(player.getGameMode() == GameMode.SURVIVAL ? Material.LAVA_BUCKET : itemInHand.getType());
            block.setType(Material.AIR);
        } else {
            coreProtectAPI.logRemoval(player.getName(), block.getLocation(), Material.WATER, block.getBlockData());
            block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 2.0f, 1.0f);
            if (block.getBlockData() instanceof Waterlogged waterlogged) {
                waterlogged.setWaterlogged(false);
                block.setBlockData(waterlogged);
            } else {
                block.setType(Material.AIR);
            }
            itemInHand.setType(player.getGameMode() == GameMode.SURVIVAL ? Material.WATER_BUCKET : itemInHand.getType());
        }
    }

    private static void setWater() {
        if (block.getBlockData() instanceof Waterlogged waterlogged) {
            waterlogged.setWaterlogged(true);
            block.setBlockData(waterlogged);
        } else {
            block.setType(Material.WATER);
        }
        block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 2.0f, 1.0f);
        coreProtectAPI.logPlacement(player.getName(), block.getLocation(), Material.WATER, block.getBlockData());
        setBucketIfSurvival();
    }
}
