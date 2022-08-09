package com.github.minersstudios.msBlock.listeners.block;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.github.minersstudios.msBlock.Main;
import com.github.minersstudios.msBlock.enums.CustomBlockMaterial;
import com.github.minersstudios.msBlock.utils.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.AbstractMap;

import static com.comphenix.protocol.ProtocolLibrary.getProtocolManager;
import static com.github.minersstudios.msBlock.utils.PlayerUtils.*;

public class PacketBlockDigListener extends PacketAdapter {

	public PacketBlockDigListener() {
		super(Main.getInstance(), PacketType.Play.Client.BLOCK_DIG);
	}

	@Override
	public void onPacketReceiving(@Nonnull PacketEvent event) {
		Player player = event.getPlayer();
		if (player == null || !player.isOnline() || player.getGameMode() != GameMode.SURVIVAL) return;
		PacketContainer packet = event.getPacket();
		EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().read(0);
		BlockPosition blockPosition = packet.getBlockPositionModifier().read(0);
		Block block = blockPosition.toLocation(player.getWorld()).getBlock();
		boolean hasSlowDigging = player.hasPotionEffect(PotionEffectType.SLOW_DIGGING);

		Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
			if (digType == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
				if (block.getBlockData() instanceof NoteBlock noteBlock) {
					if (BlockUtils.hasPlayer(player)) {
						BlockUtils.cancelAllTasksWithThisPlayer(player);
					}
					CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
					float digSpeed = CustomBlockMaterial.getDigSpeed(player, customBlockMaterial);
					BlockUtils.blocks.put(new AbstractMap.SimpleEntry<>(block, player), Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
						float ticks, progress = 0.0f;
						int currentStage = 0;
						static boolean swing = true;

						@Override
						public void run() {
							Block targetBlock = getTargetBlock(player);
							if (getTargetEntity(player, targetBlock) != null || targetBlock == null) {
								farAway.add(player);
								return;
							} else {
								farAway.remove(player);
							}

							if (!hasSlowDigging) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, true, false, false));
							}

							if (!targetBlock.equals(block)) return;

							if (!farAway.contains(player)) {
								Main.getProtocolManager().addPacketListener(new PacketAdapter(Main.getInstance(), PacketType.Play.Client.ARM_ANIMATION) {
									@Override
									public void onPacketReceiving(PacketEvent event) {
										swing = true;
									}
								});
							}

							if (!swing) {
								playZeroBreakStage(blockPosition);
								BlockUtils.cancelAllTasksWithThisPlayer(player);
							}

							this.ticks++;
							this.progress += digSpeed;

							if (this.ticks % 4.0f == 0.0f && !farAway.contains(player)) {
								customBlockMaterial.playHitSound(block);
								swing = false;
							}

							if (this.progress > this.currentStage++ * 0.1f) {
								this.currentStage = (int) Math.floor(this.progress * 10.0f);
								if (this.currentStage <= 9) {
									PacketContainer packetContainer = getProtocolManager().createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
									packetContainer.getIntegers().write(0, 0).write(1, this.currentStage - 1);
									packetContainer.getBlockPositionModifier().write(0, blockPosition);
									getProtocolManager().broadcastServerPacket(packetContainer);
								}
							}

							if (this.progress > 1.0f) {
								playZeroBreakStage(blockPosition);
								customBlockMaterial.breakCustomBlock(block, player);
							}
						}
					}, 0L, 1L));
				} else {
					if (BlockUtils.hasPlayer(player) && !BlockUtils.isWoodenSound(block.getType())) {
						BlockUtils.cancelAllTasksWithThisPlayer(player);
					}
					if (hasSlowDigging) {
						player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
					}
				}
				if (BlockUtils.isWoodenSound(block.getType())) {
					if (BlockUtils.hasPlayer(player)) {
						BlockUtils.cancelAllTasksWithThisPlayer(player);
					}
					BlockUtils.blocks.put(new AbstractMap.SimpleEntry<>(block, player), Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
						static boolean swing = true;
						float ticks = 0.0f;

						@Override
						public void run() {
							Block targetBlock = getTargetBlock(player);
							if (getTargetEntity(player, targetBlock) != null || targetBlock == null) {
								farAway.add(player);
								return;
							} else {
								farAway.remove(player);
							}

							if (!targetBlock.equals(block)) return;

							if (!farAway.contains(player)) {
								getProtocolManager().addPacketListener(new PacketAdapter(Main.getInstance(), PacketType.Play.Client.ARM_ANIMATION) {
									@Override
									public void onPacketReceiving(PacketEvent event) {
										swing = true;
									}
								});
							}

							if (!swing) {
								playZeroBreakStage(blockPosition);
								BlockUtils.cancelAllTasksWithThisPlayer(player);
							}

							this.ticks++;

							if (this.ticks % 4.0f == 0.0f) {
								block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.hit", 0.5f, 0.5f);
								swing = false;
							}
						}
					}, 0L, 1L));
				}
			} else if (digType == EnumWrappers.PlayerDigType.STOP_DESTROY_BLOCK && BlockUtils.getEntryByBlock(block) != null) {
				playZeroBreakStage(blockPosition);
				BlockUtils.cancelAllTasksWithThisBlock(block);
			} else if (
					digType == EnumWrappers.PlayerDigType.ABORT_DESTROY_BLOCK
					&& BlockUtils.getEntryByBlock(block) != null
					&& !farAway.contains(player)
			) {
				Block targetBlock = getTargetBlock(player);
				if (getTargetEntity(player, targetBlock) == null && targetBlock != null) {
					playZeroBreakStage(blockPosition);
					BlockUtils.cancelAllTasksWithThisPlayer(player);
				}
			}
		});
	}

	private static void playZeroBreakStage(@Nonnull BlockPosition blockPosition) {
		PacketContainer packetContainer = getProtocolManager().createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
		packetContainer.getIntegers().write(0, 0).write(1, -1);
		packetContainer.getBlockPositionModifier().write(0, blockPosition);
		getProtocolManager().broadcastServerPacket(packetContainer);
	}
}
