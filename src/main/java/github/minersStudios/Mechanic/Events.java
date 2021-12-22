package github.minersStudios.Mechanic;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;

public class Events implements Listener {
    private final List<Material> REPLACE = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR,
            Material.GRASS, Material.SEAGRASS, Material.WATER, Material.LAVA);

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
    public void onPlayerInteract(PlayerInteractEvent event) {
        assert event.getClickedBlock() != null;
        Player player = event.getPlayer();

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || !event.getClickedBlock().getType().equals(Material.NOTE_BLOCK))
            return;

        if(player.isSneaking()){
            event.setCancelled(false);
            return;
        } else {
            event.setCancelled(true);
        }

        if(player.getInventory().getItemInMainHand().getType().isAir()) return;

        Location locb = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation().add(0.5D, 0.5D, 0.5D);
        for (Entity ignored : locb.getChunk().getWorld().getNearbyEntities(locb, 0.5, 0.5, 0.5)) return;

        PlayerInventory inv = player.getInventory();
        ItemStack item = inv.getItemInMainHand();
        Block block = event.getClickedBlock().getRelative(event.getBlockFace());

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        EnumHand hand = Utils.parseEnumHand(Utils.getEquipmentSlot(inv, item));
        Location eyeLoc = player.getEyeLocation();
        EntityPlayer entityPlayer = Utils.parseHuman(player);
        MovingObjectPositionBlock movingObjectPositionBlock = Utils.getMovingObjectPositionBlock(player, block.getLocation(), false);
        Location point = Utils.getInteractionPoint(eyeLoc, 8, true);
        assert point != null;

        if (
                REPLACE.contains(event.getClickedBlock().getType())
                || (event.getClickedBlock().getType().equals(Material.SNOW)
                && ((Snow) event.getClickedBlock().getBlockData()).getLayers() == 1)
        ){
            block = event.getClickedBlock();
        } else if (!REPLACE.contains(block.getType())) return;

        if(inv.getItemInMainHand().getType().toString().contains("BUCKET")){
            switch (inv.getItemInMainHand().getType()){
                case LAVA_BUCKET:
                    block.setType(Material.LAVA);
                    inv.getItemInMainHand().setType(Material.BUCKET);
                case WATER_BUCKET:
                    block.setType(Material.WATER);
                    inv.getItemInMainHand().setType(Material.BUCKET);
                case PUFFERFISH_BUCKET:
                    block.setType(Material.WATER);
                    block.getLocation().getBlock().getWorld().spawnEntity(block.getLocation().add(.5d, .5d, .5d), EntityType.PUFFERFISH);
                    inv.getItemInMainHand().setType(Material.BUCKET);
                case SALMON_BUCKET:
                    block.setType(Material.WATER);
                    block.getLocation().getBlock().getWorld().spawn(block.getLocation().add(.5d, .5d, .5d), Salmon.class, salmon -> {
                        // To do
                            });
                    inv.getItemInMainHand().setType(Material.BUCKET);
            }
        }


        if (Tag.STAIRS.isTagged(item.getType())) {
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);
            Stairs data = ((Stairs) block.getBlockData());

            switch (event.getBlockFace()){
                case UP:
                    data.setHalf(Bisected.Half.BOTTOM);
                    break;
                case DOWN:
                    data.setHalf(Bisected.Half.TOP);
                    break;
                default:
                    data.setHalf(point.getY() < .5d && point.getY() >= 0d
                            ? Bisected.Half.BOTTOM
                            : Bisected.Half.TOP);
                    break;
            }
            block.setBlockData(data);

        } else if (
                Tag.SLABS.isTagged(item.getType())
                || block.getType().equals(item.getType())
        ) {
            Slab.Type dataType;
            if (block.getType() == item.getType()) {
                dataType = Slab.Type.DOUBLE;
            } else {
                if (
                        (point.getY() > 0d && point.getY() < .5d)
                        || point.getY() == 1d
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
        } else {
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);
        }
    }
}
