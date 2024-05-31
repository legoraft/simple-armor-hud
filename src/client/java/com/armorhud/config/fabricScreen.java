package com.armorhud.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class fabricScreen extends Screen {

    public Screen parent;

    public fabricScreen(Screen parent) {
        super(Text.translatable("config.title"));

        this.parent = parent;
    }

    public ButtonWidget doubleHotbarToggle;

    @Override
    protected void init() {
        doubleHotbarToggle = ButtonWidget.builder(Text.of(Text.translatable("config.doublehotbar")), button -> {
            config.DOUBLE_HOTBAR = !config.DOUBLE_HOTBAR;
        })
                .dimensions(width / 2 - 205, 20, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("config.description.doublehotbar")))
                .build();

        addDrawableChild(doubleHotbarToggle);
    }

    @Override
    public void close() {
        config.save();
        this.client.setScreen(this.parent);
    }

}
