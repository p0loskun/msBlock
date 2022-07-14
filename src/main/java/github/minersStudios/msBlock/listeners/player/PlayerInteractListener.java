package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.Main;
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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.utils.PlayerUtils.*;

public class PlayerInteractListener implements Listener {
    private static Block blockAtFace;
    private static Location interactionPoint;
    private static ItemActionContext itemActionContext;
    private static EnumHand enumHand;
    private static ItemStack itemInMainHand;
    private static Player player;
    private static net.minecraft.world.item.ItemStack nmsItem;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Block clickedBlock = event.getClickedBlock();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || clickedBlock.getType() != Material.NOTE_BLOCK) return;
        event.setCancelled(true);
        player = event.getPlayer();
        itemInMainHand = player.getInventory().getItemInMainHand();
        blockAtFace = clickedBlock.getRelative(event.getBlockFace());
        if (
                itemInMainHand.getType().isAir()
                || itemInMainHand.getType() == Material.PAPER
                || itemInMainHand.getType() == Material.LEATHER_HORSE_ARMOR
                || player.getGameMode() == GameMode.ADVENTURE
        ) return;
        for (Entity nearbyEntity : clickedBlock.getWorld().getNearbyEntities(blockAtFace.getLocation().clone().add(0.5d, 0.5d, 0.5d), 0.5d, 0.5d, 0.5d)) {
            if (!(nearbyEntity instanceof Item) && itemInMainHand.getType().isSolid()) return;
        }
        nmsItem = CraftItemStack.asNMSCopy(itemInMainHand);
        enumHand = convertEnumHand(getEquipmentSlot(player.getInventory(), itemInMainHand));
        interactionPoint = getInteractionPoint(player.getEyeLocation(), 8);
        itemActionContext = new ItemActionContext(convertPlayer(player), enumHand, getMovingObjectPositionBlock(player, blockAtFace.getLocation()));
        if (interactionPoint != null) {
            useItemInHand(event);
        }
    }

    private static void useItemInHand(@Nonnull PlayerInteractEvent event){
        if (itemInMainHand.getType().toString().contains("BUCKET") && itemInMainHand.getType() != Material.POWDER_SNOW_BUCKET) {
            new UseBucket(player, blockAtFace);
        } else if (Tag.STAIRS.isTagged(itemInMainHand.getType()) && !blockAtFace.getType().isSolid()) {
            useOn();
            if (blockAtFace.getBlockData() instanceof Stairs stairs) {
                stairs.setHalf(
                        event.getBlockFace() == BlockFace.UP ? Bisected.Half.BOTTOM
                        : event.getBlockFace() == BlockFace.DOWN ? Bisected.Half.TOP
                        : (interactionPoint.getY() < 0.5d && interactionPoint.getY() >= 0.0d ? Bisected.Half.BOTTOM
                        : Bisected.Half.TOP)
                );
                blockAtFace.setBlockData(stairs);
            }
        } else if (Tag.SLABS.isTagged(itemInMainHand.getType())) {
            boolean placeDouble = true;
            Material itemMaterial = itemInMainHand.getType();
            if(blockAtFace.getType() != itemMaterial){
                useOn();
                placeDouble = false;
            }
            if (blockAtFace.getBlockData() instanceof Slab slab) {
                if(placeDouble && blockAtFace.getType() == itemMaterial){
                    slab.setType(Slab.Type.DOUBLE);
                    blockAtFace.getWorld().playSound(
                            blockAtFace.getLocation(),
                            slab.getSoundGroup().getPlaceSound(),
                            SoundCategory.BLOCKS,
                            slab.getSoundGroup().getVolume(),
                            slab.getSoundGroup().getPitch()
                    );
                    if(player.getGameMode() == GameMode.SURVIVAL) itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
                } else if(interactionPoint.getY() < 0.5d && interactionPoint.getY() >= 0.0d && blockAtFace.getType() == itemMaterial){
                    slab.setType(Slab.Type.BOTTOM);
                }
                blockAtFace.setBlockData(slab);
            }
        } else if (Tag.SHULKER_BOXES.isTagged(itemInMainHand.getType()) && !blockAtFace.getType().isSolid()) {
            useOn();
            if(blockAtFace.getBlockData() instanceof Directional directional){
                directional.setFacing(event.getBlockFace());
                blockAtFace.setBlockData(directional);
            }
        } else if(!blockAtFace.getType().isSolid() && blockAtFace.getType() != itemInMainHand.getType()){
            useOn();
        }
    }

    private static void useOn(){
        nmsItem.useOn(itemActionContext, enumHand);
        if(!itemInMainHand.getType().isBlock()) return;
        BlockData blockData = blockAtFace.getBlockData();
        Main.coreProtectAPI.logPlacement(player.getName(), blockAtFace.getLocation(), itemInMainHand.getType(), blockData);
        blockAtFace.getWorld().playSound(
                blockAtFace.getLocation(),
                blockData.getSoundGroup().getPlaceSound(),
                SoundCategory.BLOCKS,
                blockData.getSoundGroup().getVolume(),
                blockData.getSoundGroup().getPitch()
        );
    }
}
