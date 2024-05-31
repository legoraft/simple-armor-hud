package com.armorhud;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class keyBindings {

    public static KeyBinding armorHudToggle;
    public static KeyBinding configTestToggle;

    public static void registerKeys() {
        armorHudToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.armorhud.armorvisible",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                "category.armorhud.toggles"
                ));
    }

}
