package github.minersStudios.msBlock.listeners.block;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.enumerators.CustomBlockMaterial;
import github.minersStudios.msBlock.enumerators.ToolType;
import github.minersStudios.msBlock.objects.CustomBlock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.Main.coreProtectAPI;
import static github.minersStudios.msBlock.Main.protocolManager;
import static github.minersStudios.msBlock.utils.BlockUtils.blocks;

public class PacketBreakListener extends PacketAdapter {

    public PacketBreakListener() {
        super(Main.plugin, PacketType.Play.Client.BLOCK_DIG);
    }

    @Override
    public void onPacketReceiving(@Nonnull final PacketEvent event) {
        Player player = event.getPlayer();
        if (player == null || !player.isOnline() || player.getGameMode() != GameMode.SURVIVAL) return;
        BlockPosition blockPosition = event.getPacket().getBlockPositionModifier().read(0);
        Block block = blockPosition.toLocation(player.getWorld()).getBlock();
        EnumWrappers.PlayerDigType digType = event.getPacket().getPlayerDigTypes().read(0);

        if (digType.equals(EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) && block.getType() == Material.NOTE_BLOCK && !blocks.containsKey(block)) {
            CustomBlock customBlock = new CustomBlock().getCustomBlock(block, player);
            if (customBlock == null) return;
            CustomBlockMaterial customBlockMaterial = customBlock.getCustomBlockMaterial();
            if (customBlockMaterial == null) return;
            Location blockLocation = block.getLocation();
            ItemStack handItem = player.getInventory().getItem(EquipmentSlot.HAND);
            ItemMeta handItemMeta = handItem.getItemMeta();
            float digSpeed = CustomBlockMaterial.getDigSpeed(player, customBlockMaterial);

            blocks.put(block, Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
                float ticks, progress = 0.0f;
                int current_stage = 0;

                @Override
                public void run() {
                    this.ticks++;
                    this.progress += digSpeed;
                    float next_stage = (this.current_stage + 1) * 0.1f;

                    if (this.ticks % 4.0f == 0.0f) player.getWorld().playSound(blockLocation, customBlockMaterial.getSoundHit(), SoundCategory.BLOCKS, 0.25f, 0.5f);

                    if (this.progress > next_stage) {
                        this.current_stage = (int) Math.floor(this.progress * 10.0f);
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
                        if(blocks.get(block) == null) return;
                        Bukkit.getScheduler().cancelTask(blocks.get(block));
                        blocks.keySet().remove(block);

                        World world = block.getWorld();
                        world.playSound(blockLocation, customBlockMaterial.getSoundBreak(), SoundCategory.BLOCKS, 1.0f, 0.8f);
                        world.spawnParticle(Particle.BLOCK_CRACK, blockLocation.clone().add(0.5, 0.25, 0.5), 80, 0.35, 0.35, 0.35, block.getBlockData());
                        if (customBlockMaterial.getExpToDrop() != 0) world.spawn(blockLocation, ExperienceOrb.class).setExperience(customBlockMaterial.getExpToDrop());
                        coreProtectAPI.logRemoval(player.getName(), block.getLocation(), Material.NOTE_BLOCK, block.getBlockData());
                        block.setType(Material.AIR);

                        if (!customBlockMaterial.isForceTool()) {
                            world.dropItemNaturally(blockLocation, customBlockMaterial.getItemStack());
                        } else if (customBlockMaterial.getToolType() == ToolType.getToolType(handItem)) {
                            world.dropItemNaturally(blockLocation, customBlockMaterial.getItemStack());
                        }

                        if (ToolType.getToolType(handItem) != ToolType.HAND && handItemMeta instanceof Damageable) {
                            Damageable handItemItemDamageable = (Damageable) handItemMeta;
                            handItemItemDamageable.setDamage(handItemItemDamageable.getDamage() + 1);
                            handItem.setItemMeta(handItemItemDamageable);
                            if (handItemItemDamageable.getDamage() > handItem.getType().getMaxDurability()) {
                                handItem.setAmount(handItem.getAmount() - 1);
                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                            }
                        }
                    }
                }
            }, 0L, 1L));
        } else if (
                (digType.equals(EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK) || digType.equals(EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK))
                && blocks.containsKey(block)
        ) {
            PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
            packetContainer.getIntegers().write(0, 0).write(1, -1);
            packetContainer.getBlockPositionModifier().write(0, blockPosition);
            protocolManager.broadcastServerPacket(packetContainer);
            if(blocks.get(block) == null) return;
            Bukkit.getScheduler().cancelTask(blocks.get(block));
            blocks.keySet().remove(block);
        }
    }
}
