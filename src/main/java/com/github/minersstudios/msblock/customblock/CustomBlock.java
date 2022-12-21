package com.github.minersstudios.msblock.customblock;

import com.github.minersstudios.msblock.Main;
import com.github.minersstudios.msblock.utils.BlockUtils;
import com.github.minersstudios.msblock.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class CustomBlock {
	public static final CustomBlock DEFAULT = new CustomBlock(
			//<editor-fold desc="Default noteblock params">
			new NamespacedKey(Main.getInstance(), "default"),
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

	public CustomBlock(
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

	public static @NotNull CustomBlock getCustomBlock(@NotNull Instrument instrument, @NotNull Note note, boolean powered) {
		NoteBlockData noteBlockData = new NoteBlockData(instrument, note, powered);
		for (CustomBlock customBlock : Main.getConfigCache().customBlocks.values()) {
			if (customBlock.noteBlockData == null) {
				if (customBlock.blockFaceMap != null) {
					for (NoteBlockData data : customBlock.blockFaceMap.values()) {
						if (noteBlockData.isSimilar(data)) {
							customBlock.noteBlockData = noteBlockData;
						}
					}
				} else if (customBlock.blockAxisMap != null) {
					for (NoteBlockData data : customBlock.blockAxisMap.values()) {
						if (noteBlockData.isSimilar(data)) {
							customBlock.noteBlockData = noteBlockData;
						}
					}
				}
			}
			if (
					customBlock.noteBlockData != null
					&& noteBlockData.isSimilar(customBlock.noteBlockData)
			) return customBlock;
		}
		return DEFAULT;
	}

	public static @NotNull CustomBlock getCustomBlock(int itemCustomModelData) {
		for (CustomBlock customBlock : Main.getConfigCache().customBlocks.values()) {
			if (customBlock.itemCustomModelData == itemCustomModelData) {
				return customBlock;
			}
		}
		return DEFAULT;
	}

	public @Nullable Set<Axis> getAxes() {
		if (this.blockAxisMap == null) return null;
		return this.blockAxisMap.keySet();
	}

	public @Nullable Set<BlockFace> getFaces() {
		if (this.blockFaceMap == null) return null;
		return this.blockFaceMap.keySet();
	}

	public void setCustomBlock(@NotNull Block block, @NotNull Player player, @NotNull EquipmentSlot hand) {
		this.setCustomBlock(block, player, hand, null, null);
	}

	public void setCustomBlock(@NotNull Block block, @NotNull Player player, @NotNull EquipmentSlot hand, @Nullable BlockFace blockFace, @Nullable Axis axis) {
		if (blockFace != null && this.blockFaceMap != null) {
			this.noteBlockData = this.blockFaceMap.get(blockFace);
		} else if (axis != null && this.blockAxisMap != null) {
			this.noteBlockData = this.blockAxisMap.get(axis);
		}
		if (this.noteBlockData == null) return;
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
			block.setType(Material.NOTE_BLOCK);
			NoteBlock noteBlock = this.noteBlockData.craftNoteBlock(block.getBlockData());
			block.setBlockData(noteBlock);

			this.soundGroup.playPlaceSound(block.getLocation().toCenterLocation());
			Main.getCoreProtectAPI().logPlacement(player.getName(), block.getLocation(), Material.NOTE_BLOCK, noteBlock);
			BlockUtils.removeBlock(block.getLocation());
			PlayerUtils.swingHand(player, hand);

			if (player.getGameMode() != GameMode.SURVIVAL) return;
			ItemStack itemInHand = player.getInventory().getItem(hand);
			itemInHand.setAmount(itemInHand.getAmount() - 1);
		});
	}

	public void breakCustomBlock(@NotNull Block block, @NotNull Player player) {
		Location blockLocation = block.getLocation();
		World world = block.getWorld();
		ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

		if (BlockUtils.getEntryByBlock(block) == null) return;
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> BlockUtils.cancelAllTasksWithThisBlock(block));
		this.soundGroup.playBreakSound(block.getLocation().toCenterLocation());
		world.spawnParticle(Particle.BLOCK_CRACK, blockLocation.clone().add(0.5d, 0.25d, 0.5d), 80, 0.35d, 0.35d, 0.35d, block.getBlockData());
		Main.getCoreProtectAPI().logRemoval(player.getName(), blockLocation, Material.NOTE_BLOCK, block.getBlockData());
		block.setType(Material.AIR);

		if (
				(!this.forceTool || this.toolType == ToolType.getToolType(itemInMainHand))
				&& this != CustomBlock.DEFAULT
		) {
			if (this.dropsDefaultItem) {
				world.dropItemNaturally(blockLocation, this.craftItemStack());
			}
			if (this.expToDrop != 0) {
				world.spawn(blockLocation, ExperienceOrb.class).setExperience(this.expToDrop);
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

	public enum PlacingType {
		BY_BLOCK_FACE, BY_EYE_POSITION
	}
}
