package github.minersStudios.msBlock.enumerators;

import lombok.Getter;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * CustomBlockMaterial enum with blocks parameters
 */
public enum CustomBlockMaterial {
    TEST(Instrument.XYLOPHONE, new Note(1), true, null, null, null, 20.0f, ToolType.HOE, true, 10, "Test block", 999999),

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

    // NoteBlock Data

    /** Instrument for custom block texture */
    @Getter private final Instrument instrument;

    /** Note for custom block texture */
    @Getter private final Note note;

    /** True if CustomDecorMaterial isPowered == true for custom block texture */
    @Getter private final boolean powered;

    // Sounds

    /** Place sound for custom block */
    @Getter private final Sound soundPlace;

    /** Break sound for custom block */
    @Getter private final Sound soundBreak;

    /** Hit sound for custom block */
    @Getter private final Sound soundHit;

    // Digging

    /** Dig speed float */
    private final float digSpeed;

    /** ToolType that will be used to determine the speed of digging */
    @Getter private final ToolType toolType;

    /** True if force tool type */
    @Getter private final boolean forceTool;

    // Other

    /** Experience to drop */
    @Getter private final int expToDrop;

    /** CustomBlock item name */
    private final String itemName;

    /** CustomBlock item CustomModelData */
    private final int itemCustomModelData;

    CustomBlockMaterial(
            @Nonnull Instrument instrument,
            @Nonnull Note note,
            boolean powered,

            @Nullable Sound soundPlace,
            @Nullable Sound soundBreak,
            @Nullable Sound soundHit,

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
     * Gets dig speed float
     *
     * @param player player who breaks the custom block
     * @param customBlockMaterial Custom block material enum
     *
     * @return dig speed float
     */
    public static float getDigSpeed(@Nonnull Player player, @Nonnull CustomBlockMaterial customBlockMaterial) {
        ItemStack heldItem = player.getInventory().getItem(EquipmentSlot.HAND);
        ToolTier tier = ToolTier.fromItemStack(heldItem);
        float base = 1;

        if (customBlockMaterial.getToolType() == ToolType.getToolType(heldItem)){
            base = tier.getSpeed();

            if (heldItem.containsEnchantment(Enchantment.DIG_SPEED)) {
                float level = heldItem.getEnchantmentLevel(Enchantment.DIG_SPEED);
                base += level * 0.3f;
            }
        } else {
            base /= 5.0f;
        }

        if (player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
            float level = Objects.requireNonNull(player.getPotionEffect(PotionEffectType.FAST_DIGGING)).getAmplifier() + 1;
            base *= level * 0.32f;
        }

        return base / customBlockMaterial.digSpeed;
    }

    /**
     * @return ItemStack of item
     */
    @Nonnull
    public ItemStack getItemStack(){
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setCustomModelData(itemCustomModelData);
        itemMeta.setDisplayName(ChatColor.WHITE + itemName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * @return Custom block material
     */
    @Nullable
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

    /**
     * @return Custom block material
     */
    @Nullable
    public static CustomBlockMaterial getCustomBlockMaterialByCMD(int itemCustomModelData) {
        for(CustomBlockMaterial customBlockMaterial : CustomBlockMaterial.values())
        {
            if(
                    customBlockMaterial.itemCustomModelData == itemCustomModelData
            ){
                return customBlockMaterial;
            }
        }
        return null;
    }
}
