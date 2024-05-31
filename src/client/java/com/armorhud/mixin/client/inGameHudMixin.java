package com.armorhud.mixin.client;

import com.armorhud.config.config;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameHud.class)

public abstract class inGameHudMixin {

    @Shadow @Nullable protected abstract PlayerEntity getCameraPlayer();

    @ModifyVariable(method = "renderStatusBars", at = @At("STORE"), ordinal = 11)
    public int renderStatusBars(int u) {
        PlayerEntity playerEntity = this.getCameraPlayer();
        assert playerEntity != null;

        if (config.DISABLE_ARMOR_BAR) {
            return 0;
        } else {
            return playerEntity.getArmor();
        }
    }

}
