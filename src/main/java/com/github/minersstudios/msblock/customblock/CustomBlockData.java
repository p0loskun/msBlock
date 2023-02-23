package com.github.minersstudios.msblock.customblock;

import com.github.minersstudios.msblock.MSBlock;
import com.github.minersstudios.msblock.utils.BlockUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

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

	@Contract("_, _ -> new")
	public static @NotNull CustomBlockData fromConfig(@NotNull File file, @NotNull YamlConfiguration config) {
		String fileName = file.getName();

		NamespacedKey namespacedKey = new NamespacedKey(MSBlock.getInstance(), Objects.requireNonNull(config.getString("namespaced-key"), "namespaced-key in " + fileName + " is null"));

		ConfigurationSection blockSettings = Objects.requireNonNull(
				config.getConfigurationSection("block-settings"),
				"block-settings section in " + fileName + " is null"
		);
		ConfigurationSection item = Objects.requireNonNull(
				config.getConfigurationSection("item"),
				"item section in " + fileName + " is null"
		);
		ConfigurationSection sounds = Objects.requireNonNull(
				config.getConfigurationSection("sounds"),
				"sounds section in " + fileName + " is null"
		);

		CustomBlockData customBlockData = new CustomBlockData(
				namespacedKey,
				(float) blockSettings.getDouble("dig-speed"),
				blockSettings.getInt("drop.experience"),
				blockSettings.getBoolean("drop.drops-default-item", true),
				ToolType.valueOf(blockSettings.getString("tool.type", "HAND")),
				blockSettings.getBoolean("tool.force", false),
				Material.valueOf(Objects.requireNonNull(item.getString("material"), "item.material in " + fileName + " is null")),
				item.getString("name"),
				item.getInt("custom-model-data"),
				craftNoteBlockData(config),
				craftPlaceableMaterials(config),
				SoundGroup.fromConfigSection(sounds),
				PlacingType.valueOf(config.getString("placing.placing-type", "BY_BLOCK_FACE")),
				craftBlockFaceMap(config),
				craftBlockAxisMap(config),
				config.getBoolean("craft.show-in-crafts-menu", false),
				null
		);

		Map<Character, Material> ingredientMap = new HashMap<>();
		ConfigurationSection craftSection = config.getConfigurationSection("craft.material-list");
		if (craftSection != null) {
			for (String key : craftSection.getKeys(false)) {
				ingredientMap.put(key.toCharArray()[0], Material.valueOf(Objects.requireNonNull(craftSection.get(key)).toString()));
			}

			ItemStack craftedItem = customBlockData.craftItemStack().clone();
			craftedItem.setAmount(config.getInt("craft.item-amount", 1));

			ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, craftedItem);
			shapedRecipe.setGroup(MSBlock.getInstance().getName().toLowerCase(Locale.ROOT) + config.getString("craft.group"));
			shapedRecipe.shape(config.getStringList("craft.shaped-recipe").toArray(String[]::new));

			ingredientMap.keySet().forEach(character -> shapedRecipe.setIngredient(character, ingredientMap.get(character)));

			if (customBlockData.isShowInCraftsMenu()) {
				BlockUtils.CUSTOM_BLOCK_RECIPES.add(shapedRecipe);
			}

			Bukkit.addRecipe(shapedRecipe);
			customBlockData.setShapedRecipe(shapedRecipe);
		}
		return customBlockData;
	}

	public static @NotNull CustomBlockData fromNoteBlock(@NotNull NoteBlock noteBlock) {
		return fromInstrumentNotePowered(noteBlock.getInstrument(), noteBlock.getNote(), noteBlock.isPowered());
	}

	@Contract("_, _, _ -> new")
	public static @NotNull CustomBlockData fromInstrumentNotePowered(@NotNull Instrument instrument, @NotNull Note note, boolean powered) {
		NoteBlockData noteBlockData = new NoteBlockData(instrument, note, powered);
		for (CustomBlockData customBlockData : MSBlock.getConfigCache().customBlocks.values()) {
			if (customBlockData.getNoteBlockData() == null) {
				Map<?, NoteBlockData> map =
						customBlockData.getBlockFaceMap() == null
						? customBlockData.getBlockAxisMap()
						: customBlockData.getBlockFaceMap();
				if (map != null) {
					for (NoteBlockData data : map.values()) {
						if (noteBlockData.isSimilar(data)) {
							customBlockData.setNoteBlockData(noteBlockData);
						}
					}
				}
			}
			if (noteBlockData.isSimilar(customBlockData.getNoteBlockData())) return customBlockData;
		}
		return DEFAULT;
	}

	@Contract("_ -> new")
	public static @NotNull CustomBlockData fromCustomModelData(int cmd) {
		CustomBlockData customBlockData = MSBlock.getConfigCache().customBlocks.getBySecondaryKey(cmd);
		return customBlockData == null ? DEFAULT : customBlockData;
	}

	@Contract("_ -> new")
	public static @NotNull CustomBlockData fromKey(String key) {
		CustomBlockData customBlockData = MSBlock.getConfigCache().customBlocks.getByPrimaryKey(key);
		return customBlockData == null ? DEFAULT : customBlockData;
	}

	private static @Nullable Set<Material> craftPlaceableMaterials(@NotNull YamlConfiguration config) {
		Set<Material> placeableMaterials = new HashSet<>();
		for (String material : config.getStringList("placing.placeable-materials")) {
			placeableMaterials.add(Material.valueOf(material));
		}
		return placeableMaterials.isEmpty() ? null : placeableMaterials;
	}

	@Contract("_ -> new")
	private static @Nullable Map<BlockFace, NoteBlockData> craftBlockFaceMap(@NotNull YamlConfiguration config) {
		Map<BlockFace, NoteBlockData> blockFaceMap = new HashMap<>();
		ConfigurationSection configurationSection = config.getConfigurationSection("placing.directional.block-faces");
		if (configurationSection == null) return null;
		for (String blockFace : configurationSection.getKeys(false)) {
			blockFaceMap.put(BlockFace.valueOf(blockFace.toUpperCase(Locale.ROOT)), new NoteBlockData(
					Instrument.valueOf(config.getString("placing.directional.block-faces." + blockFace + ".instrument")),
					new Note(config.getInt("placing.directional.block-faces." + blockFace + ".note")),
					config.getBoolean("placing.directional.block-faces." + blockFace + ".is-powered", false)
			));
		}
		return blockFaceMap.isEmpty() ? null : blockFaceMap;
	}

	private static @Nullable Map<Axis, NoteBlockData> craftBlockAxisMap(@NotNull YamlConfiguration config) {
		Map<Axis, NoteBlockData> blockAxisMap = new HashMap<>();
		ConfigurationSection configurationSection = config.getConfigurationSection("placing.orientable.axes");
		if (configurationSection == null) return null;
		for (String axis : configurationSection.getKeys(false)) {
			blockAxisMap.put(Axis.valueOf(axis.toUpperCase(Locale.ROOT)), new NoteBlockData(
					Instrument.valueOf(config.getString("placing.orientable.axes." + axis + ".instrument")),
					new Note(config.getInt("placing.orientable.axes." + axis + ".note")),
					config.getBoolean("placing.orientable.axes." + axis + ".is-powered", false)
			));
		}
		return blockAxisMap.isEmpty() ? null : blockAxisMap;
	}

	@Contract("_ -> new")
	private static @Nullable NoteBlockData craftNoteBlockData(@NotNull YamlConfiguration config) {
		String instrument = config.getString("placing.normal.instrument");
		return instrument == null
				? null
				: new NoteBlockData(
				Instrument.valueOf(instrument),
				new Note(config.getInt("placing.normal.note")),
				config.getBoolean("placing.normal.is-powered", false)
		);
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
