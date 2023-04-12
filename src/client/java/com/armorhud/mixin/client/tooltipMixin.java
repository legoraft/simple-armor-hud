package com.armorhud.mixin.client;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameHud.class)

public abstract class tooltipMixin {

    @Shadow private int scaledHeight;

    @ModifyVariable(method = "renderHeldItemTooltip", at = @At("STORE"), ordinal = 2)
    public int renderHeldItemTooltip(int k) {
        return this.scaledHeight - 62;
    }

}
