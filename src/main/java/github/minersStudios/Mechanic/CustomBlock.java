package github.minersStudios.Mechanic;

import github.minersStudios.Main;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomBlock {
    private Instrument instrument;
    private Note note;
    private boolean powered;
    private Block block;
    private CustomBlockMaterial customBlockMaterial;

    public CustomBlock(Block block) {
        setBlock(block);

        if(!block.getType().equals(Material.NOTE_BLOCK)) return;

        NoteBlock noteBlock = (NoteBlock) block.getBlockData();

        setInstrument(noteBlock.getInstrument());
        setNote(noteBlock.getNote());
        setPowered(noteBlock.isPowered());
        setCustomBlockMaterial(CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()));
    }

    // Instrument

    /**
     * @return instrument param of custom block
     */
    public Instrument getInstrument(){
        return instrument;
    }

    /**
     * Sets instrument param of custom block
     */
    public void setInstrument(@Nonnull Instrument instrument){
        this.instrument = instrument;
    }

    // Note

    /**
     * @return note param of custom block
     */
    public Note getNote(){
        return note;
    }

    /**
     * Sets note param of custom block
     */
    public void setNote(@Nonnull Note note){
        this.note = note;
    }

    // Block

    /**
     * @return block param of custom block
     */
    public Block getBlock(){
        return block;
    }

    /**
     * Sets a block param of custom block
     */
    public void setBlock(@Nonnull Block block){
        this.block = block;
    }

    // Powered

    /**
     * @return boolean isPowered
     */
    public boolean isPowered(){
        return powered;
    }

    /**
     * Sets boolean isPowered
     */
    public void setPowered(boolean powered){
        this.powered = powered;
    }

    // CustomBlockMaterial

    /**
     * @return CustomBlockMaterial param of a custom block
     */
    public CustomBlockMaterial getCustomBlockMaterial(){
        return customBlockMaterial;
    }

    /**
     * Sets CustomBlockMaterial param of a custom block
     */
    public void setCustomBlockMaterial(@Nullable CustomBlockMaterial customBlockMaterial){
        this.customBlockMaterial = customBlockMaterial;
    }

    // CustomBlock

    /**
     * Sets custom block not without CustomBlockMaterial
     */
    @SuppressWarnings("unused")
    public void setCustomBlock(){
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(Material.NOTE_BLOCK, false);

                NoteBlock noteBlock = (NoteBlock) block.getBlockData();

                noteBlock.setInstrument(getInstrument() == null ? getCustomBlockMaterial().getInstrument() : getInstrument());
                noteBlock.setNote(getNote() == null ? getCustomBlockMaterial().getNote() : getNote());
                noteBlock.setPowered(!isPowered() ? getCustomBlockMaterial().isPowered() : isPowered());

                block.setBlockData(noteBlock);
            }
        }.runTaskLater(Main.getInstance(), 1L);
    }

    /**
     * Sets custom block not with CustomBlockMaterial
     */
    public void setCustomBlock(@Nonnull CustomBlockMaterial customBlockMaterial) {
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(Material.NOTE_BLOCK, false);

                NoteBlock noteBlock = (NoteBlock) block.getBlockData();

                noteBlock.setInstrument(getInstrument() == null ? customBlockMaterial.getInstrument() : getInstrument());
                noteBlock.setNote(getNote() == null ? customBlockMaterial.getNote() : getNote());
                noteBlock.setPowered(!isPowered() ? customBlockMaterial.isPowered() : isPowered());

                block.setBlockData(noteBlock);
            }
        }.runTaskLater(Main.getInstance(), 1L);
    }

}
