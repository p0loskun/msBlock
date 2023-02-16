package com.github.minersstudios.msblock.events;

import com.github.minersstudios.msblock.customblock.CustomBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomBlockBreakEvent extends CustomBlockEvent implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	protected boolean cancel;

	protected final Player player;

	public CustomBlockBreakEvent(
			@NotNull final CustomBlock breakedCustomBlock,
			@NotNull final Player player
	) {
		super(breakedCustomBlock);
		this.player = player;
		this.cancel = false;
	}

	@Override
	public boolean isCancelled() {
		return this.cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	/**
	 * Gets the player who broke the custom block involved in this event
	 *
	 * @return The Player who broke the custom block involved in this event
	 */
	public @NotNull Player getPlayer() {
		return this.player;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}

	public static @NotNull HandlerList getHandlerList() {
		return handlers;
	}
}
