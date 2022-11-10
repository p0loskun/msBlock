package com.github.minersstudios.msblock.enums;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.msblock.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomBlock {
	public static final CustomBlock DEFAULT = new CustomBlock(
			"default",
			11.0f,
			0,
			true,
			ToolType.AXE,
			false,
			Material.NOTE_BLOCK,
			null,
			0,
			Instrument.BIT,
			new Note(0),
			false,
			"block.wood.place",
			1.0f,
			1.0f,
			"block.wood.break",
			1.0f,
			1.0f,
			"block.wood.hit",
			0.5f,
			0.5f,
			"block.wood.step",
			0.9f,
			0.3f
	);
	@Nonnull private final String namespacedKey;
	private final float digSpeed;
	private final int expToDrop;
	private final boolean dropsDefaultItem;
	@Nonnull private final ToolType toolType;
	private final boolean forceTool;
	@Nonnull private final Material itemMaterial;
	@Nullable private final String itemName;
	private final int itemCustomModelData;
	@Nonnull private final Instrument instrument;
	@Nonnull private final Note note;
	private final boolean powered;
	@Nullable private final String placeSound;
	private final float placeSoundPitch;
	private final float placeSoundVolume;
	@Nullable private final String breakSound;
	private final float breakSoundPitch;
	private final float breakSoundVolume;
	@Nullable private final String hitSound;
	private final float hitSoundPitch;
	private final float hitSoundVolume;
	@Nullable private final String stepSound;
	private final float stepSoundPitch;
	private final float stepSoundVolume;
	private final boolean showInCraftsMenu;
	@Nullable private ShapedRecipe shapedRecipe;

	public CustomBlock(
			@Nonnull String namespacedKey,
			float digSpeed,
			int expToDrop,
			boolean dropsDefaultItem,
			@Nonnull ToolType toolType,
			boolean forceTool,
			@Nonnull Material itemMaterial,
			@Nullable String itemName,
			int itemCustomModelData,
			@Nonnull Instrument instrument,
			@Nonnull Note note,
			boolean powered,
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
			float stepSoundVolume,
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
		this.instrument = instrument;
		this.note = note;
		this.powered = powered;
		this.placeSound = placeSound;
		this.placeSoundPitch = placeSoundPitch;
		this.placeSoundVolume = placeSoundVolume;
		this.breakSound = breakSound;
		this.breakSoundPitch = breakSoundPitch;
		this.breakSoundVolume = breakSoundVolume;
		this.stepSound = stepSound;
		this.stepSoundPitch = stepSoundPitch;
		this.stepSoundVolume = stepSoundVolume;
		this.hitSound = hitSound;
		this.hitSoundPitch = hitSoundPitch;
		this.hitSoundVolume = hitSoundVolume;
		this.showInCraftsMenu = showInCraftsMenu;
		this.shapedRecipe = shapedRecipe;
	}

	public CustomBlock(
			@Nonnull String namespacedKey,
			float digSpeed,
			int expToDrop,
			boolean dropsDefaultItem,
			@Nonnull ToolType toolType,
			boolean forceTool,
			@Nonnull Material itemMaterial,
			@Nullable String itemName,
			int itemCustomModelData,
			@Nonnull Instrument instrument,
			@Nonnull Note note,
			boolean powered,
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
		this(namespacedKey, digSpeed, expToDrop, dropsDefaultItem, toolType, forceTool, itemMaterial, itemName, itemCustomModelData, instrument, note, powered, placeSound, placeSoundPitch, placeSoundVolume, breakSound, breakSoundPitch, breakSoundVolume, hitSound, hitSoundPitch, hitSoundVolume, stepSound, stepSoundPitch, stepSoundVolume, false, null);
	}

	@Nonnull
	public String getNamespacedKey() {
		return namespacedKey;
	}

	public static float getDigSpeed(@Nonnull Player player, @Nonnull CustomBlock customBlockMaterial) {
		float base = 1.0f;
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

		if (customBlockMaterial.toolType == ToolType.getToolType(itemInMainHand)) {
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

	public int getExpToDrop() {
		return expToDrop;
	}

	public boolean isForceTool() {
		return forceTool;
	}

	public boolean isShowInCraftsMenu() {
		return showInCraftsMenu;
	}

	@Nullable public ShapedRecipe getShapedRecipe() {
		return shapedRecipe;
	}

	public void setShapedRecipe(@Nonnull ShapedRecipe shapedRecipe) {
		this.shapedRecipe = shapedRecipe;
	}

	@Nonnull
	public ItemStack getItemStack() {
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

	@Nonnull
	public static CustomBlock getCustomBlock(@Nonnull Note note, @Nonnull Instrument instrument, boolean powered) {
		for (CustomBlock customBlock : Main.getConfigCache().customBlocks.values())
			if (
					customBlock.instrument == instrument
					&& customBlock.note.equals(note)
					//&& customBlock.isPowered() == powered       removed to BlockPhysicsEvent NoteBlock powered state bug fix
			) return customBlock;
		return DEFAULT;
	}

	@Nonnull
	public static CustomBlock getCustomBlock(int itemCustomModelData) {
		for (CustomBlock customBlock : Main.getConfigCache().customBlocks.values()) {
			if (customBlock.itemCustomModelData == itemCustomModelData) {
				return customBlock;
			}
		}
		return DEFAULT;
	}

	public void setCustomBlock(@Nonnull Block block, @Nonnull Player player, @Nonnull EquipmentSlot hand) {
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
			block.setType(Material.NOTE_BLOCK);

			NoteBlock noteBlock = (NoteBlock) block.getBlockData();
			noteBlock.setInstrument(instrument);
			noteBlock.setNote(note);
			noteBlock.setPowered(powered);
			block.setBlockData(noteBlock);

			this.playPlaceSound(block);
			Main.getCoreProtectAPI().logPlacement(player.getName(), block.getLocation(), Material.NOTE_BLOCK, noteBlock);
			BlockUtils.removeBlock(block.getLocation());
			PlayerUtils.swingHand(player, hand);

			if (player.getGameMode() != GameMode.SURVIVAL) return;
			ItemStack itemInHand = player.getInventory().getItem(hand);
			itemInHand.setAmount(itemInHand.getAmount() - 1);
		});
	}

	public void breakCustomBlock(@Nonnull Block block, @Nonnull Player player) {
		Location blockLocation = block.getLocation();
		World world = block.getWorld();
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

		if (BlockUtils.getEntryByBlock(block) == null) return;
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> BlockUtils.cancelAllTasksWithThisBlock(block));
		this.playBreakSound(block);
		world.spawnParticle(Particle.BLOCK_CRACK, blockLocation.clone().add(0.5d, 0.25d, 0.5d), 80, 0.35d, 0.35d, 0.35d, block.getBlockData());
		Main.getCoreProtectAPI().logRemoval(player.getName(), blockLocation, Material.NOTE_BLOCK, block.getBlockData());
		block.setType(Material.AIR);

		if (
				(!this.isForceTool() || this.toolType == ToolType.getToolType(itemInMainHand))
				&& this != CustomBlock.DEFAULT
		) {
			if (this.dropsDefaultItem) {
				world.dropItemNaturally(blockLocation, this.getItemStack());
			}
			if (this.getExpToDrop() != 0) {
				world.spawn(blockLocation, ExperienceOrb.class).setExperience(this.getExpToDrop());
			}
		} else if (this == CustomBlock.DEFAULT) {
			world.dropItemNaturally(blockLocation, new ItemStack(Material.NOTE_BLOCK));
		}

		if (
				ToolType.getToolType(itemInMainHand) != ToolType.HAND
				&& itemInMainHand.getItemMeta() instanceof Damageable handItemDamageable
		) {
			handItemDamageable.setDamage(handItemDamageable.getDamage() + 1);
			itemInMainHand.setItemMeta(handItemDamageable);
			if (handItemDamageable.getDamage() > itemInMainHand.getType().getMaxDurability()) {
				itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
				world.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
			}
		}
	}

	public void playPlaceSound(@Nonnull Block block) {
		if (this.placeSound == null) return;
		if (this.placeSound.equalsIgnoreCase("block.wood.place")) {
			block.getWorld().playSound(block.getLocation().toCenterLocation(), Main.getConfigCache().wood_sound_place, this.placeSoundVolume, this.placeSoundPitch);
		} else {
			block.getWorld().playSound(block.getLocation().toCenterLocation(), this.placeSound, this.placeSoundVolume, this.placeSoundPitch);
		}
	}

	public void playBreakSound(@Nonnull Block block) {
		if (this.breakSound == null) return;
		if (this.breakSound.equalsIgnoreCase("block.wood.break")) {
			block.getWorld().playSound(block.getLocation().toCenterLocation(), Main.getConfigCache().wood_sound_break, this.breakSoundVolume, this.breakSoundPitch);
		} else {
			block.getWorld().playSound(block.getLocation().toCenterLocation(), this.breakSound, this.breakSoundVolume, this.breakSoundPitch);
		}
	}

	public void playHitSound(@Nonnull Block block) {
		if (this.hitSound == null) return;
		if (this.hitSound.equalsIgnoreCase("block.wood.hit")) {
			block.getWorld().playSound(block.getLocation().toCenterLocation(), Main.getConfigCache().wood_sound_hit, this.hitSoundVolume, this.hitSoundPitch);
		} else {
			block.getWorld().playSound(block.getLocation().toCenterLocation(), this.hitSound, this.hitSoundVolume, this.hitSoundPitch);
		}
	}

	public void playStepSound(@Nonnull Block block) {
		if (this.stepSound == null) return;
		if (this.stepSound.equalsIgnoreCase("block.wood.step")) {
			block.getWorld().playSound(block.getLocation().toCenterLocation(), Main.getConfigCache().wood_sound_step, this.stepSoundVolume, this.stepSoundPitch);
		} else {
			block.getWorld().playSound(block.getLocation().toCenterLocation(), this.stepSound, this.stepSoundVolume, this.stepSoundPitch);
		}
	}

	public boolean isWooden() {
		return this.toolType == ToolType.AXE;
	}
}
