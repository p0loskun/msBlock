package github.minersStudios.msBlock.enums;

import github.minersStudios.msBlock.Main;
import github.minersStudios.msBlock.utils.BlockUtils;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum CustomBlockMaterial {
    DEFAULT(Instrument.BIT, new Note(0), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Нотный блок", 13),

    TEST(Instrument.XYLOPHONE, new Note(1), true, Sound.BLOCK_AMETHYST_BLOCK_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_AMETHYST_BLOCK_HIT, 1.0f, ToolType.HOE, true, 10, "Test block", 999999),

    VERTICAL_ACACIA_PLANKS(Instrument.BANJO, new Note(0), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Вертикальные акациевые доски", 1000),
    VERTICAL_BIRCH_PLANKS(Instrument.BANJO, new Note(1), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Вертикальные берёзовые доски", 1001),
    VERTICAL_CRIMSON_PLANKS(Instrument.BANJO, new Note(2), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Вертикальные багровые доски", 1002),
    VERTICAL_DARK_OAK_PLANKS(Instrument.BANJO, new Note(3), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Вертикальные доски из тёмной древесины", 1003),
    VERTICAL_JUNGLE_PLANKS(Instrument.BANJO, new Note(4), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Вертикальные тропические доски", 1004),
    VERTICAL_OAK_PLANKS(Instrument.BANJO, new Note(5), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Вертикальные дубовые доски", 1005),
    VERTICAL_SPRUCE_PLANKS(Instrument.BANJO, new Note(6), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Вертикальные еловые доски", 1006),
    VERTICAL_WARPED_PLANKS(Instrument.BANJO, new Note(7), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Вертикальные искажённые доски", 1007),

    CARVED_ACACIA_PLANKS(Instrument.BANJO, new Note(8), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Резные акациевые доски", 1008),
    CARVED_BIRCH_PLANKS(Instrument.BANJO, new Note(9), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Резные берёзовые доски", 1009),
    CARVED_CRIMSON_PLANKS(Instrument.BANJO, new Note(10), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Резные багровые доски", 1010),
    CARVED_DARK_OAK_PLANKS(Instrument.BANJO, new Note(11), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Резные доски из тёмной древесины", 1011),
    CARVED_JUNGLE_PLANKS(Instrument.BANJO, new Note(12), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Резные тропические доски", 1012),
    CARVED_OAK_PLANKS(Instrument.BANJO, new Note(13), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Резные дубовые доски", 1013),
    CARVED_SPRUCE_PLANKS(Instrument.BANJO, new Note(14), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Резные еловые доски", 1014),
    CARVED_WARPED_PLANKS(Instrument.BANJO, new Note(15), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Резные искажённые доски", 1015),

    CRATE_1(Instrument.BANJO, new Note(16), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, 13.0f, ToolType.AXE, false, 0, "Ящик", 1016),
    ;

    @Getter private final Instrument instrument;
    @Getter private final Note note;
    @Getter private final boolean powered;
    @Getter private final Sound soundPlace;
    @Getter private final Sound soundBreak;
    @Getter private final Sound soundHit;
    private final float digSpeed;
    @Getter private final ToolType toolType;
    @Getter private final boolean forceTool;
    @Getter private final int expToDrop;
    private final String itemName;
    private final int itemCustomModelData;

    CustomBlockMaterial(
            @Nonnull Instrument instrument,
            @Nonnull Note note,
            boolean powered,
            @Nonnull Sound soundPlace,
            @Nonnull Sound soundBreak,
            @Nonnull Sound soundHit,
            float digSpeed,
            ToolType toolType,
            boolean forceTool,
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
        this.soundHit = soundHit;
        this.digSpeed = digSpeed;
        this.toolType = toolType;
        this.forceTool = forceTool;
    }

    /**
     * @param player player who breaks the custom block
     * @param customBlockMaterial CustomBlockMaterial of custom block
     * @param itemInMainHand item in main hand
     *
     * @return dig speed float
     */
    public static float getDigSpeed(@Nonnull Player player, @Nonnull CustomBlockMaterial customBlockMaterial, @Nonnull ItemStack itemInMainHand) {
        float base = 1.0f;

        if (customBlockMaterial.getToolType() == ToolType.getToolType(itemInMainHand)) {
            base = ToolTier.getToolTier(itemInMainHand).getSpeed();

            if (itemInMainHand.containsEnchantment(Enchantment.DIG_SPEED)) {
                base += itemInMainHand.getEnchantmentLevel(Enchantment.DIG_SPEED) * 0.3f;
            }
        } else {
            base /= 5.0f;
        }

        PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.FAST_DIGGING);
        if (potionEffect != null) {
            base *= (potionEffect.getAmplifier() + 1) * 0.32f;
        }

        return base / customBlockMaterial.digSpeed;
    }

    /**
     * @return ItemStack of custom block item
     */
    @Nonnull
    public ItemStack getItemStack(){
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setCustomModelData(this.itemCustomModelData);
        itemMeta.setDisplayName(ChatColor.WHITE + this.itemName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * @param note noteblock note
     * @param instrument noteblock instrument
     * @param powered noteblock powered
     * @return CustomBlockMaterial by noteblock
     */
    @Nullable
    @SuppressWarnings("deprecation")
    public static CustomBlockMaterial getCustomBlockMaterial(@Nonnull Note note, @Nonnull Instrument instrument, boolean powered) {
        for (CustomBlockMaterial customBlockMaterial : CustomBlockMaterial.values()) {
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

    /**
     * @param itemCustomModelData CustomModelData of item in main hand
     *
     * @return Custom block material by item in main hand
     */
    @Nullable
    public static CustomBlockMaterial getCustomBlockMaterial(int itemCustomModelData) {
        for (CustomBlockMaterial customBlockMaterial : CustomBlockMaterial.values()) {
            if(customBlockMaterial.itemCustomModelData == itemCustomModelData) return customBlockMaterial;
        }
        return null;
    }

    public void setCustomBlock(@Nonnull Block block, @Nonnull Player player) {
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            block.setType(Material.NOTE_BLOCK);

            NoteBlock noteBlock = (NoteBlock) block.getBlockData();
            noteBlock.setInstrument(instrument);
            noteBlock.setNote(note);
            noteBlock.setPowered(powered);
            block.setBlockData(noteBlock);

            block.getWorld().playSound(block.getLocation(), soundPlace, 1.0f, 1.0f);
            Main.coreProtectAPI.logPlacement(player.getName(), block.getLocation(), Material.NOTE_BLOCK, noteBlock);
            BlockUtils.removeBlock(block.getLocation());
            player.swingMainHand();

            if (player.getGameMode() != GameMode.SURVIVAL) return;
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
        });
    }
}
