package com.armorhud.hud;

import com.armorhud.config.config;
import net.minecraft.client.Minecraft;

public class entityMovement {
    public static int mountAdjustment(Minecraft minecraft, float vehicleHealth, int armorHeight) {
        if ( !minecraft.player.getVehicle().isAlive() ) { return 0; }

        if ( vehicleHealth > 21 ) {
            if (config.BETTER_MOUNT_HUD && !minecraft.player.isCreative()) {
                armorHeight = -20;
            } else {
                armorHeight = -(minecraft.player.isCreative() ? 26 : 10);
            }
        } else {
            if ( config.BETTER_MOUNT_HUD && !minecraft.player.isCreative() ) {
                armorHeight = -10;
            } else if ( minecraft.player.isCreative() ) {
                armorHeight = -16;
            }
        }

        return armorHeight;
    }
}
