package com.armorhud.hud;

import com.armorhud.config.config;
import net.minecraft.client.Minecraft;

public class entityMovement {
    public static int mountAdjustment(Minecraft minecraft, float vehicleHealth) {
        int armorAdjustment = 0;
        if ( !minecraft.player.getVehicle().isAlive() ) { return armorAdjustment; }

        if ( vehicleHealth > 21 ) {
            if (config.BETTER_MOUNT_HUD && !minecraft.player.isCreative()) {
                armorAdjustment = 20;
            } else {
                armorAdjustment = 10;
            }
        } else {
            if ( config.BETTER_MOUNT_HUD && !minecraft.player.isCreative() ) {
                armorAdjustment = 10;
            }
        }

        return armorAdjustment;
    }
}
