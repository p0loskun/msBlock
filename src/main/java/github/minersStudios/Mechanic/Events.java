package github.minersStudios.Mechanic;

import com.google.common.collect.Sets;
import github.minersStudios.Main;
import github.minersStudios.Mechanic.CustomBreak.BreakUtils;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;


public class Events implements Listener {
    private final HashSet<Material> REPLACE = Sets.newHashSet(
            Material.AIR,
            Material.CAVE_AIR,
            Material.VOID_AIR,
            Material.GRASS,
            Material.SEAGRASS,
            Material.WATER,
            Material.LAVA
    );



    // CoreProtect
    private final Plugin getPluginForCP = Main.getInstance().getServer().getPluginManager().getPlugin("CoreProtect");
    private static CoreProtectAPI coreProtectAPI;
    {
        assert getPluginForCP != null;
        coreProtectAPI = ((CoreProtect) getPluginForCP).getAPI();
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    private void onBlockPhysics(BlockPhysicsEvent event) {
        Block block = event.getBlock(),
                topBlock = block.getRelative(BlockFace.UP);

        if (topBlock.getType() == Material.NOTE_BLOCK) {
            update(block.getLocation());
            event.setCancelled(true);
        }
        if (block.getType() == Material.NOTE_BLOCK) {
            update(block.getLocation());
            event.setCancelled(true);
        }
    }

    private void update(Location loc) {
        Block block = loc.getBlock().getRelative(BlockFace.UP);
        if (block.getType() == Material.NOTE_BLOCK)
            block.getState().update(true, false);

        Block nextBlock = block.getRelative(BlockFace.UP);
        if (nextBlock.getType() == Material.NOTE_BLOCK)
            update(block.getLocation());
    }

    @EventHandler
    public void onPistonExtends(BlockPistonExtendEvent event){
        for (Block blocks : event.getBlocks()) {
            if (blocks.getType().equals(Material.NOTE_BLOCK))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonEvent(BlockPistonRetractEvent event){
        for (Block blocks : event.getBlocks()) {
            if (blocks.getType().equals(Material.NOTE_BLOCK))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.getBlockPlaced().getType() == Material.NOTE_BLOCK){
            event.setCancelled(true);
            event.getPlayer().sendMessage("ꑜ" + ChatColor.DARK_RED + " Простите, но нотные блоки запрещены");
            update(event.getBlockPlaced().getLocation());
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        HashSet<Block> blockList = Sets.newHashSet(event.blockList());
        blockList.stream()
                .filter(block -> block.getType() == Material.NOTE_BLOCK)
                .forEach(block -> {
                    coreProtectAPI.logRemoval("#tnt", block.getLocation(), Material.NOTE_BLOCK, block.getBlockData());
                    event.blockList().remove(block);
                    block.setType(Material.AIR);
                });
    }

    @EventHandler
    public void onNotePlay(NotePlayEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        assert event.getClickedBlock() != null;

        Block clickedBlock = event.getClickedBlock();
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || clickedBlock.getType() != Material.NOTE_BLOCK) return;
        event.setCancelled(true);

        if (player.getInventory().getItemInMainHand().getType().isAir() || player.getInventory().getItemInMainHand().getType() == Material.PAPER) return;

        Location locb = clickedBlock.getRelative(event.getBlockFace()).getLocation().add(0.5D, 0.5D, 0.5D);
        for (Entity ignored : clickedBlock.getWorld().getNearbyEntities(locb, 0.5, 0.5, 0.5)) return;

        PlayerInventory playerInventory = player.getInventory();
        ItemStack itemInMainHand = playerInventory.getItemInMainHand();
        Block block = clickedBlock.getRelative(event.getBlockFace());

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemInMainHand);
        EnumHand hand = Utils.parseEnumHand(Utils.getEquipmentSlot(playerInventory, itemInMainHand));
        Location playerEyeLocation = player.getEyeLocation();
        EntityPlayer entityPlayer = Utils.parseHuman(player);
        MovingObjectPositionBlock movingObjectPositionBlock = Utils.getMovingObjectPositionBlock(player, block.getLocation(), false);

        Location interactionPoint = Utils.getInteractionPoint(playerEyeLocation, 8, true);
        assert interactionPoint != null;

        if (
                REPLACE.contains(clickedBlock.getType())
                        || (clickedBlock.getType() == Material.SNOW
                        && ((Snow) clickedBlock.getBlockData()).getLayers() == 1)
        ) {
            block = clickedBlock;
        } else if (!REPLACE.contains(block.getType())) return;

        if (itemInMainHand.getType().toString().contains("BUCKET")) {
            new useBucket(player, block);
            return;
        }

        if (Tag.STAIRS.isTagged(itemInMainHand.getType())) {
            player.sendMessage("b");
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);
            Stairs data = (Stairs) block.getBlockData();

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
            block.setBlockData(data);

        } else if (
                Tag.SLABS.isTagged(itemInMainHand.getType())
                        || block.getType() == itemInMainHand.getType()
        ) {
            player.sendMessage("c");
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
                        || block.getType() == itemInMainHand.getType()
        ) {
            player.sendMessage("d");
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);

            Directional directional = (Directional) block.getBlockData();
            directional.setFacing(event.getBlockFace());
            block.setBlockData(directional);
        } else {
            player.sendMessage("e");
            nmsItem.useOn(new ItemActionContext(entityPlayer, hand, movingObjectPositionBlock), hand);
        }
    }
}
