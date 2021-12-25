package github.minersStudios.Mechanic;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TropicalFish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Events implements Listener {
    private final List<Material> REPLACE = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR,
            Material.GRASS, Material.SEAGRASS, Material.WATER, Material.LAVA);


    @EventHandler(priority = EventPriority.LOWEST,ignoreCancelled = true)
    private void onBlockPhysics(BlockPhysicsEvent e) {
        Block block = e.getBlock().getRelative(BlockFace.UP);
        if (block.getType().equals(Material.NOTE_BLOCK)){
            block.getState().update(true, false);
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onNoteBlockPhysics(BlockPhysicsEvent e) {
        Block block = e.getBlock();
        if (block.getType().equals(Material.NOTE_BLOCK)){
            block.getState().update(true, false);
            e.setCancelled(true);
        }
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
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.NOTE_BLOCK || event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        if (event.isCancelled()) return;
        event.setDropItems(false);
        event.setExpToDrop(0);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        ArrayList<Block> blockList = new ArrayList<>(event.blockList());

        blockList.stream()
                .filter(block -> block.getType() == Material.NOTE_BLOCK)
                .forEach(block -> {
                    event.blockList().remove(block);
                    block.setType(Material.AIR);
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

        if(inv.getItemInMainHand().getType().toString().contains("BUCKET")) {
            switch (inv.getItemInMainHand().getType()) {
                case LAVA_BUCKET:
                    block.setType(Material.LAVA);

                    if (player.getGameMode().equals(GameMode.SURVIVAL))
                        inv.getItemInMainHand().setType(Material.BUCKET);

                    break;
                case WATER_BUCKET:
                    block.setType(Material.WATER);

                    if (player.getGameMode().equals(GameMode.SURVIVAL))
                        inv.getItemInMainHand().setType(Material.BUCKET);

                    break;
                case PUFFERFISH_BUCKET:
                    block.setType(Material.WATER);
                    block.getLocation().getBlock().getWorld().spawnEntity(block.getLocation().add(.5d, .5d, .5d), EntityType.PUFFERFISH);

                    if (player.getGameMode().equals(GameMode.SURVIVAL))
                        inv.getItemInMainHand().setType(Material.BUCKET);

                    break;
                case SALMON_BUCKET:
                    block.setType(Material.WATER);
                    block.getLocation().getBlock().getWorld().spawnEntity(block.getLocation().add(.5d, .5d, .5d), EntityType.SALMON);

                    if (player.getGameMode().equals(GameMode.SURVIVAL))
                        inv.getItemInMainHand().setType(Material.BUCKET);
                case TROPICAL_FISH_BUCKET:
                    block.setType(Material.WATER);
                    block.getLocation().getBlock().getWorld().spawn(block.getLocation().add(.5d, .5d, .5d), TropicalFish.class, tropicalFish -> {
                        TropicalFishBucketMeta tropicalFishBucketMeta = (TropicalFishBucketMeta) inv.getItemInMainHand().getItemMeta();
                        assert tropicalFishBucketMeta != null;

                        tropicalFish.setBodyColor(tropicalFishBucketMeta.getBodyColor());
                        tropicalFish.setPattern(tropicalFishBucketMeta.getPattern());
                        tropicalFish.setPatternColor(tropicalFishBucketMeta.getPatternColor());
                    });

                    if (player.getGameMode().equals(GameMode.SURVIVAL))
                        inv.getItemInMainHand().setType(Material.BUCKET);

                    break;
                case BUCKET:
                    BlockData blockData = block.getBlockData();

                    if (blockData instanceof Levelled) {
                        Levelled levelled = (Levelled) blockData;

                        if (levelled.getLevel() == 0) {
                            if (player.getGameMode().equals(GameMode.SURVIVAL)) inv.getItemInMainHand().setType(
                                    (block.getType().equals(Material.LAVA)) ? Material.LAVA_BUCKET :
                                            (block.getType().equals(Material.WATER)) ? Material.WATER_BUCKET :
                                                    Material.BUCKET
                            );
                            block.setType(Material.AIR);
                        }
                    }
            }
        }


        if (Tag.STAIRS.isTagged(item.getType())) {
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
                    data.setHalf(point.getY() < .5d && point.getY() >= 0d ? Bisected.Half.BOTTOM : Bisected.Half.TOP);
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
        } else if (
                Tag.SHULKER_BOXES.isTagged(item.getType())
                        || block.getType().equals(item.getType())
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
