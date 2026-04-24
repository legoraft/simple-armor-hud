package com.armorhud.mixin.client;

import com.armorhud.armor.ArmorAccessor;
import com.armorhud.armorHud;
import com.armorhud.config.config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Gui.class)
public abstract class armorHudMixin {

	@Shadow @Final private Minecraft minecraft;
	@Shadow protected abstract LivingEntity getPlayerVehicleWithHealth();
	@Unique int armorHeight;
	@Unique boolean initialized;

	@Unique private final int FOODBAR_X = 91;
	@Unique private final int HEALTHBAR_X = -10;
	@Unique private final int HOTBAR_LEFT_X = -83;
	@Unique private final int HOTBAR_RIGHT_X = 165;

	@Inject(at = @At("TAIL"), method = "extractItemHotbar")
	private void renderHud(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		if( !config.ARMOR_HUD ) { return; }

		assert minecraft.player != null;

        if ( !initialized ) {
            armorHud.getArmorAccessor().initialize(minecraft.player);
            initialized = true;
        }

		switch ( config.position.name() ) {
			case "FOODBAR":
				renderArmor(graphics, FOODBAR_X);
				break;
			case "HEALTHBAR":
				renderArmor(graphics, HEALTHBAR_X);
				break;
			case "HOTBAR_LEFT":
				renderArmor(graphics, HOTBAR_LEFT_X);
				break;
			case "HOTBAR_RIGHT":
				if ( minecraft.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR ) {
					renderArmor(graphics, HOTBAR_RIGHT_X + 25);
				} else {
					renderArmor(graphics, HOTBAR_RIGHT_X);
				}
				break;
		}

		moveArmor(graphics);
	}

	@Unique
	private void renderArmor(GuiGraphicsExtractor context, int startXPosition) {
		int scaledWidth = context.guiWidth();

		assert minecraft.player != null;
		ArmorAccessor armorAccessor = armorHud.getArmorAccessor();
		List<ItemStack> armorPieces = armorAccessor.getArmorPieces(minecraft.player);

//		EquipmentSlot[] slots = EquipmentSlot.values();

		final int hungerWidth = 14; // Magic number to center 4 armor pieces
		final int armorWidth = 15;

		int emptyArmorSlots = 0;
		if ( config.TRIM_EMPTY_SLOTS ) {
			for ( ItemStack stack : armorPieces ) {
				if ( stack.isEmpty() ) {
					emptyArmorSlots++;
				}
			}
		}
	/*
		if (config.TRIM_EMPTY_SLOTS) {
			for (EquipmentSlot slot : slots) {
				if (slot.isArmor()) {
					ItemStack stack = armorAccessor.getArmorPiece(minecraft.player, slot);
					if (stack.isEmpty()) {
						emptyArmorSlots++;
					}
				}
			}
		}

	 */

		float hungerX = scaledWidth / 2f + startXPosition;
		float x = hungerX + hungerWidth - (7 * emptyArmorSlots) + 2 - (armorWidth * 2);

		if (config.RTL) {
			if ( config.TRIM_EMPTY_SLOTS ) { x -= (float) (( (float) armorWidth / 2 ) + 0.5); }
			x += armorWidth;
			armorPieces = armorPieces.reversed();

			for ( ItemStack armor : armorPieces ) {
				if (config.TRIM_EMPTY_SLOTS && armor.isEmpty()) continue;
				x -= armorWidth;

				renderArmorPiece(context, x, armorHeight, minecraft.player, armor);
			}

			/*for ( int i = slots.length - 1; i > 0; i-- ) {
				EquipmentSlot slot = slots[i];
				if (!slot.isArmor()) continue;
				ItemStack armor = armorAccessor.getArmorPiece(minecraft.player, slot);

				if (config.TRIM_EMPTY_SLOTS && armor.isEmpty()) continue;
				x -= armorWidth;

				renderArmorPiece(context, x, armorHeight, minecraft.player, armor);
			}

			 */
		} else {
			if ( config.TRIM_EMPTY_SLOTS ) { x += ( (float) hungerWidth / 2 ); }
			for ( ItemStack armor : armorPieces ) {
				if (config.TRIM_EMPTY_SLOTS && armor.isEmpty()) continue;
				x -= armorWidth;

				renderArmorPiece(context, x, armorHeight, minecraft.player, armor);
			}

            /*for ( EquipmentSlot slot : slots ) {
				if ( !slot.isArmor() ) continue;
				ItemStack armor = armorAccessor.getArmorPiece(minecraft.player, slot);

				if ( config.TRIM_EMPTY_SLOTS && armor.isEmpty() ) continue;
				x -= armorWidth;

				renderArmorPiece(context, x, armorHeight, minecraft.player, armor);
            }

             */
		}
	}

