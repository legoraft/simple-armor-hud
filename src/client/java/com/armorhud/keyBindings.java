package com.armorhud;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;

public class keyBindings {

    public static KeyMapping armorHudToggle;

    public static void registerKeys() {
        final KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("simple-armor-hud", "armorhud.toggles"));
        armorHudToggle = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.armorhud.armorvisible",
            InputConstants.UNKNOWN.getValue(),
            category
        ));
    }

}
