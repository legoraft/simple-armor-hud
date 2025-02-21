package com.armorhud.util;

//import com.armorhud.armor.TrinketsArmorAccessor;
import com.armorhud.armorHud;
import com.armorhud.config.config;
import net.fabricmc.loader.api.FabricLoader;

public class modDetect {

    public static void detect() {
        if (FabricLoader.getInstance().isModLoaded("bettermounthud")) {
            config.BETTER_MOUNT_HUD = true;
            armorHud.LOGGER.info("Better mount hud found!");
        }
        if (FabricLoader.getInstance().isModLoaded("double_hotbar")) {
            config.DOUBLE_HOTBAR = true;
            armorHud.LOGGER.info("Double hotbar found!");
        }
        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
//            armorHud.setArmorAccessor(new TrinketsArmorAccessor(armorHud.getArmorAccessor()));
            armorHud.LOGGER.info("Trinkets found!");
        }

        config.save();
    }
}
