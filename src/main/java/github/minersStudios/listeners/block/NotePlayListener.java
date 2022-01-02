package github.minersStudios.listeners.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;

public class NotePlayListener implements Listener {

    @EventHandler
    public void onNotePlay(NotePlayEvent event) {
        event.setCancelled(true);
    }

}
