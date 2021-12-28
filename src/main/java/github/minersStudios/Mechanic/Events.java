package github.minersStudios.Mechanic;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Events implements Listener {
    private final List<Material> REPLACE = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR,
            Material.GRASS, Material.SEAGRASS, Material.WATER, Material.LAVA);


    @EventHandler(ignoreCancelled = true)
    private void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getSourceBlock(),
                topBlock = block.getRelative(BlockFace.UP),
                bottomBlock = block.getRelative(BlockFace.DOWN);

        if (topBlock.getType() == Material.NOTE_BLOCK) {
            update(block.getLocation());
            if (Tag.DOORS.isTagged(block.getType()) && block.getBlockData() instanceof Door) {
                Door doorData = (Door) block.getBlockData();
                if (!doorData.getHalf().equals(Bisected.Half.TOP)) return;
                Door doorDataBottom = (Door) bottomBlock.getBlockData();
                doorDataBottom.setOpen(doorData.isOpen());
                bottomBlock.setBlockData(doorDataBottom);
                bottomBlock.getState().update(true, false);
            }
            event.setCancelled(true);
        }
        if (block.getType() == Material.NOTE_BLOCK)
            event.setCancelled(true);
        if (!Tag.SIGNS.isTagged(block.getType()) && !block.getType().equals(Material.LECTERN))
            block.getState().update(true, false);
    }

    private void update(Location loc) {
        Block block = loc.getBlock().getRelative(BlockFace.UP);
        if (block.getType() == Material.NOTE_BLOCK)
            block.getState().update(true, true);

        Block nextBlock = block.getRelative(BlockFace.UP);
        if (nextBlock.getType() == Material.NOTE_BLOCK)
            update(block.getLocation());
    }

    @EventHandler
    public void onPistonExtends(BlockPistonExtendEvent event) {
        if (event.getBlocks().stream().anyMatch(b -> b.getType().equals(Material.NOTE_BLOCK)))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        if (event.getBlocks().stream().anyMatch(b -> b.getType().equals(Material.NOTE_BLOCK)))
            event.setCancelled(true);
    }

    @EventHandler
    public void onNotePlay(NotePlayEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        ArrayList<Block> explodedBlockList = new ArrayList<>(event.blockList());

        explodedBlockList.stream()
                .filter(explodedBlock -> explodedBlock.getType() == Material.NOTE_BLOCK)
                .forEach(explodedBlock -> {
                    event.blockList().remove(explodedBlock);
                    explodedBlock.setType(Material.AIR);
                });
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        assert event.getClickedBlock() != null;
        Player player = event.getPlayer();

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || !event.getClickedBlock().getType().equals(Material.NOTE_BLOCK))
            return;

        event.setCancelled(true);


        if(player.getInventory().getItemInMainHand().getType().isAir()) return;

        Location locb = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().add(0.5D, 0.5D, 0.5D);
        for (Entity ignored : locb.getChunk().getWorld().getNearbyEntities(locb, 0.5, 0.5, 0.5)) return;

        PlayerInventory playerInventory = player.getInventory();
        ItemStack itemInMainHand = playerInventory.getItemInMainHand();
        Block block = event.getClickedBlock().getRelative(event.getBlockFace());

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemInMainHand);
        EnumHand hand = Utils.parseEnumHand(Utils.getEquipmentSlot(playerInventory, itemInMainHand));
        Location playerEyeLocation = player.getEyeLocation();
        EntityPlayer entityPlayer = Utils.parseHuman(player);
        MovingObjectPositionBlock movingObjectPositionBlock = Utils.getMovingObjectPositionBlock(player, block.getLocation(), false);

        Location interactionPoint = Utils.getInteractionPoint(playerEyeLocation, 8, true);
        assert interactionPoint != null;

        if (
                REPLACE.contains(event.getClickedBlock().getType())
                || (event.getClickedBlock().getType().equals(Material.SNOW)
                && ((Snow) event.getClickedBlock().getBlockData()).getLayers() == 1)
        ){
            block = event.getClickedBlock();
        } else if (!REPLACE.contains(block.getType())) return;

        if(itemInMainHand.getType().toString().contains("BUCKET")) {
            new Buckets(player, block);
        }


        if (Tag.STAIRS.isTagged(itemInMainHand.getType())) {
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);
            Stairs data = (Stairs)block.getBlockData();

            switch (event.getBlockFace()){
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
            block.setBlockData(data);

        } else if (
                Tag.SLABS.isTagged(itemInMainHand.getType())
                || block.getType().equals(itemInMainHand.getType())
        ) {
            Slab.Type dataType;
            if (block.getType() == itemInMainHand.getType()) {
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

            Slab data = (Slab) block.getBlockData();
            data.setType(dataType);
            block.setBlockData(data);
        } else if (
                Tag.SHULKER_BOXES.isTagged(itemInMainHand.getType())
                        || block.getType().equals(itemInMainHand.getType())
        ) {
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);

            Directional directional = (Directional) block.getBlockData();
            directional.setFacing(event.getBlockFace());
            block.setBlockData(directional);
        } else {
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);
        }
    }
}
