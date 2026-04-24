package com.armorhud.mixin.client;

import com.armorhud.config.config;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)

public abstract class inGameHudMixin {

    @Inject(method = "extractArmor", at=@At("HEAD"), cancellable = true)
    private static void checkArmor(GuiGraphicsExtractor context, Player player, int i, int j, int k, int x, CallbackInfo ci) {
        if (config.DISABLE_ARMOR_BAR) {
            ci.cancel();
        }
    }

}
