package com.github.minersstudios.msblock.utils;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.enums.CustomBlock;
import com.github.minersstudios.msblock.enums.ToolType;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ShapedRecipe;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ConfigCache {
	public final YamlConfiguration yamlConfiguration;
	public final Map<String, CustomBlock> customBlocks = new HashMap<>();
	@Nonnull public final String
			wood_sound_place,
			wood_sound_break,
			wood_sound_step,
			wood_sound_hit;

	public ConfigCache() {
		File configFile = new File(Main.getInstance().getDataFolder(), "config.yml");
		yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

		this.wood_sound_place = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.place"));
		this.wood_sound_break = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.break"));
		this.wood_sound_step = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.step"));
		this.wood_sound_hit = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.hit"));

		try {
			Files.walk(Paths.get(Main.getInstance().getDataFolder() + "/blocks"))
					.filter(Files::isRegularFile)
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
								Objects.requireNonNull(blockConfig.getString("namespaced-key"), "namespaced-key in " + fileName + " is null"),
								(float) blockConfig.getDouble("block-settings.dig-speed"),
								blockConfig.getInt("block-settings.drop.experience"),
								blockConfig.getBoolean("block-settings.drop.drops-default-item"),
								ToolType.valueOf(blockConfig.getString("block-settings.tool.type")),
								blockConfig.getBoolean("block-settings.tool.force"),
								Material.valueOf(blockConfig.getString("item.material")),
								blockConfig.getString("item.name"),
								blockConfig.getInt("item.custom-model-data"),
								Instrument.valueOf(blockConfig.getString("noteblock.instrument")),
								new Note(blockConfig.getInt("noteblock.note")),
								blockConfig.getBoolean("noteblock.is-powered"),
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
								(float) blockConfig.getDouble("sounds.step.volume"),
								blockConfig.getBoolean("craft.show-in-crafts-menu"),
								null
						);
						if (ingredientMap != null) {
							List<String> customBlockShapedRecipe = blockConfig.getStringList("craft.shaped-recipe");
							ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), customBlock.getNamespacedKey()), customBlock.getItemStack());
							shapedRecipe.setGroup(Main.getInstance().getName().toLowerCase(Locale.ROOT) + blockConfig.getString("craft.group"));
							shapedRecipe.shape(customBlockShapedRecipe.get(0), customBlockShapedRecipe.get(1), customBlockShapedRecipe.get(2));
							for (Character character : ingredientMap.keySet()) {
								shapedRecipe.setIngredient(character, ingredientMap.get(character));
							}
							Bukkit.addRecipe(shapedRecipe);
							customBlock.setShapedRecipe(shapedRecipe);
						}
						customBlocks.put(customBlock.getNamespacedKey(), customBlock);
					});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
