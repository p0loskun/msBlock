package com.github.MinersStudios.msBlock.listeners.block;

import com.github.MinersStudios.msBlock.enums.CustomBlockMaterial;
import com.github.MinersStudios.msBlock.utils.BlockUtils;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBlockBreak(@Nonnull BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		if (BlockUtils.isWoodenSound(block.getType())) {
			CustomBlockMaterial.DEFAULT.playBreakSound(block);
		}
		if (block.getBlockData() instanceof NoteBlock noteBlock) {
			CustomBlockMaterial customBlockMaterial = CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered());
			GameMode gameMode = player.getGameMode();
			if (gameMode == GameMode.CREATIVE) {
				customBlockMaterial.playBreakSound(block);
			}
			if (customBlockMaterial.isWooden() && gameMode != GameMode.CREATIVE) {
				event.setDropItems(false);
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, -1, true, false, false));
				block.getWorld().dropItemNaturally(block.getLocation(), customBlockMaterial.getItemStack());
			} else {
				event.setCancelled(gameMode == GameMode.SURVIVAL);
			}
		}
	}
}
