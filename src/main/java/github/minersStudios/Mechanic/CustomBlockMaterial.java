package github.minersStudios.Mechanic;

import org.bukkit.Instrument;
import org.bukkit.Note;

import javax.annotation.Nonnull;

public enum CustomBlockMaterial {
    VERTICAL_ACACIA_PLANKS(Instrument.BANJO, new Note(0), false),
    VERTICAL_BIRCH_PLANKS(Instrument.BANJO, new Note(1), false),
    VERTICAL_CRIMSON_PLANKS(Instrument.BANJO, new Note(2), false),
    VERTICAL_DARK_OAK_PLANKS(Instrument.BANJO, new Note(3), false),
    VERTICAL_JUNGLE_PLANKS(Instrument.BANJO, new Note(4), false),
    VERTICAL_OAK_PLANKS(Instrument.BANJO, new Note(5), false),
    VERTICAL_SPRUCE_PLANKS(Instrument.BANJO, new Note(6), false),
    VERTICAL_WARPED_PLANKS(Instrument.BANJO, new Note(7), false),
    ;

    private final Instrument instrument;
    private final Note note;
    private final boolean powered;

    CustomBlockMaterial(@Nonnull Instrument instrument, @Nonnull Note note, boolean powered) {
        this.instrument = instrument;
        this.note = note;
        this.powered = powered;
    }

    /**
     * @return Instrument of Material
     */
    public Instrument getInstrument() {
        return instrument;
    }

    /**
     * @return Note of Material
     */
    public Note getNote() {
        return note;
    }

    /**
     * @return True if Material is powered
     */
    public boolean isPowered() {
        return powered;
    }

    /**
     * @return Custom block material
     */
    @SuppressWarnings("deprecation")
    public static CustomBlockMaterial getCustomBlockMaterial(@Nonnull Note note, @Nonnull Instrument instrument, boolean powered) {
        for(CustomBlockMaterial customBlockMaterial : CustomBlockMaterial.values())
        {
            if(
                    customBlockMaterial.isPowered() == powered
                    && customBlockMaterial.getInstrument() == instrument
                    && customBlockMaterial.getNote().getId() == note.getId()
            ){
                return customBlockMaterial;
            }
        }
        return null;
    }
}
