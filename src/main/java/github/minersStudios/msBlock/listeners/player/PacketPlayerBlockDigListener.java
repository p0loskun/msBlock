package github.minersStudios.msBlock.listeners.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.enums.ToolType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.Main.protocolManager;
import static github.minersStudios.msBlock.utils.BlockUtils.blocks;

public class PacketPlayerBlockDigListener extends PacketAdapter {

    public PacketPlayerBlockDigListener() {
        super(Main.plugin, PacketType.Play.Client.BLOCK_DIG);
    }

    @Override
    public void onPacketReceiving(@Nonnull PacketEvent event) {
        Player player = event.getPlayer();
        PacketContainer packet = event.getPacket();
        if (player == null || !player.isOnline() || player.getGameMode() != GameMode.SURVIVAL) return;
        World world = player.getWorld();
        EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().read(0);
        BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);
        Block block = blockPosition.toLocation(world).getBlock();

        if (
                digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK
                && block.getType() == Material.NOTE_BLOCK
                && (!blocks.containsKey(block) && !blocks.containsValue(blocks.get(block)))
        ) {
            NoteBlock noteBlock = (NoteBlock) block.getBlockData();
            CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
            if (customBlockMaterial == null) return;
            Location blockLocation = block.getLocation();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            float digSpeed = CustomBlockMaterial.getDigSpeed(player, customBlockMaterial, itemInMainHand);

            blocks.put(block, Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
                float ticks, progress = 0.0f;
                int currentStage = 0;

                @Override
                public void run() {
                    this.ticks++;
                    this.progress += digSpeed;
                    float nextStage = (this.currentStage + 1) * 0.1f;

                    if (this.ticks % 4.0f == 0.0f){
                        world.playSound(blockLocation, customBlockMaterial.getSoundHit(), SoundCategory.BLOCKS, 0.5f, 0.5f);
                    }

                    if (this.progress > nextStage) {
                        this.currentStage = (int) Math.floor(this.progress * 10.0f);
                        if (this.currentStage <= 9) {
                            PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                            packetContainer.getIntegers().write(0, 0).write(1, this.currentStage - 1);
                            packetContainer.getBlockPositionModifier().write(0, blockPosition);
                            protocolManager.broadcastServerPacket(packetContainer);
                        }
                    }

                    if (this.progress > 1.0f) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
                            PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                            packetContainer.getIntegers().write(0, 0).write(1, -1);
                            packetContainer.getBlockPositionModifier().write(0, blockPosition);
                            protocolManager.broadcastServerPacket(packetContainer);
                        }, 1);

                        if(blocks.get(block) == null) return;
                        Bukkit.getScheduler().cancelTask(blocks.remove(block));

                        world.playSound(blockLocation, customBlockMaterial.getSoundBreak(), SoundCategory.BLOCKS, 1.0f, 0.8f);
                        world.spawnParticle(Particle.BLOCK_CRACK, blockLocation.clone().add(0.5d, 0.25d, 0.5d), 80, 0.35d, 0.35d, 0.35d, block.getBlockData());
                        Main.coreProtectAPI.logRemoval(player.getName(), blockLocation, Material.NOTE_BLOCK, block.getBlockData());
                        block.setType(Material.AIR);

                        if (
                                (!customBlockMaterial.isForceTool()
                                        || customBlockMaterial.getToolType() == ToolType.getToolType(itemInMainHand))
                                && customBlockMaterial != CustomBlockMaterial.DEFAULT
                        ) {
                            world.dropItemNaturally(blockLocation, customBlockMaterial.getItemStack());
                            if (customBlockMaterial.getExpToDrop() != 0) {
                                world.spawn(blockLocation, ExperienceOrb.class).setExperience(customBlockMaterial.getExpToDrop());
                            }
                        } else if(customBlockMaterial == CustomBlockMaterial.DEFAULT){
                            world.dropItemNaturally(blockLocation, new ItemStack(Material.NOTE_BLOCK));
                        }

                        if (
                                ToolType.getToolType(itemInMainHand) != ToolType.HAND
                                && itemInMainHand.getItemMeta() instanceof Damageable handItemDamageable
                        ) {
                            handItemDamageable.setDamage(handItemDamageable.getDamage() + 1);
                            itemInMainHand.setItemMeta(handItemDamageable);
                            if (handItemDamageable.getDamage() > itemInMainHand.getType().getMaxDurability()) {
                                itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
                                world.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                            }
                        }
                    }
                }
            }, 0L, 1L));
        }
        if (
                (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK
                        || digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK)
                && blocks.containsKey(block)
        ) {
            PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
            packetContainer.getIntegers().write(0, 0).write(1, -1);
            packetContainer.getBlockPositionModifier().write(0, blockPosition);
            protocolManager.broadcastServerPacket(packetContainer);
            if(blocks.get(block) != null) Bukkit.getScheduler().cancelTask(blocks.remove(block));
        }
    }
}
