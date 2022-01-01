package github.minersStudios.objects;

import github.minersStudios.enumerators.CustomBlockMaterial;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static github.minersStudios.Main.coreProtectAPI;
import static github.minersStudios.Main.plugin;

public class CustomBlock {
    private Instrument instrument;
    private Note note;
    private boolean powered;
    private final Block block;
    private CustomBlockMaterial customBlockMaterial;
    private final Player player;

    public CustomBlock(Block block, Player player) {
        this.player = player;
        this.block = block;

        if(block.getType() != Material.NOTE_BLOCK) return;

        NoteBlock noteBlock = (NoteBlock) block.getBlockData();

        instrument = noteBlock.getInstrument();
        note = noteBlock.getNote();
        powered = noteBlock.isPowered();
        setCustomBlockMaterial(CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()));
    }

    /**
     * Sets CustomBlockMaterial param of a custom block
     */
    public void setCustomBlockMaterial(@Nullable CustomBlockMaterial customBlockMaterial){
        this.customBlockMaterial = customBlockMaterial;
    }

    // CustomBlock
    /**
     * Sets custom block not with CustomBlockMaterial
     */
    public void setCustomBlock(CustomBlockMaterial customBlockMaterial) {
        new BukkitRunnable(){
            @Override
            public void run() {
                if(customBlockMaterial == null) return;
                block.setType(Material.NOTE_BLOCK);

                NoteBlock noteBlock = (NoteBlock) block.getBlockData();
                noteBlock.setInstrument(customBlockMaterial.getInstrument());
                noteBlock.setNote(customBlockMaterial.getNote());
                noteBlock.setPowered(customBlockMaterial.isPowered());
                block.setBlockData(noteBlock);

                setCustomBlockMaterial(CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()));
                block.getWorld().playSound(block.getLocation(), customBlockMaterial.getSoundPlace(), 1.0f, customBlockMaterial.getPitch());

                coreProtectAPI.logPlacement(player.getDisplayName(), block.getLocation(), Material.NOTE_BLOCK, block.getBlockData());

                if (player.getGameMode() == GameMode.SURVIVAL)
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            }
        }.runTask(plugin);
    }

    /**
     * Break custom block vanillish
     */
    public void breakCustomBlock(@Nonnull BlockBreakEvent event){
        Location blockLocation = block.getLocation();
        Player player = event.getPlayer();

        setCustomBlockMaterial(CustomBlockMaterial.getCustomBlockMaterial(note, instrument, powered));
        if(customBlockMaterial == null) return;
        event.setExpToDrop(customBlockMaterial.getExpToDrop());
        block.getWorld().playSound(blockLocation, customBlockMaterial.getSoundBreak(), 1.0f, customBlockMaterial.getPitch());
        block.getWorld().dropItemNaturally(blockLocation, customBlockMaterial.getItemStack());
        block.setType(Material.AIR);

        coreProtectAPI.logRemoval(player.getName(), block.getLocation(), Material.NOTE_BLOCK, block.getBlockData());
    }
}
