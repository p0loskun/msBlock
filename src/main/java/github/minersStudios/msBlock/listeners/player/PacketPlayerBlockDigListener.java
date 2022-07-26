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
import github.minersStudios.msBlock.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.AbstractMap;

import static github.minersStudios.msBlock.Main.protocolManager;
import static github.minersStudios.msBlock.utils.BlockUtils.blocks;
import static github.minersStudios.msBlock.utils.BlockUtils.getEntryByBlock;

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
                if (BlockUtils.hasPlayer(player) && !BlockUtils.isWoodenSound(block.getType()))
                    BlockUtils.cancelAllTasksWithThisPlayer(player);
                Bukkit.getScheduler().runTask(this.plugin, () -> {
                    if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING))
                        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                });
            } else if (block.getBlockData() instanceof NoteBlock noteBlock) {
                if (BlockUtils.hasPlayer(player)) {
                    BlockUtils.cancelAllTasksWithThisPlayer(player);
                }
                CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
                float digSpeed = CustomBlockMaterial.getDigSpeed(player, customBlockMaterial);
                blocks.put(new AbstractMap.SimpleEntry<>(block, player), Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
                    float ticks, progress = 0.0f;
                    int currentStage = 0;
                    static boolean swing = true;

                    @Override
                    public void run() {
                        if (block.getLocation().getBlock().getType() != Material.NOTE_BLOCK && blocks.get(getEntryByBlock(block)) != null)
                            BlockUtils.cancelAllTasksWithThisBlock(block);
                        Block targetBlock = PlayerUtils.getTargetBlock(player);
                        if (targetBlock == null || PlayerUtils.getTargetEntity(player) != null) {
                            PlayerUtils.farAway.add(player);
                            return;
                        } else {
                            PlayerUtils.farAway.remove(player);
                        }
                        if (!targetBlock.equals(block)) return;

                        Bukkit.getScheduler().runTask(plugin, () -> protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.ARM_ANIMATION) {
                            @Override
                            public void onPacketReceiving(PacketEvent event) {
                                swing = true;
                            }
                        }));

                        if (!swing) {
                            playZeroBreakStage(player, blockPosition);
                            BlockUtils.cancelAllTasksWithThisPlayer(player);
                        }

                        this.ticks++;
                        this.progress += digSpeed;
                        float nextStage = this.currentStage++ * 0.1f;

                        if (this.ticks % 4.0f == 0.0f) {
                            if (!PlayerUtils.farAway.contains(player))
                                customBlockMaterial.playHitSound(block);
                            swing = false;
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
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> playZeroBreakStage(player, blockPosition), 1L);
                            customBlockMaterial.breakCustomBlock(block, player);
                        }
                    }
                }, 0L, 1L));
            }
            if (BlockUtils.isWoodenSound(block.getType()) && !BlockUtils.hasPlayer(player)) {
                blocks.put(new AbstractMap.SimpleEntry<>(block, player), Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
                    static boolean swing = true;
                    float ticks = 0.0f;

                    @Override
                    public void run() {
                        if (block.getLocation().getBlock().getType().isAir() && getEntryByBlock(block) != null)
                            BlockUtils.cancelAllTasksWithThisBlock(block);
                        Block targetBlock = PlayerUtils.getTargetBlock(player);
                        if (targetBlock == null || PlayerUtils.getTargetEntity(player) != null) {
                            PlayerUtils.farAway.add(player);
                            return;
                        } else {
                            PlayerUtils.farAway.remove(player);
                        }
                        if (!targetBlock.equals(block)) return;

                        Bukkit.getScheduler().runTask(plugin, () -> protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.ARM_ANIMATION) {
                            @Override
                            public void onPacketReceiving(PacketEvent event) {
                                swing = true;
                            }
                        }));

                        if (!swing) {
                            playZeroBreakStage(player, blockPosition);
                            BlockUtils.cancelAllTasksWithThisPlayer(player);
                        }
                        this.ticks++;

                        if (this.ticks % 4.0f == 0.0f) {
                            if (!PlayerUtils.farAway.contains(player))
                                block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.hit", 0.5f, 0.5f);
                            swing = false;
                        }
                    }
                }, 0L, 1L));
            }
        } else if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK && getEntryByBlock(block) != null) {
            playZeroBreakStage(player, blockPosition);
            BlockUtils.cancelAllTasksWithThisBlock(block);
        } else if (digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK && getEntryByBlock(block) != null && !PlayerUtils.farAway.contains(player)) {
            playZeroBreakStage(player, blockPosition);
            BlockUtils.cancelAllTasksWithThisPlayer(player);
        }
    }

    private static void playZeroBreakStage(@Nonnull Player player, @Nonnull BlockPosition blockPosition) {
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
        packetContainer.getIntegers().write(0, 0).write(1, -1);
        packetContainer.getBlockPositionModifier().write(0, blockPosition);
        protocolManager.broadcastServerPacket(packetContainer);
        PlayerUtils.farAway.remove(player);
    }
}
