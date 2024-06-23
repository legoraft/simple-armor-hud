package com.armorhud.mixin.client;

import com.armorhud.config.config;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)

public abstract class inGameHudMixin {

//    @ModifyVariable(method = "renderArmor", at = @At("STORE"), ordinal = 1)
//    private static int renderArmor(int l) {
//
//        if (config.DISABLE_ARMOR_BAR) {
//            return 0;
//        } else {
//            return player.getArmor();
//        }
//    }

    @Inject(method = "renderArmor", at=@At("HEAD"))
    private static void checkArmor(DrawContext context, PlayerEntity player, int i, int j, int k, int x, CallbackInfo ci) {
        int l;

        if (config.DISABLE_ARMOR_BAR) {
            l = 0;
        } else {
            l = player.getArmor();
        }
    }

}
