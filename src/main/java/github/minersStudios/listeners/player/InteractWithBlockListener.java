package github.minersStudios.listeners.player;

import github.minersStudios.utils.UseBucket;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static github.minersStudios.utils.PlayerUtils.*;

public class InteractWithBlockListener implements Listener {
    private static ItemActionContext itemActionContext;
    private static EnumHand enumHand;
    private static net.minecraft.world.item.ItemStack nmsItem;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        assert event.getClickedBlock() != null;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock().getType() != Material.NOTE_BLOCK) return;
        event.setCancelled(true);

        Block clickedBlock = event.getClickedBlock(),
                blockAtFace = clickedBlock.getRelative(event.getBlockFace());
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        if (
                itemInMainHand.getType().isAir()
                || itemInMainHand.getType() == Material.PAPER
                || itemInMainHand.getType() == Material.LEATHER_HORSE_ARMOR
        ) return;
        for (Entity nearbyEntity : clickedBlock.getWorld().getNearbyEntities(blockAtFace.getLocation().add(0.5d, 0.5d, 0.5d), 0.5d, 0.5d, 0.5d))
            if(!(nearbyEntity instanceof Item) && itemInMainHand.getType().isSolid()) return;

        nmsItem = CraftItemStack.asNMSCopy(itemInMainHand);
        enumHand = parseEnumHand(getEquipmentSlot(player.getInventory(), itemInMainHand));
        Location playerEyeLocation = player.getEyeLocation();
        EntityPlayer entityPlayer = parseHuman(player);
        MovingObjectPositionBlock movingObjectPositionBlock = getMovingObjectPositionBlock(player, blockAtFace.getLocation(), false);
        Location interactionPoint = getInteractionPoint(playerEyeLocation, 8, true);
        assert interactionPoint != null;
        if (itemInMainHand.getType().toString().contains("BUCKET")) {
            new UseBucket(player, blockAtFace);
            return;
        }
        itemActionContext = new ItemActionContext(entityPlayer, enumHand, movingObjectPositionBlock);

        if (Tag.STAIRS.isTagged(itemInMainHand.getType())) {
            useOn(clickedBlock.getRelative(event.getBlockFace()));
            Stairs data = (Stairs) blockAtFace.getBlockData();
            switch (event.getBlockFace()) {
                case UP:
                    data.setHalf(Bisected.Half.BOTTOM);
                    break;
                case DOWN:
                    data.setHalf(Bisected.Half.TOP);
                    break;
                default:
                    data.setHalf(interactionPoint.getY() < .5d && interactionPoint.getY() >= 0d ? Bisected.Half.BOTTOM : Bisected.Half.TOP);
                    break;
            }
            blockAtFace.setBlockData(data);
        } else if (Tag.SLABS.isTagged(itemInMainHand.getType())) {
            Slab.Type dataType;
            if (blockAtFace.getType() == itemInMainHand.getType()) {
                dataType = Slab.Type.DOUBLE;
            } else {
                if ((interactionPoint.getY() > 0d && interactionPoint.getY() < .5d) || interactionPoint.getY() == 1d) {
                    dataType = Slab.Type.BOTTOM;
                } else {
                    dataType = Slab.Type.TOP;
                }
                useOn(clickedBlock.getRelative(event.getBlockFace()));
            }

            Slab data = (Slab) blockAtFace.getBlockData();
            data.setType(dataType);
            blockAtFace.setBlockData(data);
        } else if (Tag.SHULKER_BOXES.isTagged(itemInMainHand.getType())) {
            useOn(clickedBlock.getRelative(event.getBlockFace()));

            Directional directional = (Directional) blockAtFace.getBlockData();
            directional.setFacing(event.getBlockFace());
            blockAtFace.setBlockData(directional);
        } else {
            useOn(clickedBlock.getRelative(event.getBlockFace()));
        }
    }

    private static void useOn(Block block){
        nmsItem.useOn(itemActionContext, enumHand);
        block.getWorld().playSound(
                block.getLocation(),
                block.getType().createBlockData().getSoundGroup().getPlaceSound(),
                SoundCategory.BLOCKS,
                2.0f,
                block.getType().createBlockData().getSoundGroup().getPitch()
        );
    }

}
