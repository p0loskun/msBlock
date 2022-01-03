package github.minersStudios.enumerators;

import github.minersStudios.objects.CustomBlock;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import java.util.Objects;

public enum CustomBlockMaterial {
    VERTICAL_ACACIA_PLANKS(
            Instrument.BANJO, new Note(0), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT,
            13.0f, ToolType.AXE, false,
            0,
            "Вертикальные акациевые доски", 1000),
    VERTICAL_BIRCH_PLANKS(
            Instrument.BANJO, new Note(1), false,
            Sound.BLOCK_AMETHYST_BLOCK_PLACE, Sound.BLOCK_AMETHYST_BLOCK_BREAK, Sound.BLOCK_AMETHYST_BLOCK_HIT,
            13.0f, ToolType.AXE, false,
            0,
            "Вертикальные берёзовые доски", 1001),
    VERTICAL_CRIMSON_PLANKS(
            Instrument.BANJO, new Note(2), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT,
            13.0f, ToolType.AXE, false,
            0,
            "Вертикальные багровые доски", 1002),
    VERTICAL_DARK_OAK_PLANKS(
            Instrument.BANJO, new Note(3), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT,
            13.0f, ToolType.AXE, false,
            0,
            "Вертикальные доски из тёмной древесины", 1003),
    VERTICAL_JUNGLE_PLANKS(
            Instrument.BANJO, new Note(4), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT,
            13.0f, ToolType.AXE, false,
            0,
            "Вертикальные тропические доски", 1004),
    VERTICAL_OAK_PLANKS(
            Instrument.BANJO, new Note(5), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT,
            13.0f, ToolType.AXE, false,
            0,
            "Вертикальные дубовые доски", 1005),
    VERTICAL_SPRUCE_PLANKS(
            Instrument.BANJO, new Note(6), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT,
            13.0f, ToolType.AXE, false,
            0,
            "Вертикальные еловые доски", 1006),
    VERTICAL_WARPED_PLANKS(
            Instrument.BANJO, new Note(7), false,
            Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT,
            13.0f, ToolType.AXE, false,
            0,
            "Вертикальные искажённые доски", 1007),
    ;

    private final Instrument instrument;
    private final Note note;
    private final boolean powered;

    private final Sound soundPlace;
    private final Sound soundBreak;
    private final Sound soundHit;

    private final float digSpeed;
    private final ToolType toolType;
    private final boolean forceTool;

    private final int expToDrop;

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

    // NoteBlockData

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

    // Sound

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
     * @return Sound hit of custom block
     */
    public Sound getSoundHit() {
        return soundHit;
    }

    //Dig

    /**
     * @return ToolType
     */
    public ToolType getToolType() {
        return toolType;
    }

    /**
     * @return Dig speed
     */
    public static float getDigSpeed(Player player, CustomBlock block)
    {
        ItemStack heldItem = player.getInventory().getItem(EquipmentSlot.HAND);
        ToolTier tier = ToolTier.fromItemStack(heldItem);
        float base = 1;

        if (block.getCustomBlockMaterial().getToolType() == ToolType.getTool(heldItem)){
            base = tier.speed;

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

        return base / block.getCustomBlockMaterial().digSpeed;
    }

    /**
     * @return True if force tool type
     */
    public boolean isForceTool() {
        return forceTool;
    }


    // Other

    /**
     * @return Exp int to drop
     */
    public int getExpToDrop() {
        return expToDrop;
    }

    /**
     * @return ItemStack of item
     */
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
