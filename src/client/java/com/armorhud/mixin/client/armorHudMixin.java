package com.armorhud.mixin.client;

import com.armorhud.config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)

public abstract class armorHudMixin {

	@Shadow protected abstract void renderHotbarItem(MatrixStack matrixStack, int i, int j, float f, PlayerEntity playerEntity, ItemStack itemStack, int k);

	@Shadow @Final private MinecraftClient client;

	@Shadow private int scaledWidth;

	@Shadow private int scaledHeight;

	@Shadow protected abstract LivingEntity getRiddenEntity();

	@Inject(at = @At("HEAD"), method = "render")

	private void renderHud(MatrixStack matrices, float tickDelta, CallbackInfo ci) {

		assert client.player != null;

		int h = 68;
		int i = this.scaledHeight - 55;

//		Moves armorhud up if player is underwater
		if (client.player.getAir() < client.player.getMaxAir() || client.player.isSwimming()) {
			i = this.scaledHeight - 65;
		}

//		Moves armorhud down if player is in creative
		if (client.player.isCreative()) {
			i = this.scaledHeight - 39;
		}

//		Moves armorhud up if player is on mount
		if (client.player.hasVehicle() && getRiddenEntity() != null) {
			if (getRiddenEntity().isAlive()) {
				if (getRiddenEntity().getMaxHealth() > 20) {
					if (config.BETTER_MOUNT_HUD) {
						i = this.scaledHeight - 75;
					} else {
						i = this.scaledHeight - 65;
					}
				}
				else {
					if (config.BETTER_MOUNT_HUD) {
						i = this.scaledHeight - 65;
					} else {
						i = this.scaledHeight - 55;
					}
				}
			}
		}

//		Render all armor items from player
		for (int j = 0; j < 4; j++) {
			renderHotbarItem(matrices, this.scaledWidth  / 2 + h, i, tickDelta, client.player, client.player.getInventory().getArmorStack(j), 1);
			h -= 15;
		}

	}

}