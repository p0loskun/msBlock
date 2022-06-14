package github.minersStudios.msBlock.objects;

import github.minersStudios.msBlock.enumerators.CustomBlockMaterial;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static github.minersStudios.msBlock.Main.coreProtectAPI;
import static github.minersStudios.msBlock.Main.plugin;

@Nonnull
public class CustomBlock {

    private final Block block;
    private final Player player;

    /** CustomBlockMaterial param of a custom block */
    @Nullable @Getter @Setter(AccessLevel.PRIVATE) private CustomBlockMaterial customBlockMaterial;

    /**
     * @param block clicked block / block at face
     * @param player player who interacts
     */
    public CustomBlock(@Nonnull Block block, @Nonnull Player player) {
        this.player = player;
        this.block = block;

        if(block.getType() != Material.NOTE_BLOCK) return;
        NoteBlock noteBlock = (NoteBlock) block.getBlockData();
        this.setCustomBlockMaterial(CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()));
    }

    /**
     * Sets custom block not with CustomBlockMaterial
     *
     * @param customBlockMaterial custom block material that is used to place the custom block
     */
    public void setCustomBlock(@Nonnull CustomBlockMaterial customBlockMaterial) {
        new BukkitRunnable(){
            @Override
            public void run() {
                block.setType(Material.NOTE_BLOCK);

                NoteBlock noteBlock = (NoteBlock) block.getBlockData();
                noteBlock.setInstrument(customBlockMaterial.getInstrument());
                noteBlock.setNote(customBlockMaterial.getNote());
                noteBlock.setPowered(customBlockMaterial.isPowered());
                block.setBlockData(noteBlock);

                setCustomBlockMaterial(CustomBlockMaterial.getCustomBlockMaterial(noteBlock.getNote(), noteBlock.getInstrument(), noteBlock.isPowered()));
                block.getWorld().playSound(block.getLocation(), customBlockMaterial.getSoundPlace(), 1.0f, 1.0f);
                coreProtectAPI.logPlacement(player.getName(), block.getLocation(), Material.NOTE_BLOCK, block.getBlockData());

                if (player.getGameMode() == GameMode.SURVIVAL)
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            }
        }.runTask(plugin);
    }
}
