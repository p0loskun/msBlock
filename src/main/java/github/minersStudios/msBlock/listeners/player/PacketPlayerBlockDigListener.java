package github.minersStudios.msBlock.listeners.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.Main.protocolManager;
import static github.minersStudios.msBlock.utils.BlockUtils.blocks;
import static github.minersStudios.msBlock.utils.BlockUtils.getObjectByBlock;

public class PacketPlayerBlockDigListener extends PacketAdapter {

    public PacketPlayerBlockDigListener() {
        super(Main.plugin, PacketType.Play.Client.BLOCK_DIG);
    }

    @Override
    public void onPacketReceiving(@Nonnull PacketEvent event) {
        Player player = event.getPlayer();
        if (player == null || !player.isOnline() || player.getGameMode() != GameMode.SURVIVAL) return;
        PacketContainer packet = event.getPacket();
        EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().read(0);
        BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);
        Block block = blockPosition.toLocation(player.getWorld()).getBlock();

        if (digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
            if (block.getType() != Material.NOTE_BLOCK) {
                Bukkit.getScheduler().runTask(this.plugin, () -> {
                    if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING))
                        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                });
            } else if (block.getBlockData() instanceof NoteBlock noteBlock && BlockUtils.notHasPlayer(player)){
                CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
                assert customBlockMaterial != null;
                float digSpeed = CustomBlockMaterial.getDigSpeed(player, customBlockMaterial);
                blocks.put(new Object[]{block, player}, Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
                    float ticks, progress = 0.0f;
                    int currentStage = 0;

                    @Override
                    public void run() {
                        if (block.getLocation().getBlock().getType() != Material.NOTE_BLOCK && blocks.get(getObjectByBlock(block)) != null)
                            BlockUtils.cancelAllTasksWithThisBlock(block);
                        this.ticks++;
                        this.progress += digSpeed;
                        float nextStage = this.currentStage++ * 0.1f;

                        if (this.ticks % 4.0f == 0.0f)
                            customBlockMaterial.playHitSound(block);

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
                            }, 1L);
                            customBlockMaterial.breakCustomBlock(block, player);
                        }
                    }
                }, 0L, 1L));
            }
            if (BlockUtils.isWoodenSound(block.getType()) && BlockUtils.notHasPlayer(player)) {
                blocks.put(new Object[]{block, player}, Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
                    if (block.getLocation().getBlock().getType().isAir() && getObjectByBlock(block) != null)
                        BlockUtils.cancelAllTasksWithThisBlock(block);
                    block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.hit", 0.5f, 0.5f);
                }, 0L, 4L));
            }
        } else if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK && getObjectByBlock(block) != null) {
            PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
            packetContainer.getIntegers().write(0, 0).write(1, -1);
            packetContainer.getBlockPositionModifier().write(0, blockPosition);
            protocolManager.broadcastServerPacket(packetContainer);
            BlockUtils.cancelAllTasksWithThisBlock(block);
        } else if (digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK) {
            PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
            packetContainer.getIntegers().write(0, 0).write(1, -1);
            packetContainer.getBlockPositionModifier().write(0, blockPosition);
            protocolManager.broadcastServerPacket(packetContainer);
            Bukkit.getScheduler().runTask(Main.plugin, () -> player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, true, false, false)));
            BlockUtils.cancelAllTasksWithThisPlayer(player);
        }
    }
}
