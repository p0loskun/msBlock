package github.minersStudios.utils;

import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

import javax.annotation.Nonnull;
import java.util.Random;

public class UseBucket {
    private static Player player;

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

    public UseBucket(@Nonnull Player player, @Nonnull Block block) {
        UseBucket.player = player;
        PlayerInventory inventory = player.getInventory();
        switch (inventory.getItemInMainHand().getType()) {
            case LAVA_BUCKET:
                block.setType(Material.LAVA);

                setBucketIfSurvival();
                break;
            case WATER_BUCKET:
                block.setType(Material.WATER);

                setBucketIfSurvival();
                break;
            case PUFFERFISH_BUCKET:
                block.setType(Material.WATER);
                block.getLocation().getBlock().getWorld().spawnEntity(block.getLocation().add(.5d, .5d, .5d), EntityType.PUFFERFISH);

                setBucketIfSurvival();
                break;
            case SALMON_BUCKET:
                block.setType(Material.WATER);
                block.getLocation().getBlock().getWorld().spawnEntity(block.getLocation().add(.5d, .5d, .5d), EntityType.SALMON);

                setBucketIfSurvival();
            case TROPICAL_FISH_BUCKET:
                block.setType(Material.WATER);
                block.getLocation().getBlock().getWorld().spawn(block.getLocation().add(.5d, .5d, .5d), TropicalFish.class, tropicalFish -> {
                    TropicalFishBucketMeta tropicalFishBucketMeta = (TropicalFishBucketMeta) inventory.getItemInMainHand().getItemMeta();
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
            case COD_BUCKET:
                block.setType(Material.WATER);
                block.getLocation().getBlock().getWorld().spawnEntity(block.getLocation().add(.5d, .5d, .5d), EntityType.COD);

                setBucketIfSurvival();
                break;
            case AXOLOTL_BUCKET:
                block.setType(Material.WATER);
                block.getLocation().getBlock().getWorld().spawn(block.getLocation().add(.5d, .5d, .5d), Axolotl.class, axolotl -> {
                    AxolotlBucketMeta axolotlBucketMeta = (AxolotlBucketMeta) inventory.getItemInMainHand().getItemMeta();
                    assert axolotlBucketMeta != null;
                    if (axolotlBucketMeta.hasVariant()) {
                        axolotl.setVariant(axolotlBucketMeta.getVariant());
                    } else {
                        axolotl.setVariant(randomVariant());
                    }
                });
                break;
            case BUCKET:
                BlockData blockData = block.getBlockData();

                if (blockData instanceof Levelled) {
                    Levelled levelled = (Levelled) blockData;

                    if (levelled.getLevel() == 0) {
                        if (UseBucket.player.getGameMode() == GameMode.SURVIVAL)
                            inventory.getItemInMainHand().setType(
                                    (block.getType() == Material.LAVA) ? Material.LAVA_BUCKET :
                                    (block.getType() == Material.WATER) ? Material.WATER_BUCKET :
                                    Material.BUCKET
                            );
                        block.setType(Material.AIR);
                    }
                }
        }
    }
}
