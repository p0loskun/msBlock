package com.github.minersstudios.msblock.listeners.block;

import com.github.minersstudios.msblock.customBlock.CustomBlock;
import com.github.minersstudios.msblock.customBlock.ToolType;
import com.github.minersstudios.msblock.utils.BlockUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBlockBreak(@NotNull BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Location blockLocation = block.getLocation().toCenterLocation();
		if (BlockUtils.isWoodenSound(block.getType())) {
			CustomBlock.DEFAULT.getSoundGroup().playBreakSound(blockLocation);
		}
		if (block.getBlockData() instanceof NoteBlock noteBlock) {
			CustomBlock customBlockMaterial = CustomBlock.getCustomBlock(noteBlock.getInstrument(), noteBlock.getNote(), noteBlock.isPowered());
			GameMode gameMode = player.getGameMode();
			if (gameMode == GameMode.CREATIVE) {
				customBlockMaterial.getSoundGroup().playBreakSound(blockLocation);
			}
			if (customBlockMaterial.getToolType() == ToolType.AXE && gameMode != GameMode.CREATIVE) {
				event.setDropItems(false);
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, true, false, false));
				block.getWorld().dropItemNaturally(block.getLocation(), customBlockMaterial.craftItemStack());
			} else {
				event.setCancelled(gameMode == GameMode.SURVIVAL);
			}
		}
	}
}
