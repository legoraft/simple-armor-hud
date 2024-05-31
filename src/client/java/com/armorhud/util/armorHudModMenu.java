package com.armorhud.util;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.armorhud.config.fabricScreen;
import net.minecraft.client.gui.screen.Screen;

public class armorHudModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return this::createConfigScreen;
    }

    private Screen createConfigScreen(Screen parent) {
        return new fabricScreen(parent);
    }

}
