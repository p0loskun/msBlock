package com.github.minersstudios.msblock.customblock;

import com.github.minersstudios.msblock.MSBlock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class CustomBlockData {
	public static final CustomBlockData DEFAULT = new CustomBlockData(
			//<editor-fold desc="Default note block params">
			new NamespacedKey(MSBlock.getInstance(), "default"),
			11.0f,
			0,
			true,
			ToolType.AXE,
			false,
			Material.NOTE_BLOCK,
			null,
			0,
			new NoteBlockData(Instrument.BIT, new Note(0), false),
			null,
			new SoundGroup(
					"block.wood.place",
					SoundCategory.BLOCKS,
					1.0f,
					0.5f,
					"block.wood.break",
					SoundCategory.BLOCKS,
					1.0f,
					1.0f,
					"block.wood.hit",
					SoundCategory.BLOCKS,
					0.5f,
					0.5f,
					"block.wood.step",
					SoundCategory.PLAYERS,
					0.9f,
					0.3f
			),
			null,
			null,
			null,
			false,
			null
			//</editor-fold>
	);
	private @NotNull NamespacedKey namespacedKey;
	private float digSpeed;
	private int expToDrop;
	private boolean dropsDefaultItem;
	private @NotNull ToolType toolType;
	private boolean forceTool;
	private @NotNull Material itemMaterial;
	private @Nullable String itemName;
	private int itemCustomModelData;
	private @Nullable NoteBlockData noteBlockData;
	private @Nullable Set<Material> placeableMaterials;
	private @NotNull SoundGroup soundGroup;
	private @Nullable PlacingType placingType;
	private @Nullable Map<BlockFace, NoteBlockData> blockFaceMap;
	private @Nullable Map<Axis, NoteBlockData> blockAxisMap;
	private boolean showInCraftsMenu;
	private @Nullable ShapedRecipe shapedRecipe;

	public CustomBlockData(
			@NotNull NamespacedKey namespacedKey,
			float digSpeed,
			int expToDrop,
			boolean dropsDefaultItem,
			@NotNull ToolType toolType,
			boolean forceTool,
			@NotNull Material itemMaterial,
			@Nullable String itemName,
			int itemCustomModelData,
			@Nullable NoteBlockData noteBlockData,
			@Nullable Set<Material> placeableMaterials,
			@NotNull SoundGroup soundGroup,
			@Nullable PlacingType placingType,
			@Nullable Map<BlockFace, NoteBlockData> blockFaceMap,
			@Nullable Map<Axis, NoteBlockData> blockAxisMap,
			boolean showInCraftsMenu,
			@Nullable ShapedRecipe shapedRecipe
	) {
		this.namespacedKey = namespacedKey;
		this.digSpeed = digSpeed;
		this.expToDrop = expToDrop;
		this.dropsDefaultItem = dropsDefaultItem;
		this.toolType = toolType;
		this.forceTool = forceTool;
		this.itemMaterial = itemMaterial;
		this.itemName = itemName;
		this.itemCustomModelData = itemCustomModelData;
		this.noteBlockData = noteBlockData;
		this.placeableMaterials = placeableMaterials;
		this.soundGroup = soundGroup;
		this.placingType = placingType;
		this.blockFaceMap = blockFaceMap;
		this.blockAxisMap = blockAxisMap;
		this.showInCraftsMenu = showInCraftsMenu;
		this.shapedRecipe = shapedRecipe;
	}

	public void setNamespacedKey(@NotNull NamespacedKey namespacedKey) {
		this.namespacedKey = namespacedKey;
	}

	public @NotNull NamespacedKey getNamespacedKey() {
		return this.namespacedKey;
	}

	public void setDigSpeed(float digSpeed) {
		this.digSpeed = digSpeed;
	}

	public float getDigSpeed() {
		return this.digSpeed;
	}

	public float getCalculatedDigSpeed(@NotNull Player player) {
		float base = 1.0f;
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

		if (this.toolType == ToolType.getToolType(itemInMainHand)) {
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

		return base / this.digSpeed;
	}

	public void setExpToDrop(int expToDrop) {
		this.expToDrop = expToDrop;
	}

	public int getExpToDrop() {
		return this.expToDrop;
	}

	public void setDropsDefaultItem(boolean dropsDefaultItem) {
		this.dropsDefaultItem = dropsDefaultItem;
	}

	public boolean isDropsDefaultItem() {
		return this.dropsDefaultItem;
	}

	public void setToolType(@NotNull ToolType toolType) {
		this.toolType = toolType;
	}

	public @NotNull ToolType getToolType() {
		return this.toolType;
	}

	public void setForceTool(boolean forceTool) {
		this.forceTool = forceTool;
	}

	public boolean isForceTool() {
		return this.forceTool;
	}

	public void setItemMaterial(@NotNull Material itemMaterial) {
		this.itemMaterial = itemMaterial;
	}

	public @NotNull Material getItemMaterial() {
		return this.itemMaterial;
	}

	public void setItemName(@Nullable String itemName) {
		this.itemName = itemName;
	}

	public @Nullable String getItemName() {
		return this.itemName;
	}

	public void setItemCustomModelData(int itemCustomModelData) {
		this.itemCustomModelData = itemCustomModelData;
	}

	public int getItemCustomModelData() {
		return this.itemCustomModelData;
	}

	public void setNoteBlockData(@Nullable NoteBlockData noteBlockData) {
		this.noteBlockData = noteBlockData;
	}

	public @Nullable NoteBlockData getNoteBlockData() {
		return this.noteBlockData;
	}

	public void setPlaceableMaterials(@Nullable Set<Material> placeableMaterials) {
		this.placeableMaterials = placeableMaterials;
	}

	public @Nullable Set<Material> getPlaceableMaterials() {
		return this.placeableMaterials;
	}

	public void setSoundGroup(@NotNull SoundGroup soundGroup) {
		this.soundGroup = soundGroup;
	}

	public @NotNull SoundGroup getSoundGroup() {
		return this.soundGroup;
	}

	public void setPlacingType(@Nullable PlacingType placingType) {
		this.placingType = placingType;
	}

	public @Nullable PlacingType getPlacingType() {
		return this.placingType;
	}

	public void setBlockFaceMap(@Nullable Map<BlockFace, NoteBlockData> blockFaceMap) {
		this.blockFaceMap = blockFaceMap;
	}

	public @Nullable Map<BlockFace, NoteBlockData> getBlockFaceMap() {
		return this.blockFaceMap;
	}

	public void setBlockAxisMap(@Nullable Map<Axis, NoteBlockData> blockAxisMap) {
		this.blockAxisMap = blockAxisMap;
	}

	public @Nullable Map<Axis, NoteBlockData> getBlockAxisMap() {
		return this.blockAxisMap;
	}

	public void setShowInCraftsMenu(boolean showInCraftsMenu) {
		this.showInCraftsMenu = showInCraftsMenu;
	}

	public boolean isShowInCraftsMenu() {
		return this.showInCraftsMenu;
	}

	public void setShapedRecipe(@Nullable ShapedRecipe shapedRecipe) {
		this.shapedRecipe = shapedRecipe;
	}

	public @Nullable ShapedRecipe getShapedRecipe() {
		return this.shapedRecipe;
	}

	public @NotNull ItemStack craftItemStack() {
		ItemStack itemStack = new ItemStack(itemMaterial);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;
		itemMeta.setCustomModelData(this.itemCustomModelData);
		if (this.itemName != null) {
			itemMeta.displayName(
					Component.text()
							.append(Component.text(this.itemName)
							.style(Style.style(
									NamedTextColor.WHITE,
									TextDecoration.OBFUSCATED.withState(false),
									TextDecoration.BOLD.withState(false),
									TextDecoration.ITALIC.withState(false),
									TextDecoration.STRIKETHROUGH.withState(false),
									TextDecoration.UNDERLINED.withState(false)
							)))
							.build()
			);
		}
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public @Nullable Set<Axis> getAxes() {
		if (this.blockAxisMap == null) return null;
		return this.blockAxisMap.keySet();
	}

	public @Nullable Set<BlockFace> getFaces() {
		if (this.blockFaceMap == null) return null;
		return this.blockFaceMap.keySet();
	}

	public enum PlacingType {
		BY_BLOCK_FACE, BY_EYE_POSITION
	}
}
