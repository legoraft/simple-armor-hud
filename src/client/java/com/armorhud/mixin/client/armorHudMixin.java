package com.armorhud.mixin.client;

import com.armorhud.armor.ArmorAccessor;
import com.armorhud.armorHud;
import com.armorhud.config.config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
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

	@Shadow public abstract void tick(boolean paused);

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

		renderArmor(context, tickCounter);
		moveArmor(context);
	}

	@Unique
	private void renderArmor(DrawContext context, RenderTickCounter tickCounter) {
		int scaledWidth = context.getScaledWindowWidth();

		assert client.player != null;

        ArmorAccessor armorAccessor = armorHud.getArmorAccessor();
        int pieces = armorAccessor.getPieces(client.player);

		final int hungerWidth = 80 + 8; // Bar advances 8 pixels to the left 10 times, 8 is added for the width of the last sprite.
		final int armorWidth = 15;
		final int barWidth = armorWidth * pieces;
		float hungerX = scaledWidth / 2f + 91;
		float x = hungerX - hungerWidth / 2f + barWidth / 2f;
		x += 2; // This makes it look better because the helmet is thinner.

        for (int j = 0; j < pieces; j++) {
			x -= armorWidth;
			int armorPiece;

			if (config.RTL) {
				armorPiece = (pieces - 1) - j;
			} else {
				armorPiece = j;
			}

			renderArmorPiece(context, x, armorHeight, tickCounter, client.player, armorAccessor.getArmorPiece(client.player, armorPiece));
		}
	}

	// Pretty much the same as renderHotbarItem but with x and y as float parameters.
	@Unique
	private void renderArmorPiece(DrawContext context, float x, float y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack) {
		if (stack.isEmpty()) return;

		context.getMatrices().push();
		context.getMatrices().translate(x, y, 0);

		context.drawItem(player, stack, 0, 0, 1);

		context.drawItemInSlot(this.client.textRenderer, stack, 0,0);
		context.getMatrices().pop();
	}

	@Unique
	private void moveArmor(DrawContext context) {
		int scaledHeight = context.getScaledWindowHeight();

		assert client.player != null;

//		Moves armorhud up if player uses double hotbar
		armorHeight = scaledHeight - (config.DOUBLE_HOTBAR ? 76 : 55);

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
