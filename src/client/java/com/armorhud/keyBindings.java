package com.armorhud;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;

public class keyBindings {

    public static KeyBinding armorHudToggle;

    public static void registerKeys() {
        armorHudToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.armorhud.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "category.armorhud.toggles"
                ));
    }

}
