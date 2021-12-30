package github.minersStudios.Mechanic;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public enum CustomBlockMaterial {
    VERTICAL_ACACIA_PLANKS(
                Instrument.BANJO, new Note(0), false,
                Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, 1.0f,
            0, "Вертикальные акациевые доски", 1000),
    VERTICAL_BIRCH_PLANKS(
            Instrument.BANJO, new Note(1), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, 1.0f,
            0, "Вертикальные берёзовые доски", 1001),
    VERTICAL_CRIMSON_PLANKS(
            Instrument.BANJO, new Note(2), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, 1.0f,
            0, "Вертикальные багровые доски", 1002),
    VERTICAL_DARK_OAK_PLANKS(
            Instrument.BANJO, new Note(3), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, 1.0f,
            0, "Вертикальные доски из тёмной древесины", 1003),
    VERTICAL_JUNGLE_PLANKS(
            Instrument.BANJO, new Note(4), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, 1.0f,
            0, "Вертикальные тропические доски", 1004),
    VERTICAL_OAK_PLANKS(
            Instrument.BANJO, new Note(5), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, 1.0f,
            0, "Вертикальные дубовые доски", 1005),
    VERTICAL_SPRUCE_PLANKS(
            Instrument.BANJO, new Note(6), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, 1.0f,
            0, "Вертикальные еловые доски", 1006),
    VERTICAL_WARPED_PLANKS(
            Instrument.BANJO, new Note(7), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, 1.0f,
            0, "Вертикальные искажённые доски", 1007),
    ;

    private final Instrument instrument;
    private final Note note;
    private final boolean powered;
    private final Sound soundPlace;
    private final Sound soundBreak;
    private final float pitch;
    private final int expToDrop;
    private final String itemName;
    private final int itemCustomModelData;

    CustomBlockMaterial(
            @Nonnull Instrument instrument,
            @Nonnull Note note,
            boolean powered,
            @Nonnull Sound soundPlace,
            @Nonnull Sound soundBreak,
            float pitch,
            int expToDrop,
            String itemName,
            int itemCustomModelData
    ) {
        this.instrument = instrument;
        this.note = note;
        this.powered = powered;

        this.itemName = itemName;
        this.itemCustomModelData = itemCustomModelData;

        this.expToDrop = expToDrop;
        this.soundPlace = soundPlace;
        this.soundBreak = soundBreak;
        this.pitch = pitch;
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
     * @return Sound place of custom block
     */
    public Sound getSoundPlace() {
        return soundPlace;
    }

    /**
     * @return Sound break of custom block
     */
    public Sound getSoundBreak() {
        return soundBreak;
    }

    /**
     * @return Pitch float for sound
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * @return Exp int to drop
     */
    public int getExpToDrop() {
        return expToDrop;
    }

    /**
     * @return ItemStack of item
     */
    public ItemStack getItemStack(boolean setItemName){
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setCustomModelData(itemCustomModelData);
        itemMeta.setDisplayName(setItemName ? ChatColor.WHITE + itemName : null);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * @return Custom block material
     */
    @SuppressWarnings("deprecation")
    public static CustomBlockMaterial getCustomBlockMaterial(@Nonnull Note note, @Nonnull Instrument instrument, boolean powered) {
        for(CustomBlockMaterial customBlockMaterial : CustomBlockMaterial.values())
        {
            if(
                    customBlockMaterial.isPowered() == powered &&
                    customBlockMaterial.getInstrument() == instrument &&
                    customBlockMaterial.getNote().getId() == note.getId()
            ){
                return customBlockMaterial;
            }
        }
        return null;
    }
}
