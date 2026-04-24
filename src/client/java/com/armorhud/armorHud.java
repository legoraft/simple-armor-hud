package com.armorhud;

import com.armorhud.armor.ArmorAccessor;
import com.armorhud.armor.CombinedArmorAccessor;
import com.armorhud.armor.VanillaArmorAccessor;
import com.armorhud.config.config;
import com.armorhud.util.armorHudRegistries;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class armorHud implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("simple-armor-hud");
    public static final config CONFIG = new config();
    private static CombinedArmorAccessor armorAccessor;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Simple Armor Hud loaded!");
        armorAccessor = new CombinedArmorAccessor();
        armorAccessor.addAccessor(new VanillaArmorAccessor());

        CONFIG.load();
        armorHudRegistries.registerArmorHud();
        handleKeys();

        String accessorNames = armorAccessor.getAccessors().stream()
                .map(ArmorAccessor::getName)
                .collect(Collectors.joining(", "));

        LOGGER.info("Loaded armorAccessors: [ {} ]",
                accessorNames.isEmpty() ? "none" : accessorNames);
    }

    public void handleKeys() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (keyBindings.armorHudToggle.consumeClick()) {
                config.ARMOR_HUD = !config.ARMOR_HUD;
            }
        });
    }

    public static ArmorAccessor getArmorAccessor() {
        return armorAccessor;
    }
}
