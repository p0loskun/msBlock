package github.minersStudios.listeners.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NotePlayListener implements Listener {

    @EventHandler
    public void onNotePlay(org.bukkit.event.block.NotePlayEvent event) {
        event.setCancelled(true);
    }

}
