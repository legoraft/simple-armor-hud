package com.armorhud.mixin.client;

import com.armorhud.armorHud;
import com.armorhud.config.config;
import com.armorhud.hud.entityMovement;
import com.armorhud.hud.armorPlacement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Hud;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public abstract class armorHudMixin {

	@Shadow @Final private Minecraft minecraft;
	@Shadow protected abstract LivingEntity getPlayerVehicleWithHealth();
	@Unique int armorHeight;
	@Unique boolean initialized;

	@Inject(at = @At("TAIL"), method = "extractItemHotbar")
	private void renderHud(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		if( !config.ARMOR_HUD ) { return; }

		assert minecraft.player != null;

        if ( !initialized ) {
            armorHud.getArmorAccessor().initialize(minecraft.player);
            initialized = true;
        }

		armorPlacement.place(graphics, minecraft);
	}

	@Unique
	private void moveArmor(GuiGraphicsExtractor graphics) {
		int scaledHeight = graphics.guiHeight();
		int healthDisplacement = 0;
		assert minecraft.player != null;

		armorHeight = armorPlacement.heightPosition(scaledHeight, armorHeight);

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
				armorHeight -= entityMovement.mountAdjustment(minecraft, getPlayerVehicleWithHealth().getMaxHealth());
			}
		}
	}
}
