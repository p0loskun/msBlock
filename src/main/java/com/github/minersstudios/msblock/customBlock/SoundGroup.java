package com.github.minersstudios.msblock.customBlock;

import com.github.minersstudios.msblock.Main;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("unused")
public class SoundGroup {
	@Nullable private String placeSound;
	private float placeSoundPitch;
	private float placeSoundVolume;
	@Nullable private String breakSound;
	private float breakSoundPitch;
	private float breakSoundVolume;
	@Nullable private String hitSound;
	private float hitSoundPitch;
	private float hitSoundVolume;
	@Nullable private String stepSound;
	private float stepSoundPitch;
	private float stepSoundVolume;

	public SoundGroup(
			@Nullable String placeSound,
			float placeSoundPitch,
			float placeSoundVolume,
			@Nullable String breakSound,
			float breakSoundPitch,
			float breakSoundVolume,
			@Nullable String hitSound,
			float hitSoundPitch,
			float hitSoundVolume,
			@Nullable String stepSound,
			float stepSoundPitch,
			float stepSoundVolume
	) {
		this.placeSound = placeSound;
		this.placeSoundPitch = placeSoundPitch;
		this.placeSoundVolume = placeSoundVolume;
		this.breakSound = breakSound;
		this.breakSoundPitch = breakSoundPitch;
		this.breakSoundVolume = breakSoundVolume;
		this.hitSound = hitSound;
		this.hitSoundPitch = hitSoundPitch;
		this.hitSoundVolume = hitSoundVolume;
		this.stepSound = stepSound;
		this.stepSoundPitch = stepSoundPitch;
		this.stepSoundVolume = stepSoundVolume;
	}

	public void setPlaceSound(@Nullable String placeSound) {
		this.placeSound = placeSound;
	}

	@Nullable
	public String getPlaceSound() {
		return this.placeSound;
	}

	public void setPlaceSoundPitch(float placeSoundPitch) {
		this.placeSoundPitch = placeSoundPitch;
	}

	public float getPlaceSoundPitch() {
		return this.placeSoundPitch;
	}

	public void setPlaceSoundVolume(float placeSoundVolume) {
		this.placeSoundVolume = placeSoundVolume;
	}

	public float getPlaceSoundVolume() {
		return this.placeSoundVolume;
	}

	public void setBreakSound(@Nullable String breakSound) {
		this.breakSound = breakSound;
	}

	@Nullable
	public String getBreakSound() {
		return this.breakSound;
	}

	public void setBreakSoundPitch(float breakSoundPitch) {
		this.breakSoundPitch = breakSoundPitch;
	}

	public float getBreakSoundPitch() {
		return this.breakSoundPitch;
	}

	public void setBreakSoundVolume(float breakSoundVolume) {
		this.breakSoundVolume = breakSoundVolume;
	}

	public float getBreakSoundVolume() {
		return this.breakSoundVolume;
	}

	public void setHitSound(@Nullable String hitSound) {
		this.hitSound = hitSound;
	}

	@Nullable
	public String getHitSound() {
		return this.hitSound;
	}

	public float getHitSoundPitch() {
		return this.hitSoundPitch;
	}

	public void setHitSoundPitch(float hitSoundPitch) {
		this.hitSoundPitch = hitSoundPitch;
	}

	public float getHitSoundVolume() {
		return this.hitSoundVolume;
	}

	public void setHitSoundVolume(float hitSoundVolume) {
		this.hitSoundVolume = hitSoundVolume;
	}

	public void setStepSound(@Nullable String stepSound) {
		this.stepSound = stepSound;
	}

	@Nullable
	public String getStepSound() {
		return this.stepSound;
	}

	public void setStepSoundPitch(float stepSoundPitch) {
		this.stepSoundPitch = stepSoundPitch;
	}

	public float getStepSoundPitch() {
		return this.stepSoundPitch;
	}

	public void setStepSoundVolume(float stepSoundVolume) {
		this.stepSoundVolume = stepSoundVolume;
	}

	public float getStepSoundVolume() {
		return this.stepSoundVolume;
	}

	public void playPlaceSound(@Nonnull Location location) {
		if (this.placeSound == null) return;
		if (this.placeSound.equalsIgnoreCase("block.wood.place")) {
			location.getWorld().playSound(location, Main.getConfigCache().woodSoundPlace, this.placeSoundVolume, this.placeSoundPitch);
		} else {
			location.getWorld().playSound(location, this.placeSound, this.placeSoundVolume, this.placeSoundPitch);
		}
	}

	public void playBreakSound(@Nonnull Location location) {
		if (this.breakSound == null) return;
		if (this.breakSound.equalsIgnoreCase("block.wood.break")) {
			location.getWorld().playSound(location, Main.getConfigCache().woodSoundBreak, this.breakSoundVolume, this.breakSoundPitch);
		} else {
			location.getWorld().playSound(location, this.breakSound, this.breakSoundVolume, this.breakSoundPitch);
		}
	}

	public void playHitSound(@Nonnull Location location) {
		if (this.hitSound == null) return;
		if (this.hitSound.equalsIgnoreCase("block.wood.hit")) {
			location.getWorld().playSound(location, Main.getConfigCache().woodSoundHit, this.hitSoundVolume, this.hitSoundPitch);
		} else {
			location.getWorld().playSound(location, this.hitSound, this.hitSoundVolume, this.hitSoundPitch);
		}
	}

	public void playStepSound(@Nonnull Location location) {
		if (this.stepSound == null) return;
		if (this.stepSound.equalsIgnoreCase("block.wood.step")) {
			location.getWorld().playSound(location, Main.getConfigCache().woodSoundStep, this.stepSoundVolume, this.stepSoundPitch);
		} else {
			location.getWorld().playSound(location, this.stepSound, this.stepSoundVolume, this.stepSoundPitch);
		}
	}
}