	@Unique
	private void moveArmor(GuiGraphicsExtractor context) {
		if ( config.position != config.Position.FOODBAR && config.position != config.Position.HEALTHBAR ) {
			int scaledHeight = context.guiHeight();

			switch ( config.position.name() ) {
				case "HOTBAR_LEFT", "HOTBAR_RIGHT":
					armorHeight = scaledHeight - 19;
					break;
            }

			return;
		}

		int scaledHeight = context.guiHeight();
		int healthDisplacement = 0;
		assert minecraft.player != null;

//		Moves armorhud up if player uses double hotbar
		armorHeight = scaledHeight - (config.DOUBLE_HOTBAR ? 76 : 55);

/*  	TODO: fix visual bug where remaining hearts available (>20hp) get removed later than armor hud -Dino
		Skips unnecessary checks when Above_Health_Bar is on, not a fan of the extra if statement, but it works -Dino
		Note: setting gets turned off above 9 rows of hearts, since the hud will fly off the screen at some point -Dino			*/

		if ( config.position == config.Position.HEALTHBAR ) {
			if ( minecraft.player.getMaxHealth() + minecraft.player.getMaxAbsorption() < 180 ) {
/* 			Displacement calculation extracted for clarity. -Dino
 			Calc breaks above 90 hearts since hearts don't get condensed further, so it overshoots down -Dino			*/
				int playerHealthRows = (int) Math.ceil((minecraft.player.getMaxHealth() + minecraft.player.getMaxAbsorption()) / 20);
				healthDisplacement = (10 * playerHealthRows) - ((playerHealthRows>2) ? (playerHealthRows -2) * (playerHealthRows -1) : 0);

//			Moves armorhud up depending on how much health you have, along with negative displacement from higher heart counts -Dino
				armorHeight -= healthDisplacement;
			}

			if ( minecraft.player.isCreative() ) {
				armorHeight += 16 + healthDisplacement;
			} else if ( config.DISABLE_ARMOR_BAR ) {
				armorHeight += 10;
			}
		} else {
			if ( minecraft.player.getAirSupply() < minecraft.player.getMaxAirSupply() || minecraft.player.isUnderWater() && !minecraft.player.isCreative() ) {
				armorHeight -= 10;
			}

//			Moves armorhud down if player is in creative
			if ( minecraft.player.isCreative() ) {
				armorHeight += 16;
			}

//			Moves armorhud up if player is on mount, like horse
			if ( minecraft.player.isPassenger() && getPlayerVehicleWithHealth() != null ) {
				moveRiddenEntity();
			}
		}
	}

	@Unique
	private void moveRiddenEntity() {
		assert minecraft.player != null;

//		Check if entity player is riding is alive, like a horse
		if ( getPlayerVehicleWithHealth().isAlive() ) {

//		If horse health is 21, it still displays 10 hearts
			if ( getPlayerVehicleWithHealth().getMaxHealth() > 21 ) {
				if ( config.BETTER_MOUNT_HUD && !minecraft.player.isCreative() ) {
					armorHeight -= 20;
				} else {
					armorHeight -= (minecraft.player.isCreative() ? 26 : 10);
				}
			}

//		Armor hud only has to be moved up if better mount hud is enabled or player is in creative
			else {
				if ( config.BETTER_MOUNT_HUD && !minecraft.player.isCreative() ) {
					armorHeight -= 10;
				} else if ( minecraft.player.isCreative() ) {
					armorHeight -= 16;
				}
			}
		}
	}

	// Pretty much the same as renderHotbarItem but with x and y as float parameters.
	@Unique
	private void renderArmorPiece(GuiGraphicsExtractor context, float x, float y, Player player, ItemStack stack) {
		if ( stack.isEmpty() ) return;

		context.pose().pushMatrix();
		context.pose().translate(x, y);

		context.item(player, stack, 0, 0, 1);

		context.itemDecorations(this.minecraft.font, stack, 0,0);
		context.pose().popMatrix();
	}

}
