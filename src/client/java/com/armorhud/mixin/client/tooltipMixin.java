package com.armorhud.mixin.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Hud.class)

public abstract class tooltipMixin {

    @ModifyVariable(method = "extractSelectedItemName", at = @At("STORE"), ordinal = 2)
    public int renderHeldItemTooltip(int k, GuiGraphicsExtractor context) {
        return context.guiHeight() - 62;
    }

}
