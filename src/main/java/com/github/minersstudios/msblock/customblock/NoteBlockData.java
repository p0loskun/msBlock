package com.github.minersstudios.msblock.customblock;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class NoteBlockData {
	private @NotNull Instrument instrument;
	private @NotNull Note note;
	private boolean powered;

	public NoteBlockData(
			@NotNull Instrument instrument,
			@NotNull Note note,
			boolean powered
	) {
		this.instrument = instrument;
		this.note = note;
		this.powered = powered;
	}

	public @NotNull NoteBlock craftNoteBlock(@NotNull BlockData blockData) {
		NoteBlock noteBlock = (NoteBlock) blockData;
		noteBlock.setInstrument(this.instrument);
		noteBlock.setNote(this.note);
		noteBlock.setPowered(this.powered);
		return noteBlock;
	}

	public boolean isSimilar(@NotNull NoteBlockData noteBlockData) {
		return this.instrument == noteBlockData.instrument
				&& this.note.equals(noteBlockData.note)
				&& this.powered == noteBlockData.powered;
	}

	public void setInstrument(@NotNull Instrument instrument) {
		this.instrument = instrument;
	}

	public @NotNull Instrument getInstrument() {
		return this.instrument;
	}

	public void setNote(@NotNull Note note) {
		this.note = note;
	}

	public @NotNull Note getNote() {
		return this.note;
	}

	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	public boolean isPowered() {
		return this.powered;
	}
}
