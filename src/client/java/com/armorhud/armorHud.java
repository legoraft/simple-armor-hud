package com.armorhud;

import com.armorhud.armor.ArmorAccessor;
import com.armorhud.armor.VanillaArmorAccessor;
import com.armorhud.config.config;
import com.armorhud.util.armorHudRegistries;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class armorHud implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("simple-armor-hud");
    public static final config CONFIG = new config();
    private static ArmorAccessor armorAccessor;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Simple Armor Hud loaded!");
        armorAccessor = new VanillaArmorAccessor();
        CONFIG.load();
        armorHudRegistries.registerArmorHud();
        handleKeys();

        LOGGER.info("Armor accessor implementation: {}", armorAccessor.getClass().getSimpleName());
    }

    public void handleKeys() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBindings.armorHudToggle.wasPressed()) {
                config.ARMOR_HUD = !config.ARMOR_HUD;
            }
        });
    }

    public static void setArmorAccessor(ArmorAccessor armorAccessor) {
        armorHud.armorAccessor = armorAccessor;
    }

    public static ArmorAccessor getArmorAccessor() {
        return armorAccessor;
    }
}
