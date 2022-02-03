package github.minersStudios.msBlock.listeners.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;

import javax.annotation.Nonnull;

public class NotePlayListener implements Listener {

    @EventHandler
    public void onNotePlay(@Nonnull NotePlayEvent event) {
        event.setCancelled(true);
    }
}
