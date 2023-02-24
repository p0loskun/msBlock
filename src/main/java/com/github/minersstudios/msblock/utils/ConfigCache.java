package com.github.minersstudios.msblock.utils;

import com.github.minersstudios.msblock.MSBlock;
import com.github.minersstudios.msblock.customblock.CustomBlockData;
import com.github.minersstudios.msblock.customblock.NoteBlockData;
import com.github.minersstudios.mscore.collections.DualMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public final class ConfigCache {
	public final DualMap<String, Integer, CustomBlockData> customBlocks = new DualMap<>();
	public final Map<Integer, CustomBlockData> cachedNoteBlockData = new HashMap<>();
	public final List<Recipe> customBlockRecipes = new ArrayList<>();

	public final @NotNull String
			woodSoundPlace,
			woodSoundBreak,
			woodSoundStep,
			woodSoundHit;

	public ConfigCache() {
		File configFile = new File(MSBlock.getInstance().getPluginFolder(), "config.yml");
		YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

		this.woodSoundPlace = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.place"), "wood-sound.place is null");
		this.woodSoundBreak = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.break"));
		this.woodSoundStep = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.step"));
		this.woodSoundHit = Objects.requireNonNull(yamlConfiguration.getString("wood-sound.hit"));
	}

	public void loadBlocks() {
		try (Stream<Path> paths = Files.walk(Paths.get(MSBlock.getInstance().getPluginFolder() + "/blocks"))) {
			paths
			.filter(Files::isRegularFile)
			.map(Path::toFile)
			.forEach(file -> {
				if (file.getName().equals("example.yml")) return;
				CustomBlockData customBlockData = CustomBlockData.fromConfig(file, YamlConfiguration.loadConfiguration(file));
				this.customBlocks.put(customBlockData.getNamespacedKey().getKey(), customBlockData.getItemCustomModelData(), customBlockData);

				NoteBlockData noteBlockData = customBlockData.getNoteBlockData();
				if (noteBlockData == null) {
					Map<?, NoteBlockData> map =
							customBlockData.getBlockFaceMap() == null
							? customBlockData.getBlockAxisMap()
							: customBlockData.getBlockFaceMap();

					if (map != null) {
						for (NoteBlockData data : map.values()) {
							this.cachedNoteBlockData.put(data.toInt(), customBlockData);
						}
					}
				} else {
					this.cachedNoteBlockData.put(noteBlockData.toInt(), customBlockData);
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
