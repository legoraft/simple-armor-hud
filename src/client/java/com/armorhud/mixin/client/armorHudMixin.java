package com.armorhud.mixin.client;

import com.armorhud.armorHud;
import com.armorhud.config.config;
import com.armorhud.hud.armorPlacement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Hud;
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
}
