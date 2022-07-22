package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import github.minersStudios.msBlock.utils.UseBucket;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.context.ItemActionContext;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.utils.PlayerUtils.*;

public class PlayerInteractListener implements Listener {
    private static Block blockAtFace;
    private static Location interactionPoint;
    private static ItemActionContext itemActionContext;
    private static EnumHand enumHand;
    private static ItemStack itemInHand;
    private static Player player;
    private static net.minecraft.world.item.ItemStack nmsItem;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || event.getHand() == null) return;
        Block clickedBlock = event.getClickedBlock();
        EquipmentSlot hand = event.getHand();
        player = event.getPlayer();
        if (hand != EquipmentSlot.HAND && player.getInventory().getItemInMainHand().getType() == Material.PAPER) {
            hand = EquipmentSlot.HAND;
            event.setCancelled(true);
        }
        itemInHand = player.getInventory().getItem(hand);
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && clickedBlock.getType() == Material.NOTE_BLOCK) {
            event.setCancelled(true);
            blockAtFace = clickedBlock.getRelative(event.getBlockFace());
            if (
                    !itemInHand.getType().isAir()
                    && itemInHand.getType() != Material.PAPER
                    && itemInHand.getType() != Material.LEATHER_HORSE_ARMOR
                    && player.getGameMode() != GameMode.ADVENTURE
            ) {
                for (Entity nearbyEntity : clickedBlock.getWorld().getNearbyEntities(blockAtFace.getLocation().clone().add(0.5d, 0.5d, 0.5d), 0.5d, 0.5d, 0.5d))
                    if (!(nearbyEntity instanceof Item) && itemInHand.getType().isSolid()) return;
                nmsItem = CraftItemStack.asNMSCopy(itemInHand);
                enumHand = EnumHand.a;
                interactionPoint = getInteractionPoint(player.getEyeLocation(), 8);
                itemActionContext = new ItemActionContext(convertPlayer(player), enumHand, getMovingObjectPositionBlock(player, blockAtFace.getLocation()));
                if (interactionPoint != null)
                    useItemInHand(event);
            }
        }
        if (
                event.getAction() == Action.RIGHT_CLICK_BLOCK
                && itemInHand.getType() == Material.PAPER
                && (event.getHand() == EquipmentSlot.HAND || hand == EquipmentSlot.OFF_HAND)
                && player.getGameMode() != GameMode.ADVENTURE
                && BlockUtils.REPLACE.contains(clickedBlock.getRelative(event.getBlockFace()).getType())
        ) {
            if ((clickedBlock.getType().isInteractable() && clickedBlock.getType() != Material.NOTE_BLOCK) && !player.isSneaking()) return;
            Block replaceableBlock = BlockUtils.REPLACE.contains(clickedBlock.getType()) ? clickedBlock
                    : clickedBlock.getRelative(event.getBlockFace());
            for (Entity nearbyEntity : replaceableBlock.getWorld().getNearbyEntities(replaceableBlock.getLocation().add(0.5d, 0.5d, 0.5d), 0.5d, 0.5d, 0.5d))
                if (!(nearbyEntity instanceof Item) && !(nearbyEntity instanceof ItemFrame)) return;
            ItemMeta itemMeta = itemInHand.getItemMeta();
            if (itemMeta == null || !itemMeta.hasCustomModelData()) return;
            CustomBlockMaterial.getCustomBlockMaterial(itemMeta.getCustomModelData()).setCustomBlock(replaceableBlock, player, hand);
        }
    }

    private static void useItemInHand(@Nonnull PlayerInteractEvent event) {
        if (itemInHand.getType().toString().contains("BUCKET") && itemInHand.getType() != Material.POWDER_SNOW_BUCKET) {
            new UseBucket(player, blockAtFace);
        } else if (Tag.STAIRS.isTagged(itemInHand.getType()) && !blockAtFace.getType().isSolid()) {
            useOn();
            if (blockAtFace.getBlockData() instanceof Stairs stairs) {
                stairs.setHalf(
                        event.getBlockFace() == BlockFace.UP ? Bisected.Half.BOTTOM
                        : event.getBlockFace() == BlockFace.DOWN ? Bisected.Half.TOP
                        : interactionPoint.getY() < 0.5d && interactionPoint.getY() >= 0.0d ? Bisected.Half.BOTTOM
                        : Bisected.Half.TOP
                );
                blockAtFace.setBlockData(stairs);
            }
        } else if (Tag.SLABS.isTagged(itemInHand.getType())) {
            boolean placeDouble = true;
            Material itemMaterial = itemInHand.getType();
            if (blockAtFace.getType() != itemMaterial) {
                useOn();
                placeDouble = false;
            }
            if (!(blockAtFace.getBlockData() instanceof Slab slab)) return;
            if (placeDouble && blockAtFace.getType() == itemMaterial) {
                slab.setType(Slab.Type.DOUBLE);
                blockAtFace.getWorld().playSound(
                        blockAtFace.getLocation(),
                        slab.getSoundGroup().getPlaceSound(),
                        SoundCategory.BLOCKS,
                        slab.getSoundGroup().getVolume(),
                        slab.getSoundGroup().getPitch()
                );
                if (player.getGameMode() == GameMode.SURVIVAL)
                    itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else if (event.getBlockFace() == BlockFace.DOWN || interactionPoint.getY() > 0.5d && interactionPoint.getY() < 1.0d && blockAtFace.getType() == itemMaterial) {
                slab.setType(Slab.Type.TOP);
            } else if (event.getBlockFace() == BlockFace.UP || interactionPoint.getY() < 0.5d && interactionPoint.getY() > 0.0d && blockAtFace.getType() == itemMaterial) {
                slab.setType(Slab.Type.BOTTOM);
            }
            blockAtFace.setBlockData(slab);
        } else if (Tag.SHULKER_BOXES.isTagged(itemInHand.getType()) && !blockAtFace.getType().isSolid()) {
            useOn();
            if (blockAtFace.getBlockData() instanceof Directional directional) {
                directional.setFacing(event.getBlockFace());
                blockAtFace.setBlockData(directional);
            }
        } else if (!blockAtFace.getType().isSolid() && blockAtFace.getType() != itemInHand.getType()) {
            useOn();
        }
    }

    private static void useOn() {
        nmsItem.useOn(itemActionContext, enumHand);
        if (!itemInHand.getType().isBlock()) return;
        BlockData blockData = blockAtFace.getBlockData();
        Main.coreProtectAPI.logPlacement(player.getName(), blockAtFace.getLocation(), itemInHand.getType(), blockData);
        blockAtFace.getWorld().playSound(
                blockAtFace.getLocation(),
                blockData.getSoundGroup().getPlaceSound(),
                SoundCategory.BLOCKS,
                blockData.getSoundGroup().getVolume(),
                blockData.getSoundGroup().getPitch()
        );
    }
}
