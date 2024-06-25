package com.armorhud.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameHud.class)

public abstract class tooltipMixin {

    @ModifyVariable(method = "renderHeldItemTooltip", at = @At("STORE"), ordinal = 2)
    public int renderHeldItemTooltip(int k, DrawContext context) {
        return context.getScaledWindowHeight() - 62;
    }

}
