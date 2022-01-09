package github.minersStudios.utils;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

import javax.annotation.Nonnull;
import java.util.Random;

import static github.minersStudios.Main.coreProtectAPI;

public class UseBucket {
    private static Player player;
    private static Block block;
    private static ItemStack itemInMainHand;
    private static Location blockLocation;

    public UseBucket(@Nonnull Player player, @Nonnull Block block) {
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        UseBucket.player = player;
        UseBucket.block = block;
        UseBucket.itemInMainHand = itemInMainHand;
        UseBucket.blockLocation = block.getLocation().add(.5d, .5d, .5d);

        Material itemMaterial = itemInMainHand.getType();
        switch (itemMaterial) {
            case PUFFERFISH_BUCKET:
                summonPrimitiveEntities(EntityType.PUFFERFISH);
                break;
            case SALMON_BUCKET:
                summonPrimitiveEntities(EntityType.SALMON);
                break;
            case COD_BUCKET:
                summonPrimitiveEntities(EntityType.COD);
                break;
            case TROPICAL_FISH_BUCKET:
                setTropicalFish();
                break;
            case AXOLOTL_BUCKET:
                setAxolotl();
                break;
            case BUCKET:
                useEmptyBucket();
                break;
            default:
                if(itemMaterial == Material.LAVA_BUCKET){
                    block.setType(Material.LAVA);
                    block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 2.0f, 1.0f);
                    coreProtectAPI.logPlacement(player.getName(), block.getLocation(), Material.LAVA, block.getBlockData());
                    setBucketIfSurvival();
                } else if(itemMaterial == Material.WATER_BUCKET){
                    block.setType(Material.WATER);
                    block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 2.0f, 1.0f);
                    coreProtectAPI.logPlacement(player.getName(), block.getLocation(), Material.WATER, block.getBlockData());
                    setBucketIfSurvival();
                }
                break;
        }
    }

    private static Axolotl.Variant randomVariant() {
        return Axolotl.Variant.values()[new Random().nextInt(Axolotl.Variant.values().length)];
    }

    private static TropicalFish.Pattern randomPattern() {
        return TropicalFish.Pattern.values()[new Random().nextInt(TropicalFish.Pattern.values().length)];
    }

    private static DyeColor randomBodyColor() {
        return DyeColor.values()[new Random().nextInt(DyeColor.values().length)];
    }

    private static void setBucketIfSurvival() {
        if (player.getGameMode() == GameMode.SURVIVAL)
            player.getInventory().getItemInMainHand().setType(Material.BUCKET);
    }

    private static void setTropicalFish(){
        block.setType(Material.WATER);
        block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 2.0f, 1.0f);
        coreProtectAPI.logPlacement(player.getName(), block.getLocation(), Material.WATER, block.getBlockData());
        block.getWorld().spawn(blockLocation, TropicalFish.class, tropicalFish -> {
            TropicalFishBucketMeta tropicalFishBucketMeta = (TropicalFishBucketMeta) itemInMainHand.getItemMeta();
            assert tropicalFishBucketMeta != null;
            if(tropicalFishBucketMeta.hasVariant()){
                tropicalFish.setBodyColor(tropicalFishBucketMeta.getBodyColor());
                tropicalFish.setPattern(tropicalFishBucketMeta.getPattern());
                tropicalFish.setPatternColor(tropicalFishBucketMeta.getPatternColor());
            } else {
                tropicalFish.setBodyColor(randomBodyColor());
                tropicalFish.setPattern(randomPattern());
                tropicalFish.setPatternColor(randomBodyColor());
            }

        });
        setBucketIfSurvival();
    }

    private static void setAxolotl(){
        block.setType(Material.WATER);
        block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 2.0f, 1.0f);
        coreProtectAPI.logPlacement(player.getName(), block.getLocation(), Material.WATER, block.getBlockData());
        block.getWorld().spawn(blockLocation, Axolotl.class, axolotl -> {
            AxolotlBucketMeta axolotlBucketMeta = (AxolotlBucketMeta) itemInMainHand.getItemMeta();
            assert axolotlBucketMeta != null;
            axolotl.setVariant(axolotlBucketMeta.hasVariant() ? axolotlBucketMeta.getVariant() : randomVariant());
        });
        setBucketIfSurvival();
    }

    private static void summonPrimitiveEntities(EntityType entityType){
        block.setType(Material.WATER);
        block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 2.0f, 1.0f);
        coreProtectAPI.logPlacement(player.getName(), block.getLocation(), Material.WATER, block.getBlockData());
        block.getWorld().spawnEntity(block.getLocation().add(.5d, .5d, .5d), entityType);
        setBucketIfSurvival();
    }

    private static void useEmptyBucket(){
        BlockData blockData = block.getBlockData();
        if (blockData instanceof Levelled) {
            Levelled levelled = (Levelled) blockData;

            if (levelled.getLevel() == 0) {
                if (UseBucket.player.getGameMode() != GameMode.SURVIVAL) return;
                if(block.getType() == Material.LAVA){
                    coreProtectAPI.logRemoval(player.getName(), block.getLocation(), Material.LAVA, block.getBlockData());
                    block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 2.0f, 1.0f);
                    itemInMainHand.setType(Material.LAVA_BUCKET);
                } else if (block.getType() == Material.WATER){
                    coreProtectAPI.logRemoval(player.getName(), block.getLocation(), Material.WATER, block.getBlockData());
                    block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 2.0f, 1.0f);
                    itemInMainHand.setType(Material.WATER_BUCKET);
                } else {
                    coreProtectAPI.logRemoval(player.getName(), block.getLocation(), Material.AIR, block.getBlockData());
                    itemInMainHand.setType(Material.BUCKET);
                }
                block.setType(Material.AIR);
            }
        }

    }
}
