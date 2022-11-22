package com.github.minersstudios.msblock.customBlock;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class NoteBlockData {
	private Instrument instrument;
	private Note note;
	private boolean powered;

	public NoteBlockData(
			@Nonnull Instrument instrument,
			@Nonnull Note note,
			boolean powered
	) {
		this.instrument = instrument;
		this.note = note;
		this.powered = powered;
	}

	@Nonnull
	public NoteBlock craftNoteBlock(@Nonnull BlockData blockData) {
		NoteBlock noteBlock = (NoteBlock) blockData;
		noteBlock.setInstrument(this.instrument);
		noteBlock.setNote(this.note);
		noteBlock.setPowered(this.powered);
		return noteBlock;
	}

	public void setInstrument(@Nonnull Instrument instrument) {
		this.instrument = instrument;
	}

	@Nonnull
	public Instrument getInstrument() {
		return this.instrument;
	}

	public void setNote(@Nonnull Note note) {
		this.note = note;
	}

	@Nonnull
	public Note getNote() {
		return this.note;
	}

	public void setPowered(boolean powered) {
		this.powered = powered;
	}

	public boolean isPowered() {
		return this.powered;
	}
}
