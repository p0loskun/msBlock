package com.github.MinersStudios.msBlock.listeners.block;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;

import javax.annotation.Nonnull;

public class NotePlayListener implements Listener {

	@EventHandler
	public void onNotePlay(@Nonnull NotePlayEvent event) {
		event.setCancelled(!(event.getInstrument() == Instrument.BIT && event.getNote().equals(new Note(0))));
	}
}
