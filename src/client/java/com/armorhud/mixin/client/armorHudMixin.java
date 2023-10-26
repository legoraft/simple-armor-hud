package com.armorhud.mixin.client;

import com.armorhud.config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
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

	@Shadow @Final private MinecraftClient client;
	@Shadow private int scaledWidth;
	@Shadow private int scaledHeight;
	@Shadow protected abstract LivingEntity getRiddenEntity();
	@Shadow protected abstract void renderHotbarItem(DrawContext context, int x, int y, float f, PlayerEntity player, ItemStack stack, int seed);

	int armorHeight;

	@Inject(at = @At("TAIL"), method = "renderHotbar")
	private void renderHud(float tickDelta, DrawContext context, CallbackInfo ci) {
		if(!config.ARMOR_HUD) { return; }

		assert client.player != null;

		renderArmor(context, tickDelta);
		moveArmor();

	}

	private void renderArmor(DrawContext context, float tickDelta) {
		int armorX = 68;

		for (int j = 0; j < 4; j++) {
			renderHotbarItem(context, this.scaledWidth / 2 + armorX, armorHeight, tickDelta, client.player, client.player.getInventory().getArmorStack(j), 1);
			armorX -= 15;
		}
	}

	private void moveArmor() {

//		Moves armorhud up if player uses double hotbar
		armorHeight = this.scaledHeight - (config.DOUBLE_HOTBAR ? 76 : 55);

//		Moves armorhud up if player is underwater
		if (client.player.getAir() < client.player.getMaxAir() || client.player.isSubmergedInWater() && !client.player.isCreative()) {
			armorHeight -= 10;
		}

//		Moves armorhud down if player is in creative
		armorHeight += (client.player.isCreative() ? 16 : 0);

//		Moves armorhud up if player is on mount
		if (client.player.hasVehicle() && getRiddenEntity() != null) {
			moveArmorHorse();
		}
	}

	private void moveArmorHorse() {

		if (getRiddenEntity().isAlive()) {
			if (getRiddenEntity().getMaxHealth() > 21) {
				if (config.BETTER_MOUNT_HUD && !client.player.isCreative()) {
					armorHeight -= 20;
				} else {
					armorHeight -= (client.player.isCreative() ? 26 : 10);
				}
			}
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
