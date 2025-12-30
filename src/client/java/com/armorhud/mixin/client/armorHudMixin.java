package com.armorhud.mixin.client;

import com.armorhud.armor.ArmorAccessor;
import com.armorhud.armorHud;
import com.armorhud.config.config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class armorHudMixin {

	@Shadow @Final private MinecraftClient client;
	@Shadow protected abstract LivingEntity getRiddenEntity();
	@Unique int armorHeight;
	@Unique boolean initialized;

	@Unique private final int FOODBAR_X = 91;
	@Unique private final int HEALTHBAR_X = -10;

	@Inject(at = @At("TAIL"), method = "renderHotbar")
	private void renderHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		if( !config.ARMOR_HUD ) { return; }

		assert client.player != null;

        if ( !initialized ) {
            armorHud.getArmorAccessor().initialize(client.player);
            initialized = true;
        }

		switch ( config.position.name() ) {
			case "FOODBAR":
				renderArmor(context, FOODBAR_X);
				break;
			case "HEALTHBAR":
				renderArmor(context, HEALTHBAR_X);
				break;
			default:
				renderArmor(context, FOODBAR_X);
				break;
		}

		moveArmor(context);
	}

	@Unique
	private void renderArmor(DrawContext context, int startXPosition) {
		int scaledWidth = context.getScaledWindowWidth();

		assert client.player != null;
		ArmorAccessor armorAccessor = armorHud.getArmorAccessor();

		final int hungerWidth = 14; // Magic number to center 4 armor pieces
		final int armorWidth = 15;

		float hungerX = scaledWidth / 2f + startXPosition;
		float x = hungerX + hungerWidth + 2;

		EquipmentSlot[] slots = EquipmentSlot.values();

		if (config.RTL) {
			for ( int i = slots.length - 1; i > 0; i-- ) {
				EquipmentSlot slot = slots[i];
				x -= armorWidth;

				if ( slot.isArmorSlot() ) {
					renderArmorPiece(context, x, armorHeight, client.player, armorAccessor.getArmorPiece(client.player, slot));
				}
			}
		} else {
            for ( EquipmentSlot slot : slots ) {
                x -= armorWidth;

                if ( slot.isArmorSlot() ) {
                    renderArmorPiece(context, x, armorHeight, client.player, armorAccessor.getArmorPiece(client.player, slot));
                }
            }
		}

/*		Putting this on the backburner for a little while to implement other things
		// counts empty slots to center condensed armor bar, don't like having to loop through the equip slots twice but idk how else to center this dynamically -dino
		int emptyArmorSlots = 0;
		if (config.TRIM_EMPTY_SLOTS) {
			for (int i = 2; i<6; i++) { // checks players armor slots only. probably makes stuff like trinkets incompatible -dino
				if(client.player.getEquippedStack(slots[i]).isEmpty()) {
					emptyArmorSlots++;
				}
			}
		}

		float x = hungerX + hungerWidth - (7 * emptyArmorSlots) + 2;
*/
	}

	// Pretty much the same as renderHotbarItem but with x and y as float parameters.
	@Unique
	private void renderArmorPiece(DrawContext context, float x, float y, PlayerEntity player, ItemStack stack) {
		if ( stack.isEmpty() ) return;

		context.getMatrices().pushMatrix();
		context.getMatrices().translate(x, y);

		context.drawItem(player, stack, 0, 0, 1);

		context.drawStackOverlay(this.client.textRenderer, stack, 0,0);
		context.getMatrices().popMatrix();
	}

	@Unique
	private void moveArmor(DrawContext context) {
		if ( config.position != config.Position.FOODBAR && config.position != config.Position.HEALTHBAR ) {
			return;
		}

		int scaledHeight = context.getScaledWindowHeight();
		int healthDisplacement = 0;
		assert client.player != null;

//		Moves armorhud up if player uses double hotbar
		armorHeight = scaledHeight - (config.DOUBLE_HOTBAR ? 76 : 55);

/*  	TODO: fix visual bug where remaining hearts available (>20hp) get removed later than armor hud -Dino
		Skips unnecessary checks when Above_Health_Bar is on, not a fan of the extra if statement, but it works -Dino
		Note: setting gets turned off above 9 rows of hearts, since the hud will fly off the screen at some point -Dino			*/

		if ( config.position == config.Position.HEALTHBAR ) {
			if ( client.player.getMaxHealth() + client.player.getMaxAbsorption() < 180 ) {
/* 			Displacement calculation extracted for clarity. -Dino
 			Calc breaks above 90 hearts since hearts don't get condensed further, so it overshoots down -Dino			*/
				int playerHealthRows = (int) Math.ceil((client.player.getMaxHealth() + client.player.getMaxAbsorption()) / 20);
				healthDisplacement = (10 * playerHealthRows) - ((playerHealthRows>2) ? (playerHealthRows -2) * (playerHealthRows -1) : 0);

//			Moves armorhud up depending on how much health you have, along with negative displacement from higher heart counts -Dino
				armorHeight -= healthDisplacement;
			}

			if ( client.player.isCreative() ) {
				armorHeight += 16 + healthDisplacement;
			} else if ( config.DISABLE_ARMOR_BAR ) {
				armorHeight += 10;
			}
		}

		if ( client.player.getAir() < client.player.getMaxAir() || client.player.isSubmergedInWater() && !client.player.isCreative() ) {
			armorHeight -= 10;
		}

//			Moves armorhud down if player is in creative
		if ( client.player.isCreative() ) {
			armorHeight += 16;
		}

//			Moves armorhud up if player is on mount, like horse
		if ( client.player.hasVehicle() && getRiddenEntity() != null ) {
			moveRiddenEntity();
		}
	}

	@Unique
	private void moveRiddenEntity() {
		assert client.player != null;

//		Check if entity player is riding is alive, like a horse
		if ( getRiddenEntity().isAlive() ) {

//		If horse health is 21, it still displays 10 hearts
			if ( getRiddenEntity().getMaxHealth() > 21 ) {
				if ( config.BETTER_MOUNT_HUD && !client.player.isCreative() ) {
					armorHeight -= 20;
				} else {
					armorHeight -= (client.player.isCreative() ? 26 : 10);
				}
			}

//		Armor hud only has to be moved up if better mount hud is enabled or player is in creative
			else {
				if ( config.BETTER_MOUNT_HUD && !client.player.isCreative() ) {
					armorHeight -= 10;
				} else if ( client.player.isCreative() ) {
					armorHeight -= 16;
				}
			}
		}
	}

}
