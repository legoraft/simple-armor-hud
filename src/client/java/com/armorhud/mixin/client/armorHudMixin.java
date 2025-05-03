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

	@Inject(at = @At("TAIL"), method = "renderHotbar")
	private void renderHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		if(!config.ARMOR_HUD) { return; }

		assert client.player != null;

        if (!initialized) {
            armorHud.getArmorAccessor().initialize(client.player);
            initialized = true;
        }

		renderArmor(context);
		moveArmor(context);
	}

	@Unique
	private void renderArmor(DrawContext context) {
		int scaledWidth = context.getScaledWindowWidth();

		assert client.player != null;

        ArmorAccessor armorAccessor = armorHud.getArmorAccessor();

		final int hungerWidth = 14; // Magic number to center 4 armor pieces
		final int armorWidth = 15;

//		Added check for Above_Health_Bar -Dino
		float hungerX = scaledWidth / 2f + (config.ABOVE_HEALTH_BAR
				&& client.player.getMaxHealth() + client.player.getMaxAbsorption() < 180 ? -10 : 91);
		float x = hungerX + hungerWidth;
		x += 2; // This makes it look better because the helmet is thinner.

        for (EquipmentSlot slot : EquipmentSlot.values()) {
			x -= armorWidth;

			if (slot.isArmorSlot()) {
				renderArmorPiece(context, x, armorHeight, client.player, armorAccessor.getArmorPiece(client.player, slot));
			}
		}
	}

	// Pretty much the same as renderHotbarItem but with x and y as float parameters.
	@Unique
	private void renderArmorPiece(DrawContext context, float x, float y, PlayerEntity player, ItemStack stack) {
		if (stack.isEmpty()) return;

		context.getMatrices().push();
		context.getMatrices().translate(x, y, 0);

		context.drawItem(player, stack, 0, 0, 1);

		context.drawStackOverlay(this.client.textRenderer, stack, 0,0);
		context.getMatrices().pop();
	}

	@Unique
	private void moveArmor(DrawContext context) {
		int scaledHeight = context.getScaledWindowHeight();

		assert client.player != null;

//		Moves armorhud up if player uses double hotbar
		armorHeight = scaledHeight - (config.DOUBLE_HOTBAR ? 76 : 55);

/*  	TODO: fix visual bug where remaining hearts available (>20hp) get removed later than armor hud -Dino
		Skips unnecessary checks when Above_Health_Bar is on, not a fan of the extra if statement, but it works -Dino
		Note: setting gets turned off above 9 rows of hearts, since the hud will fly off the screen at some point -Dino			*/
		if (config.ABOVE_HEALTH_BAR && client.player.getMaxHealth() + client.player.getMaxAbsorption() < 180) {
/* 			Displacement calculation extracted for clarity. -Dino
 			Calc breaks above 90 hearts since hearts don't get condensed further, so it overshoots down -Dino			*/
			int playerHealthRows = (int) Math.ceil((client.player.getMaxHealth() + client.player.getMaxAbsorption()) / 20);
			int healthDisplacement = (10 * playerHealthRows) - ((playerHealthRows>2) ? (playerHealthRows -2) * (playerHealthRows -1) : 0);

//			Moves armorhud up depending on how much health you have, along with negative displacement from higher heart counts -Dino
			armorHeight -= healthDisplacement;

/*			Moves armorhud down if player is in creative or Disable_Armor_Bar is on,
			formatted as an if-statement for readability -Dino
 */
			if(client.player.isCreative()) {
				armorHeight += 16 + healthDisplacement;
			} else if (config.DISABLE_ARMOR_BAR) {
				armorHeight += 10;
			}
		} else {
//			Moves armorhud up if player is underwater
			if ((client.player.getAir() < client.player.getMaxAir() || client.player.isSubmergedInWater() && !client.player.isCreative())) {
				armorHeight -= 10;
			}

//			Moves armorhud down if player is in creative
			armorHeight += (client.player.isCreative() ? 16 : 0);

//			Moves armorhud up if player is on mount, like horse
			if (client.player.hasVehicle() && getRiddenEntity() != null) {
				moveArmorHorse();
			}
		}
	}

	@Unique
	private void moveArmorHorse() {
		assert client.player != null;

//		Check if entity player is riding is alive, like a horse
		if (getRiddenEntity().isAlive()) {
//		If horse health is 21, it still displays 10 hearts
			if (getRiddenEntity().getMaxHealth() > 21) {
				if (config.BETTER_MOUNT_HUD && !client.player.isCreative()) {
					armorHeight -= 20;
				} else {
					armorHeight -= (client.player.isCreative() ? 26 : 10);
				}
			}

//		Armor hud only has to be moved up if better mount hud is enabled or player is in creative
			else {
				if (config.BETTER_MOUNT_HUD && !client.player.isCreative()) {
					armorHeight -= 10;
				} else if (client.player.isCreative()) {
					armorHeight -= 16;
				}
			}
		}
	}

}
