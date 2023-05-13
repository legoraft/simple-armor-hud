package com.armorhud;

import net.fabricmc.api.ClientModInitializer;

public class armorHud implements ClientModInitializer {

    public static final config CONFIG = new config();

    @Override
    public void onInitializeClient() {
        System.out.println("Simple Armor Hud loaded!");
        CONFIG.load();
    }
}
