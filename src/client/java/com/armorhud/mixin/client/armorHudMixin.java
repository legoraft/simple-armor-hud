package com.armorhud.mixin.client;

import com.armorhud.armor.ArmorAccessor;
import com.armorhud.armorHud;
import com.armorhud.config.config;
import com.armorhud.hud.armorRenderer;
import com.armorhud.hud.entityMovement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Hud;
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

@Mixin(Hud.class)
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
				armorRenderer.renderArmor(graphics, minecraft, armorHeight, FOODBAR_X);
				break;
			case "HEALTHBAR":
				armorRenderer.renderArmor(graphics, minecraft, armorHeight, HEALTHBAR_X);
				break;
			case "HOTBAR_LEFT":
				if ( !minecraft.player.getOffhandItem().isEmpty() ) {
					armorRenderer.renderArmor(graphics, minecraft, armorHeight, HOTBAR_LEFT_X - 28);
				} else {
					armorRenderer.renderArmor(graphics, minecraft, armorHeight, HOTBAR_LEFT_X);
				}
				break;
			case "HOTBAR_RIGHT":
				if ( minecraft.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR ) {
					armorRenderer.renderArmor(graphics, minecraft, armorHeight, HOTBAR_RIGHT_X + 25);
				} else {
					armorRenderer.renderArmor(graphics, minecraft, armorHeight, HOTBAR_RIGHT_X);
				}
				break;
		}

		moveArmor(graphics);
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
				armorHeight -= entityMovement.mountAdjustment(minecraft, getPlayerVehicleWithHealth().getMaxHealth(), armorHeight);
			}
		}
	}
}
