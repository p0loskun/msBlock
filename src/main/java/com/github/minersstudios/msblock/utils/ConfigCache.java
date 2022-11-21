package com.github.minersstudios.msblock.utils;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.customBlock.CustomBlock;
import com.github.minersstudios.msblock.customBlock.NoteBlockData;
import com.github.minersstudios.msblock.customBlock.SoundGroup;
import com.github.minersstudios.msblock.customBlock.ToolType;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class ConfigCache {
	public final Map<String, CustomBlock> customBlocks = new HashMap<>();
	@Nonnull public final String
			woodSoundPlace,
			woodSoundBreak,
			woodSoundStep,
			woodSoundHit;

	public ConfigCache() {
		File configFile = new File(Main.getInstance().getDataFolder(), "config.yml");
		YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

		this.woodSoundPlace = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.place"), "wood-sound.place is null");
		this.woodSoundBreak = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.break"));
		this.woodSoundStep = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.step"));
		this.woodSoundHit = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.hit"));

		try (Stream<Path> path = Files.walk(Paths.get(Main.getInstance().getDataFolder() + "/blocks"))) {
			path.filter(Files::isRegularFile)
					.map(Path::toFile)
					.forEach((blocksFile) -> {
						String fileName = blocksFile.getName();
						if (fileName.equalsIgnoreCase("example.yml")) return;
						YamlConfiguration blockConfig = YamlConfiguration.loadConfiguration(blocksFile);
						Map<Character, Material> ingredientMap = new HashMap<>();
						ConfigurationSection craftSection = blockConfig.getConfigurationSection("craft.material-list");
						if (craftSection != null) {
							for (String key : craftSection.getKeys(false)) {
								ingredientMap.put(key.toCharArray()[0], Material.valueOf(Objects.requireNonNull(craftSection.get(key)).toString()));
							}
						} else {
							ingredientMap = null;
						}

						CustomBlock customBlock = new CustomBlock(
								new NamespacedKey(Main.getInstance(), Objects.requireNonNull(blockConfig.getString("namespaced-key"), "namespaced-key in " + fileName + " is null")),
								(float) blockConfig.getDouble("block-settings.dig-speed"),
								blockConfig.getInt("block-settings.drop.experience"),
								blockConfig.getBoolean("block-settings.drop.drops-default-item", true),
								ToolType.valueOf(blockConfig.getString("block-settings.tool.type", "HAND")),
								blockConfig.getBoolean("block-settings.tool.force", false),
								Material.valueOf(Objects.requireNonNull(blockConfig.getString("item.material"), "item.material in " + fileName + " is null")),
								blockConfig.getString("item.name"),
								blockConfig.getInt("item.custom-model-data"),
								this.craftNoteBlockData(blockConfig),
								this.getPlaceableMaterials(blockConfig),
								new SoundGroup(
										blockConfig.getString("sounds.place.sound-name"),
										(float) blockConfig.getDouble("sounds.place.pitch"),
										(float) blockConfig.getDouble("sounds.place.volume"),
										blockConfig.getString("sounds.break.sound-name"),
										(float) blockConfig.getDouble("sounds.break.pitch"),
										(float) blockConfig.getDouble("sounds.break.volume"),
										blockConfig.getString("sounds.hit.sound-name"),
										(float) blockConfig.getDouble("sounds.hit.pitch"),
										(float) blockConfig.getDouble("sounds.hit.volume"),
										blockConfig.getString("sounds.step.sound-name"),
										(float) blockConfig.getDouble("sounds.step.pitch"),
										(float) blockConfig.getDouble("sounds.step.volume")
								),
								CustomBlock.PlacingType.valueOf(blockConfig.getString("placing.placing-type", "BY_BLOCK_FACE")),
								this.getBlockFaceMap(blockConfig),
								this.getBlockAxisMap(blockConfig),
								blockConfig.getBoolean("craft.show-in-crafts-menu", false),
								null
						);
						if (ingredientMap != null) {
							List<String> customBlockShapedRecipe = blockConfig.getStringList("craft.shaped-recipe");
							ItemStack craftedItem = customBlock.getItemStack().clone();
							craftedItem.setAmount(blockConfig.getInt("craft.item-amount", 1));
							ShapedRecipe shapedRecipe = new ShapedRecipe(customBlock.getNamespacedKey(), craftedItem);
							shapedRecipe.setGroup(Main.getInstance().getName().toLowerCase(Locale.ROOT) + blockConfig.getString("craft.group"));
							shapedRecipe.shape(customBlockShapedRecipe.toArray(String[]::new));
							for (Character character : ingredientMap.keySet()) {
								shapedRecipe.setIngredient(character, ingredientMap.get(character));
							}
							Bukkit.addRecipe(shapedRecipe);
							customBlock.setShapedRecipe(shapedRecipe);
						}
						this.customBlocks.put(customBlock.getNamespacedKey().getKey(), customBlock);
					});
		} catch (IOException e) {
			Main.getInstance().getLogger().info(ExceptionUtils.getFullStackTrace(e));
		}
	}

	@Nullable
	private Set<Material> getPlaceableMaterials(@Nonnull YamlConfiguration blockConfig) {
		Set<Material> placeableMaterials = new HashSet<>();
		for (String material : blockConfig.getStringList("placing.placeable-materials")) {
			placeableMaterials.add(Material.valueOf(material));
		}
		return placeableMaterials.isEmpty() ? null : placeableMaterials;
	}

	@Nullable
	private Map<BlockFace, NoteBlockData> getBlockFaceMap(@Nonnull YamlConfiguration blockConfig) {
		Map<BlockFace, NoteBlockData> blockFaceMap = new HashMap<>();
		ConfigurationSection configurationSection = blockConfig.getConfigurationSection("placing.directional.block-faces");
		if (configurationSection == null) return null;
		for (String blockFace : configurationSection.getKeys(false)) {
			blockFaceMap.put(BlockFace.valueOf(blockFace.toUpperCase(Locale.ROOT)), new NoteBlockData(
					Instrument.valueOf(blockConfig.getString("placing.directional.block-faces." + blockFace + ".instrument")),
					new Note(blockConfig.getInt("placing.directional.block-faces." + blockFace + ".note")),
					blockConfig.getBoolean("placing.directional.block-faces." + blockFace + ".is-powered", false)
			));
		}
		return blockFaceMap.isEmpty() ? null : blockFaceMap;
	}

	@Nullable
	private Map<Axis, NoteBlockData> getBlockAxisMap(@Nonnull YamlConfiguration blockConfig) {
		Map<Axis, NoteBlockData> blockAxisMap = new HashMap<>();
		ConfigurationSection configurationSection = blockConfig.getConfigurationSection("placing.orientable.axes");
		if (configurationSection == null) return null;
		for (String axis : configurationSection.getKeys(false)) {
			blockAxisMap.put(Axis.valueOf(axis.toUpperCase(Locale.ROOT)), new NoteBlockData(
					Instrument.valueOf(blockConfig.getString("placing.orientable.axes." + axis + ".instrument")),
					new Note(blockConfig.getInt("placing.orientable.axes." + axis + ".note")),
					blockConfig.getBoolean("placing.orientable.axes." + axis + ".is-powered", false)
			));
		}
		return blockAxisMap.isEmpty() ? null : blockAxisMap;
	}

	@Nullable
	private NoteBlockData craftNoteBlockData(@Nonnull YamlConfiguration blockConfig) {
		String instrument = blockConfig.getString("placing.normal.instrument");
		return
				instrument == null ? null :
				new NoteBlockData(
						Instrument.valueOf(instrument),
						new Note(blockConfig.getInt("placing.normal.note")),
						blockConfig.getBoolean("placing.normal.is-powered", false)
				);
	}
}
