package github.minersStudios.utils;

import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
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

public class UseBucket {
    private static Player player;
    private static Block block;
    private static Location blockLocation;

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

    private static void summonPrimitiveEntities(EntityType entityType){
        block.setType(Material.WATER);
        block.getWorld().spawnEntity(block.getLocation().add(.5d, .5d, .5d), entityType);
        setBucketIfSurvival();
    }

    public UseBucket(@Nonnull Player player, @Nonnull Block block) {
        UseBucket.player = player;
        UseBucket.block = block;
        UseBucket.blockLocation = block.getLocation().add(.5d, .5d, .5d);

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        Material material = itemInMainHand.getType();
        switch (material) {
            case LAVA_BUCKET:
            case WATER_BUCKET:
                block.setType(material == Material.LAVA_BUCKET ? Material.LAVA : Material.WATER);
                setBucketIfSurvival();
                break;
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
                block.setType(Material.WATER);
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
                break;
            case AXOLOTL_BUCKET:
                block.setType(Material.WATER);
                block.getWorld().spawn(blockLocation, Axolotl.class, axolotl -> {
                    AxolotlBucketMeta axolotlBucketMeta = (AxolotlBucketMeta) itemInMainHand.getItemMeta();
                    assert axolotlBucketMeta != null;
                    axolotl.setVariant(
                            axolotlBucketMeta.hasVariant() ? axolotlBucketMeta.getVariant()
                            : randomVariant()
                    );
                });
                setBucketIfSurvival();
                break;
            case BUCKET:
                BlockData blockData = block.getBlockData();
                if (blockData instanceof Levelled) {
                    Levelled levelled = (Levelled) blockData;

                    if (levelled.getLevel() == 0) {
                        if (UseBucket.player.getGameMode() != GameMode.SURVIVAL) return;
                        itemInMainHand.setType(
                                (block.getType() == Material.LAVA) ? Material.LAVA_BUCKET
                                : (block.getType() == Material.WATER) ? Material.WATER_BUCKET
                                : Material.BUCKET
                        );
                        block.setType(Material.AIR);
                    }
                }
        }
    }
}
