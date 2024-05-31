package com.armorhud.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class fabricScreen extends Screen {

    public fabricScreen() {
        super(Text.translatable("config.title"));
    }

    public ButtonWidget doubleHotbarToggle;

    @Override
    protected void init() {
        doubleHotbarToggle = ButtonWidget.builder(Text.of("config.doublehotbar"), button -> {

        })
                .dimensions(width / 2 - 205, 20, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("config.description.doublehotbar")))
                .build();

        addDrawableChild(doubleHotbarToggle);
    }

}
