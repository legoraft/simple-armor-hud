package com.armorhud.util;

import com.armorhud.config.config;
import net.fabricmc.loader.api.FabricLoader;

public class modDetect {

    public static void detect() {
        if (FabricLoader.getInstance().isModLoaded("bettermounthud")) {
            config.BETTER_MOUNT_HUD = true;
            System.out.println("Better mount hud found!");
        }
        if (FabricLoader.getInstance().isModLoaded("double_hotbar")) {
            config.DOUBLE_HOTBAR = true;
            System.out.println("Double hotbar found!");
        }

        config.save();
    }
}
