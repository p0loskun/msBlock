package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.utils.UseBucket;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.context.ItemActionContext;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.Main.coreProtectAPI;
import static github.minersStudios.msBlock.utils.PlayerUtils.*;

public class InteractWithBlockListener implements Listener {

    private static Block blockAtFace;
    private static Location interactionPoint;
    private static ItemActionContext itemActionContext;
    private static EnumHand enumHand;
    private static ItemStack itemInMainHand;
    private static Player player;
    private static net.minecraft.world.item.ItemStack nmsItem;

    @EventHandler
    public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
        assert event.getClickedBlock() != null;
        Block clickedBlock = event.getClickedBlock();
        player = event.getPlayer();
        if (
                event.getAction() != Action.RIGHT_CLICK_BLOCK
                || clickedBlock.getType() != Material.NOTE_BLOCK
                || player.isSneaking()
        ) return;
        event.setCancelled(true);
        itemInMainHand = player.getInventory().getItemInMainHand();
        blockAtFace = clickedBlock.getRelative(event.getBlockFace());
        if (
                itemInMainHand.getType().isAir()
                || itemInMainHand.getType() == Material.PAPER
                || itemInMainHand.getType() == Material.LEATHER_HORSE_ARMOR
                || player.getGameMode() == GameMode.ADVENTURE
        ) return;
        for (Entity nearbyEntity : clickedBlock.getWorld().getNearbyEntities(blockAtFace.getLocation().add(0.5d, 0.5d, 0.5d), 0.5d, 0.5d, 0.5d)) {
            if (!(nearbyEntity instanceof Item) && itemInMainHand.getType().isSolid()) return;
        }
        nmsItem = CraftItemStack.asNMSCopy(itemInMainHand);
        enumHand = convertEnumHand(getEquipmentSlot(player.getInventory(), itemInMainHand));
        interactionPoint = getInteractionPoint(player.getEyeLocation(), 8);
        itemActionContext = new ItemActionContext(convertPlayer(player), enumHand, getMovingObjectPositionBlock(player, blockAtFace.getLocation()));
        if(interactionPoint != null)
            useItemInHand(event);
    }

    private static void useOn(@Nonnull Block block){
        nmsItem.useOn(itemActionContext, enumHand);
        if(!itemInMainHand.getType().isBlock()) return;
        coreProtectAPI.logPlacement(player.getName(), block.getLocation(), itemInMainHand.getType(), block.getBlockData());
        block.getWorld().playSound(
                block.getLocation(),
                block.getType().createBlockData().getSoundGroup().getPlaceSound(),
                SoundCategory.BLOCKS,
                2.0f,
                block.getType().createBlockData().getSoundGroup().getPitch()
        );
    }

    private static void useItemInHand(@Nonnull PlayerInteractEvent event){
        if (itemInMainHand.getType().toString().contains("BUCKET") && itemInMainHand.getType() != Material.POWDER_SNOW_BUCKET) {
            new UseBucket(player, blockAtFace);
        } else if (Tag.STAIRS.isTagged(itemInMainHand.getType())) {
            useOn(blockAtFace);
            Stairs data = (Stairs) blockAtFace.getBlockData();
            data.setHalf(event.getBlockFace() == BlockFace.UP ? Bisected.Half.BOTTOM
                    : event.getBlockFace() == BlockFace.DOWN ? Bisected.Half.TOP
                    : (interactionPoint.getY() < 0.5d && interactionPoint.getY() >= 0.0d ? Bisected.Half.BOTTOM
                    : Bisected.Half.TOP));
            blockAtFace.setBlockData(data);
        } else if (Tag.SLABS.isTagged(itemInMainHand.getType()) || blockAtFace.getType() == itemInMainHand.getType()) {
            Slab.Type dataType;
            if (blockAtFace.getType() == itemInMainHand.getType()) {
                dataType = Slab.Type.DOUBLE;
                blockAtFace.getWorld().playSound(blockAtFace.getLocation(), blockAtFace.getType().createBlockData().getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 2.0f, blockAtFace.getType().createBlockData().getSoundGroup().getPitch());
            } else {
                if ((interactionPoint.getY() > 0.0d && interactionPoint.getY() < 0.5d) || interactionPoint.getY() == 1.0d) dataType = Slab.Type.BOTTOM;
                else dataType = Slab.Type.TOP;
                useOn(blockAtFace);
            }
            Slab data = (Slab) blockAtFace.getBlockData();
            data.setType(dataType);
            blockAtFace.setBlockData(data);
        } else if (Tag.SHULKER_BOXES.isTagged(itemInMainHand.getType())) {
            useOn(blockAtFace);
            Directional directional = (Directional) blockAtFace.getBlockData();
            directional.setFacing(event.getBlockFace());
            blockAtFace.setBlockData(directional);
        } else {
            useOn(blockAtFace);
        }
    }
}
