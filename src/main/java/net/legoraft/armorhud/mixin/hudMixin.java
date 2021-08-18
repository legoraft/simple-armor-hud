package net.legoraft.armorhud.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class hudMixin {

	@Shadow @Final private MinecraftClient client;
	@Shadow private int scaledWidth;
	@Shadow private int scaledHeight;


	@Inject(at = @At("TAIL"), method = "renderHotbar")
	public void renderArmorHud(float delta, MatrixStack matrices, CallbackInfo ci) {
		assert this.client.player != null;

		int i = this.scaledHeight - 55;

//		Moves armorhud up if player is under water
		if (!client.player.isSubmergedInWater()) {
			i = this.scaledHeight - 55;
		}
		if (client.player.isSubmergedInWater()) {
			i = this.scaledHeight - 63;
		}

//		Render all armor items from player
		MinecraftClient.getInstance().getItemRenderer().renderInGui(this.client.player.getInventory().getArmorStack(3), this.scaledWidth / 2 + 18, i);
		MinecraftClient.getInstance().getItemRenderer().renderInGui(this.client.player.getInventory().getArmorStack(2), this.scaledWidth / 2 + 33, i);
		MinecraftClient.getInstance().getItemRenderer().renderInGui(this.client.player.getInventory().getArmorStack(1), this.scaledWidth / 2 + 48, i);
		MinecraftClient.getInstance().getItemRenderer().renderInGui(this.client.player.getInventory().getArmorStack(0), this.scaledWidth / 2 + 63, i);

	}
}
