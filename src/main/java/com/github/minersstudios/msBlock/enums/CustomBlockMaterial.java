package com.github.minersstudios.msBlock.enums;

import com.github.minersstudios.msBlock.Main;
import com.github.minersstudios.msBlock.utils.BlockUtils;
import com.github.minersstudios.msBlock.utils.PlayerUtils;
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
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum CustomBlockMaterial {
	//<editor-fold desc="CustomBlockMaterials">
	DEFAULT(Instrument.BIT, new Note(0), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Нотный блок", 13),

	VERTICAL_ACACIA_PLANKS(Instrument.BANJO, new Note(0), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Вертикальные акациевые доски", 13000),
	VERTICAL_BIRCH_PLANKS(Instrument.BANJO, new Note(1), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Вертикальные берёзовые доски", 13001),
	VERTICAL_CRIMSON_PLANKS(Instrument.BANJO, new Note(2), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Вертикальные багровые доски", 13002),
	VERTICAL_DARK_OAK_PLANKS(Instrument.BANJO, new Note(3), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Вертикальные доски из тёмной древесины", 13003),
	VERTICAL_JUNGLE_PLANKS(Instrument.BANJO, new Note(4), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Вертикальные тропические доски", 13004),
	VERTICAL_OAK_PLANKS(Instrument.BANJO, new Note(5), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Вертикальные дубовые доски", 13005),
	VERTICAL_SPRUCE_PLANKS(Instrument.BANJO, new Note(6), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Вертикальные еловые доски", 13006),
	VERTICAL_WARPED_PLANKS(Instrument.BANJO, new Note(7), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Вертикальные искажённые доски", 13007),
	VERTICAL_MANGROVE_PLANKS(Instrument.BANJO, new Note(8), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Вертикальные мангровые доски", 13008),

	FRAMED_ACACIA_PLANKS(Instrument.BANJO, new Note(9), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Обрамленные акациевые доски", 13009),
	FRAMED_BIRCH_PLANKS(Instrument.BANJO, new Note(10), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Обрамленные берёзовые доски", 13010),
	FRAMED_CRIMSON_PLANKS(Instrument.BANJO, new Note(11), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Обрамленные багровые доски", 13011),
	FRAMED_DARK_OAK_PLANKS(Instrument.BANJO, new Note(12), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Обрамленные доски из тёмной древесины", 13012),
	FRAMED_JUNGLE_PLANKS(Instrument.BANJO, new Note(13), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Обрамленные тропические доски", 13013),
	FRAMED_OAK_PLANKS(Instrument.BANJO, new Note(14), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Обрамленные дубовые доски", 13014),
	FRAMED_SPRUCE_PLANKS(Instrument.BANJO, new Note(15), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Обрамленные еловые доски", 13015),
	FRAMED_WARPED_PLANKS(Instrument.BANJO, new Note(16), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Обрамленные искажённые доски", 13016),
	FRAMED_MANGROVE_PLANKS(Instrument.BANJO, new Note(17), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Обрамленные мангровые доски", 13017),

	CARVED_ACACIA_PLANKS(Instrument.BANJO, new Note(18), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Резные акациевые доски", 13018),
	CARVED_BIRCH_PLANKS(Instrument.BANJO, new Note(19), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Резные берёзовые доски", 13019),
	CARVED_CRIMSON_PLANKS(Instrument.BANJO, new Note(20), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Резные багровые доски", 13020),
	CARVED_DARK_OAK_PLANKS(Instrument.BANJO, new Note(21), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Резные доски из тёмной древесины", 13021),
	CARVED_JUNGLE_PLANKS(Instrument.BANJO, new Note(22), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Резные тропические доски", 13022),
	CARVED_OAK_PLANKS(Instrument.BANJO, new Note(23), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Резные дубовые доски", 13023),
	CARVED_SPRUCE_PLANKS(Instrument.BANJO, new Note(24), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Резные еловые доски", 13024),
	CARVED_WARPED_PLANKS(Instrument.BIT, new Note(1), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Резные искажённые доски", 13025),
	CARVED_MANGROVE_PLANKS(Instrument.BIT, new Note(2), false, Sound.BLOCK_WOOD_PLACE, Sound.BLOCK_WOOD_BREAK, Sound.BLOCK_WOOD_HIT, Sound.BLOCK_WOOD_STEP, 11.0f, ToolType.AXE, false, 0, "Резные мангровые доски", 13026),

	HACPAL_BLOCK(Instrument.BIT, new Note(3), false, Sound.ENTITY_PUFFER_FISH_HURT, Sound.ENTITY_PUFFER_FISH_DEATH, Sound.ENTITY_LLAMA_SPIT, Sound.ENTITY_LLAMA_SPIT, 10.0f, ToolType.SHEARS, false, 0, "Насрал-блок", 13027),
	SVINSTER_BLOCK(Instrument.BIT, new Note(4), false, Sound.ENTITY_PIG_AMBIENT, Sound.ENTITY_PIG_DEATH, Sound.ENTITY_PIG_HURT, Sound.ENTITY_PIG_AMBIENT, 10.0f, ToolType.SWORD, false, 0, "Свинстер-блок", 13028),
	//</editor-fold>
	;

	private static final CustomBlockMaterial[] customBlockMaterials = values();
	private final Instrument instrument;
	private final Note note;
	private final boolean powered;
	private final Sound placeSound, breakSound, hitSound, stepSound;
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
			@Nullable Sound placeSound,
			@Nullable Sound breakSound,
			@Nullable Sound hitSound,
			@Nullable Sound stepSound,
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
		this.placeSound = placeSound;
		this.breakSound = breakSound;
		this.stepSound = stepSound;
		this.hitSound = hitSound;
		this.digSpeed = digSpeed;
		this.toolType = toolType;
		this.forceTool = forceTool;
	}

	/**
	 * @param player              player who breaks the custom block
	 * @param customBlockMaterial CustomBlockMaterial of custom block
	 * @return dig speed float
	 */
	public static float getDigSpeed(@Nonnull Player player, @Nonnull CustomBlockMaterial customBlockMaterial) {
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

	/**
	 * @return ItemStack of custom block item
	 */
	@Nonnull
	public ItemStack getItemStack() {
		ItemStack itemStack = new ItemStack(Material.PAPER);
		ItemMeta itemMeta = itemStack.getItemMeta();
		assert itemMeta != null;
		itemMeta.setCustomModelData(this.itemCustomModelData);
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
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	/**
	 * @param note       noteblock note
	 * @param instrument noteblock instrument
	 * @param powered    noteblock powered
	 * @return CustomBlockMaterial by noteblock
	 */
	@Nonnull
	public static CustomBlockMaterial getCustomBlockMaterial(@Nonnull Note note, @Nonnull Instrument instrument, boolean powered) {
		for (CustomBlockMaterial customBlockMaterial : customBlockMaterials)
			if (
					customBlockMaterial.instrument == instrument
					&& customBlockMaterial.note.equals(note)
					//&& customBlockMaterial.isPowered() == powered       removed to BlockPhysicsEvent NoteBlock powered state bug fix
			) return customBlockMaterial;
		return DEFAULT;
	}

	/**
	 * @param itemCustomModelData CustomModelData of item in main hand
	 * @return Custom block material by item in main hand
	 */
	@Nonnull
	public static CustomBlockMaterial getCustomBlockMaterial(int itemCustomModelData) {
		for (CustomBlockMaterial customBlockMaterial : customBlockMaterials) {
			if (customBlockMaterial.itemCustomModelData == itemCustomModelData) {
				return customBlockMaterial;
			}
		}
		return DEFAULT;
	}

	/**
	 * Places a custom block
	 *
	 * @param block  block on which the custom block will be placed
	 * @param player player who placed the block
	 */
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

	/**
	 * Breaks custom block
	 *
	 * @param block  custom block
	 * @param player player who broke the block
	 */
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
				&& this != CustomBlockMaterial.DEFAULT
		) {
			world.dropItemNaturally(blockLocation, this.getItemStack());
			if (this.getExpToDrop() != 0) {
				world.spawn(blockLocation, ExperienceOrb.class).setExperience(this.getExpToDrop());
			}
		} else if (this == CustomBlockMaterial.DEFAULT) {
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

	/**
	 * Plays custom block place sound
	 *
	 * @param block block in which the sound will be played
	 */
	public void playPlaceSound(@Nonnull Block block) {
		if (this.placeSound == null) return;
		if (this.placeSound == Sound.BLOCK_WOOD_PLACE) {
			block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.place", 1.0f, 1.0f);
		} else {
			block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), this.placeSound, 1.0f, 0.9f);
		}
	}

	/**
	 * Plays custom block break sound
	 *
	 * @param block block in which the sound will be played
	 */
	public void playBreakSound(@Nonnull Block block) {
		if (this.breakSound == null) return;
		if (this.breakSound == Sound.BLOCK_WOOD_BREAK) {
			block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.break", 1.0f, 1.0f);
		} else {
			block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), this.breakSound, 1.0f, 0.9f);
		}
	}

	/**
	 * Plays custom block hit sound
	 *
	 * @param block block in which the sound will be played
	 */
	public void playHitSound(@Nonnull Block block) {
		if (this.hitSound == null) return;
		if (this.hitSound == Sound.BLOCK_WOOD_HIT) {
			block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.hit", 0.5f, 0.5f);
		} else {
			block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), this.hitSound, 0.6f, 0.9f);
		}
	}

	/**
	 * Plays custom block step sound
	 *
	 * @param block block in which the sound will be played
	 */
	public void playStepSound(@Nonnull Block block) {
		if (this.stepSound == null) return;
		if (this.stepSound == Sound.BLOCK_WOOD_STEP) {
			block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), "custom.block.wood.step", 0.3f, 0.9f);
		} else {
			block.getWorld().playSound(block.getLocation().clone().add(0.5d, 0.5d, 0.5d), this.stepSound, 0.6f, 0.9f);
		}
	}

	public boolean isWooden() {
		return this.toolType == ToolType.AXE;
	}

	public boolean isForceTool() {
		return forceTool;
	}

	public int getExpToDrop() {
		return expToDrop;
	}
}
