package github.minersStudios.msBlock.listeners.player;

import github.minersStudios.msBlock.enums.CustomBlockMaterial;
import github.minersStudios.msBlock.utils.BlockUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.annotation.Nonnull;

import static github.minersStudios.msBlock.utils.PlayerUtils.steps;

public class PlayerMoveListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerMove(@Nonnull PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Block bottomBlock = player.getLocation().subtract(0.0d, 0.5d, 0.0d).getBlock();
		if (
				(bottomBlock.getType() == Material.NOTE_BLOCK || BlockUtils.isWoodenSound(bottomBlock.getType()))
				&& event.getTo() != null
				&& player.getGameMode() != GameMode.SPECTATOR
				&& !player.isSneaking()
				&& bottomBlock.getType().isSolid()
		) {
			Location from = event.getFrom().clone(),
					to = event.getTo().clone();
			from.setY(0);
			to.setY(0);
			double distance = from.distance(to);
			if (distance == 0.0d) return;
			double fullDistance = steps.containsKey(player) ? steps.get(player) + distance : 1.0d;
			steps.put(player, fullDistance > 1.25d ? 0.0d : fullDistance);
			if (fullDistance > 1.25d) {
				if (bottomBlock.getBlockData() instanceof NoteBlock noteBlock) {
					CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()).playStepSound(bottomBlock);
				} else {
					bottomBlock.getWorld().playSound(bottomBlock.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.step", 0.3f, 0.9f);
				}
			}
		} else {
			steps.remove(player);
		}
	}
}