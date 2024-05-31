package com.armorhud;

import com.armorhud.config.config;
import com.armorhud.config.fabricScreen;
import com.armorhud.util.armorHudRegistries;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class armorHud implements ClientModInitializer {

    public static final config CONFIG = new config();

    @Override
    public void onInitializeClient() {
        System.out.println("Simple Armor Hud loaded!");
        CONFIG.load();
        armorHudRegistries.registerArmorHud();
        handleKeys();
    }

    public void handleKeys() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBindings.armorHudToggle.wasPressed()) {
                config.ARMOR_HUD = !config.ARMOR_HUD;
            }

            if (keyBindings.configTestToggle.wasPressed()) {
                client.setScreen(new fabricScreen());
            }
        });
    }
}
