package github.minersStudios.msBlock.utils;

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

import static github.minersStudios.msBlock.Main.coreProtectAPI;

public class UseBucket {

    private static Player player;
    private static Block block;
    private static ItemStack itemInMainHand;
    private static Location blockLocation;

    /**
     * Uses a bucket vanillish
     *
     * @param player who uses the bucket
     * @param block block at face of interacted block
     */
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
     * Uses bucket with tropical fish
     */
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

    /**
     * Uses bucket with axolotl
     */
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

    /**
     * Uses bucket with Puffer fish / Salmon / Cod
     */
    private static void summonPrimitiveEntities(EntityType entityType){
        block.setType(Material.WATER);
        block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 2.0f, 1.0f);
        coreProtectAPI.logPlacement(player.getName(), block.getLocation(), Material.WATER, block.getBlockData());
        block.getWorld().spawnEntity(block.getLocation().add(.5d, .5d, .5d), entityType);
        setBucketIfSurvival();
    }

    /**
     * Uses empty bucket
     */
    private static void useEmptyBucket() {
        BlockData blockData = block.getBlockData();
        if (!(blockData instanceof Levelled)) return;
        Levelled levelled = (Levelled) blockData;
        if (levelled.getLevel() != 0) return;
        if (block.getType() == Material.LAVA) {
            coreProtectAPI.logRemoval(player.getName(), block.getLocation(), Material.LAVA, block.getBlockData());
            block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 2.0f, 1.0f);
            itemInMainHand.setType(player.getGameMode() == GameMode.SURVIVAL ? Material.LAVA_BUCKET : itemInMainHand.getType());
        } else {
            coreProtectAPI.logRemoval(player.getName(), block.getLocation(), Material.WATER, block.getBlockData());
            block.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 2.0f, 1.0f);
            itemInMainHand.setType(player.getGameMode() == GameMode.SURVIVAL ? Material.WATER_BUCKET : itemInMainHand.getType());
        }
        block.setType(Material.AIR);
    }
}
