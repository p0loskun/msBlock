package github.minersStudios.listeners.block;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import github.minersStudios.Main;
import github.minersStudios.enumerators.CustomBlockMaterial;
import github.minersStudios.enumerators.ToolType;
import github.minersStudios.objects.CustomBlock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import static github.minersStudios.Main.coreProtectAPI;
import static github.minersStudios.Main.protocolManager;
import static github.minersStudios.utils.PlayerUtils.diggers;

public class PacketBreakListener extends PacketAdapter {

    public PacketBreakListener() {
        super(Main.plugin, PacketType.Play.Client.BLOCK_DIG);
    }

    @Override
    public void onPacketReceiving(final PacketEvent event) {
        Player player = event.getPlayer();
        if (player == null || !player.isOnline() || player.getGameMode() != GameMode.SURVIVAL) return;
        BlockPosition blockPosition = event.getPacket().getBlockPositionModifier().read(0);
        EnumWrappers.PlayerDigType digType = event.getPacket().getPlayerDigTypes().read(0);

        Block block = blockPosition.toLocation(player.getWorld()).getBlock();
        if (block.getType() != Material.NOTE_BLOCK) return;
        if (digType.equals(EnumWrappers.PlayerDigType.START_DESTROY_BLOCK)) {
            Location blockLocation = block.getLocation();
            CustomBlock customBlock = new CustomBlock().getCustomBlock(block, player);
            if (customBlock.getCustomBlockMaterial() == null) return;
            ItemStack handItem = player.getInventory().getItem(EquipmentSlot.HAND);

            float digSpeed = CustomBlockMaterial.getDigSpeed(player, customBlock);

            diggers.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
                float ticks = 0;
                float progress = 0;
                int current_stage = 0;

                @Override
                public void run() {
                    if (diggers.get(player) == null) return;
                    this.ticks++;
                    this.progress += digSpeed;
                    float next_stage = (this.current_stage + 1) * 0.1F;
                    World world = block.getWorld();

                    if (this.ticks % 4 == 0) {
                        world.playSound(blockLocation, customBlock.getCustomBlockMaterial().getSoundHit(), SoundCategory.BLOCKS, 0.25f, 0.5f);
                    }

                    if (this.progress > next_stage) {
                        this.current_stage = (int) Math.floor(this.progress * 10F);

                        if (this.current_stage <= 9) {
                            PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                            packetContainer.getIntegers().write(0, 0).write(1, this.current_stage - 1);
                            packetContainer.getBlockPositionModifier().write(0, blockPosition);
                            protocolManager.broadcastServerPacket(packetContainer);
                        }
                    }

                    if (progress > 1F) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                            packetContainer.getIntegers().write(0, 0).write(1, -1);
                            packetContainer.getBlockPositionModifier().write(0, blockPosition);
                            protocolManager.broadcastServerPacket(packetContainer);
                        }, 1);
                        Bukkit.getScheduler().cancelTask(diggers.remove(player));

                        world.playSound(blockLocation, customBlock.getCustomBlockMaterial().getSoundBreak(), SoundCategory.BLOCKS, 1.0f, 0.8f);
                        world.spawnParticle(Particle.BLOCK_CRACK, blockLocation.add(0.5, 0.5, 0.5), 100, 0.25, 0.25, 0.25, block.getBlockData());
                        blockLocation.add(-0.5, -0.5, -0.5);
                        if (customBlock.getCustomBlockMaterial().getExpToDrop() != 0)
                            world.spawn(blockLocation, ExperienceOrb.class).setExperience(customBlock.getCustomBlockMaterial().getExpToDrop());
                        coreProtectAPI.logRemoval(player.getName(), block.getLocation(), Material.NOTE_BLOCK, block.getBlockData());
                        block.setType(Material.AIR);

                        if (!customBlock.getCustomBlockMaterial().isForceTool()) {
                            world.dropItemNaturally(blockLocation, customBlock.getCustomBlockMaterial().getItemStack());
                        } else if (customBlock.getCustomBlockMaterial().getToolType() == ToolType.getTool(handItem)) {
                            world.dropItemNaturally(blockLocation, customBlock.getCustomBlockMaterial().getItemStack());
                        }

                        if (ToolType.getTool(handItem) == ToolType.HAND) return;
                        if (!(handItem.getItemMeta() instanceof Damageable)) return;
                        Damageable itemMeta = (Damageable) handItem.getItemMeta();
                        assert itemMeta != null;
                        itemMeta.setDamage(itemMeta.getDamage() + 1);
                        handItem.setItemMeta(itemMeta);
                        if(itemMeta.getDamage() < player.getInventory().getItemInMainHand().getType().getMaxDurability())return;
                        player.getInventory().clear(player.getInventory().getHeldItemSlot());
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    }
                }
            }, 0L, 1L));

        } else if (
                (digType.equals(EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK)
                        || digType.equals(EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK))
                        && diggers.containsKey(player)
        ) {
            PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
            packetContainer.getIntegers().write(0, 0).write(1, -1);
            packetContainer.getBlockPositionModifier().write(0, blockPosition);
            protocolManager.broadcastServerPacket(packetContainer);
            Bukkit.getScheduler().cancelTask(diggers.get(player));
        }
    }
}
