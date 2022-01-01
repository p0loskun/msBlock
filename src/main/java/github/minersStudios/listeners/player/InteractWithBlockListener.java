package github.minersStudios.listeners.player;

import com.google.common.collect.Sets;
import github.minersStudios.utils.UseBucket;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashSet;

import static github.minersStudios.utils.PlayerUtils.*;

public class InteractWithBlockListener implements Listener {

    private static final HashSet<Material> REPLACE = Sets.newHashSet(
            Material.AIR,
            Material.CAVE_AIR,
            Material.VOID_AIR,
            Material.GRASS,
            Material.SEAGRASS,
            Material.WATER,
            Material.LAVA
    );

    @EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        assert event.getClickedBlock() != null;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock().getType() != Material.NOTE_BLOCK) return;
        event.setCancelled(true);

        Block clickedBlock = event.getClickedBlock(),
                blockAtFace = clickedBlock.getRelative(event.getBlockFace());
        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand().getType().isAir() || player.getInventory().getItemInMainHand().getType() == Material.PAPER) return;
        for (Entity nearbyEntity : clickedBlock.getWorld().getNearbyEntities(blockAtFace.getLocation().add(0.5d, 0.5d, 0.5d), 0.5d, 0.5d, 0.5d))
            if(!(nearbyEntity instanceof Item)) return;

        PlayerInventory playerInventory = player.getInventory();
        ItemStack itemInMainHand = playerInventory.getItemInMainHand();

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemInMainHand);
        EnumHand hand = parseEnumHand(getEquipmentSlot(playerInventory, itemInMainHand));
        Location playerEyeLocation = player.getEyeLocation();
        EntityPlayer entityPlayer = parseHuman(player);
        MovingObjectPositionBlock movingObjectPositionBlock = getMovingObjectPositionBlock(player, blockAtFace.getLocation(), false);

        Location interactionPoint = getInteractionPoint(playerEyeLocation, 8, true);
        assert interactionPoint != null;

        if (
                REPLACE.contains(clickedBlock.getType())
                || (clickedBlock.getType() == Material.SNOW
                && ((Snow) clickedBlock.getBlockData()).getLayers() == 1)
        ) {
            blockAtFace = clickedBlock;
        } else if (!REPLACE.contains(blockAtFace.getType())) return;

        if (itemInMainHand.getType().toString().contains("BUCKET")) {
            new UseBucket(player, blockAtFace);
            return;
        }

        if (Tag.STAIRS.isTagged(itemInMainHand.getType())) {
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);
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

        } else if (
                Tag.SLABS.isTagged(itemInMainHand.getType())
                || blockAtFace.getType() == itemInMainHand.getType()
        ) {
            Slab.Type dataType;
            if (blockAtFace.getType() == itemInMainHand.getType()) {
                dataType = Slab.Type.DOUBLE;
            } else {
                if (
                        (interactionPoint.getY() > 0d && interactionPoint.getY() < .5d)
                                || interactionPoint.getY() == 1d
                ) {
                    dataType = Slab.Type.BOTTOM;
                } else {
                    dataType = Slab.Type.TOP;
                }
                nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);
            }

            Slab data = (Slab) blockAtFace.getBlockData();
            data.setType(dataType);
            blockAtFace.setBlockData(data);
        } else if (
                Tag.SHULKER_BOXES.isTagged(itemInMainHand.getType())
                || blockAtFace.getType() == itemInMainHand.getType()
        ) {
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);

            Directional directional = (Directional) blockAtFace.getBlockData();
            directional.setFacing(event.getBlockFace());
            blockAtFace.setBlockData(directional);
        } else {
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);
        }
    }
}
