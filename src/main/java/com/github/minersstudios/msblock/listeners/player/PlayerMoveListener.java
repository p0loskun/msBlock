package com.github.minersstudios.msblock.listeners.player;

import com.github.minersstudios.msblock.customBlock.CustomBlock;
import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.msblock.utils.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.annotation.Nonnull;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onPlayerMove(@Nonnull PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Block bottomBlock = player.getLocation().subtract(0.0d, 0.5d, 0.0d).getBlock();
		Location bottomBlockLocation = bottomBlock.getLocation().toCenterLocation();
		if (
				(bottomBlock.getType() == Material.NOTE_BLOCK || BlockUtils.isWoodenSound(bottomBlock.getType()))
				&& bottomBlock.getType().isSolid()
				&& player.getGameMode() != GameMode.SPECTATOR
				&& !player.isFlying()
				&& !player.isSneaking()
		) {
			Location from = event.getFrom().clone(),
					to = event.getTo().clone();
			from.setY(0.0d);
			to.setY(0.0d);
			double distance = from.distance(to);
			if (distance == 0.0d) return;
			double fullDistance = PlayerUtils.steps.containsKey(player) ? PlayerUtils.steps.get(player) + distance : 1.0d;
			PlayerUtils.steps.put(player, fullDistance > 1.25d ? 0.0d : fullDistance);
			if (fullDistance > 1.25d) {
				if (bottomBlock.getBlockData() instanceof NoteBlock noteBlock) {
					CustomBlock.getCustomBlock(noteBlock.getInstrument(), noteBlock.getNote(), noteBlock.isPowered()).getSoundGroup().playStepSound(bottomBlockLocation);
				} else {
					CustomBlock.DEFAULT.getSoundGroup().playStepSound(bottomBlockLocation);
				}
			}
		} else {
			PlayerUtils.steps.remove(player);
		}
	}
}
