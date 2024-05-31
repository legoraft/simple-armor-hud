package com.armorhud.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class fabricScreen extends GameOptionsScreen {

    public Screen parent;

    public fabricScreen(Screen parent) {
        super(parent, null, Text.translatable("config.title"));

        this.parent = parent;
    }

    public CyclingButtonWidget doubleHotbarToggle;
    public CyclingButtonWidget betterMountHudToggle;
    private OptionListWidget optionListWidget;

    @Override
    protected void init() {
        this.optionListWidget = this.addDrawableChild(new OptionListWidget(this.client, this.width, this.height, this));

        doubleHotbarToggle = CyclingButtonWidget.onOffBuilder(config.DOUBLE_HOTBAR)
                .build(Text.translatable("config.doublehotbar"), ((button, value) -> {
            config.DOUBLE_HOTBAR = !config.DOUBLE_HOTBAR;
        }));

        betterMountHudToggle = CyclingButtonWidget.onOffBuilder(config.BETTER_MOUNT_HUD)
                .build(Text.translatable("config.bettermounthud"), (button, value) -> {
            config.BETTER_MOUNT_HUD = !config.BETTER_MOUNT_HUD;
        });

        this.optionListWidget.addWidgetEntry(doubleHotbarToggle, betterMountHudToggle);
        super.init();
    }

    @Override
    public void close() {
        config.save();
        this.client.setScreen(this.parent);
    }

}
