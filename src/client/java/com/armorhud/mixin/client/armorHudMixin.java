package com.armorhud.mixin.client;

import com.armorhud.config.config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
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
	@Shadow private int scaledWidth;
	@Shadow private int scaledHeight;
	@Shadow protected abstract LivingEntity getRiddenEntity();

	@Unique int armorHeight;

	@Inject(at = @At("TAIL"), method = "renderHotbar")
	private void renderHud(float tickDelta, DrawContext context, CallbackInfo ci) {
		if(!config.ARMOR_HUD) { return; }

		assert client.player != null;

		renderArmor(context, tickDelta);
		moveArmor();
	}

	@Unique
	private void renderArmor(DrawContext context, float tickDelta) {
		final int hungerWidth = 80 + 8; // Bar advances 8 pixels to the left 10 times, 8 is added for the width of the last sprite.
		final int armorWidth = 15;
		final int barWidth = armorWidth * 4;
		float hungerX = scaledWidth / 2f + 91;
		float x = hungerX - hungerWidth / 2f + barWidth / 2f;
		x += 2; // This makes it look better because the helmet is thinner.

		for (int j = 0; j < 4; j++) {
			x -= armorWidth;
			renderArmorPiece(context, x, armorHeight, tickDelta, client.player, client.player.getInventory().getArmorStack(j), 1);
		}
	}

	// Pretty much the same as renderHotbarItem but with x and y as float parameters.
	@Unique
	private void renderArmorPiece(DrawContext context, float x, float y, float tickDelta, PlayerEntity player, ItemStack stack, int seed) {
		if (stack.isEmpty()) return;

		// Magic
		float f = (float)stack.getBobbingAnimationTime() - tickDelta;
		context.getMatrices().push();
		context.getMatrices().translate(x, y, 0);

		if (f > 0) {
			float g = 1 + f / 5;
			context.getMatrices().push();
			context.getMatrices().translate(8, 12, 0);
			context.getMatrices().scale(1 / g, (g + 1) / 2, 1);
			context.getMatrices().translate(-8, -12, 0);
		}
		context.drawItem(player, stack, 0, 0, seed);
		if (f > 0) {
			context.getMatrices().pop();
		}

		context.drawItemInSlot(this.client.textRenderer, stack, 0,0);
		context.getMatrices().pop();
	}

	@Unique
	private void moveArmor() {

//		Moves armorhud up if player uses double hotbar
		armorHeight = this.scaledHeight - (config.DOUBLE_HOTBAR ? 76 : 55);

//		Moves armorhud up if player is underwater
		if (client.player.getAir() < client.player.getMaxAir() || client.player.isSubmergedInWater() && !client.player.isCreative()) {
			armorHeight -= 10;
		}

//		Moves armorhud down if player is in creative
		armorHeight += (client.player.isCreative() ? 16 : 0);

//		Moves armorhud up if player is on mount, like horse
		if (client.player.hasVehicle() && getRiddenEntity() != null) {
			moveArmorHorse();
		}
	}

	private void moveArmorHorse() {

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
