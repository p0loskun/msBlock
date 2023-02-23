package com.github.minersstudios.msblock.utils;

import com.github.minersstudios.msblock.MSBlock;
import com.github.minersstudios.msblock.customblock.CustomBlockData;
import com.github.minersstudios.mscore.collections.DualMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class ConfigCache {
	public final DualMap<String, Integer, CustomBlockData> customBlocks = new DualMap<>();
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

		this.loadBlocks();
	}

	private void loadBlocks() {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(MSBlock.getInstance().getPluginFolder() + "/blocks"), "*.yml")) {
			stream.forEach(path -> {
				File file = path.toFile();
				if (file.getName().equals("example.yml")) return;
				CustomBlockData customBlockData = CustomBlockData.fromConfig(file, YamlConfiguration.loadConfiguration(file));
				this.customBlocks.put(customBlockData.getNamespacedKey().getKey(), customBlockData.getItemCustomModelData(), customBlockData);
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
