package com.armorhud;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class keyBindings {

	public static KeyBinding armorHudToggle;

	public static void registerKeys() {
		final KeyBinding.Category category = KeyBinding.Category.create(Identifier.of("simple-armor-hud", "armorhud.toggles"));
		armorHudToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.armorhud.armorvisible",
				InputUtil.UNKNOWN_KEY.getCode(),
				category
		));
	}

}
