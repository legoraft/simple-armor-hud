package net.legoraft.armorhud.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class hudMixin {

	@Shadow @Final private MinecraftClient client;
	@Shadow private int scaledWidth;
	@Shadow private int scaledHeight;
	@Shadow protected abstract void renderHotbarItem(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed);

	@Inject(at = @At("HEAD"), method = "renderHotbar")
	public void renderArmorHud(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
		assert this.client.player != null;

		int i = this.scaledHeight - 55;

//		Moves armorhud up if player is under water
		if (!client.player.isSubmergedInWater() && !client.player.isCreative()) {
			i = this.scaledHeight - 55;
		}
		if (client.player.isSubmergedInWater() && !client.player.isCreative()) {
			i = this.scaledHeight - 65;
		}
		if (client.player.isCreative()) {
			i = this.scaledHeight - 39;
		}

//		Render all armor items from player
		this.renderHotbarItem(this.scaledWidth / 2 + 18, i, tickDelta, client.player, this.client.player.getInventory().getArmorStack(3), 1);
		this.renderHotbarItem(this.scaledWidth / 2 + 33, i, tickDelta, client.player, this.client.player.getInventory().getArmorStack(2), 1);
		this.renderHotbarItem(this.scaledWidth / 2 + 48, i, tickDelta, client.player, this.client.player.getInventory().getArmorStack(1), 1);
		this.renderHotbarItem(this.scaledWidth / 2 + 63, i, tickDelta, client.player, this.client.player.getInventory().getArmorStack(0), 1);

	}
	
	@ModifyVariable(at = @At("STORE"), method = "renderHeldItemTooltip", ordinal = 2)
	public int renderHeldItemTooltip(int k) {
		return this.scaledHeight - 62;
	}
	
}
