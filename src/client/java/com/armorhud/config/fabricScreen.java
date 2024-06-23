package com.armorhud.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;

public class fabricScreen extends GameOptionsScreen {
    public Screen parent;

    public fabricScreen(Screen parent) {
        super(parent, null, Text.translatable("config.title"));

        this.parent = parent;
    }

    public CyclingButtonWidget doubleHotbarToggle;
    public CyclingButtonWidget betterMountHudToggle;
    public CyclingButtonWidget armorHudToggle;
    public CyclingButtonWidget rightToLeftToggle;

    public CyclingButtonWidget disableArmorBar;

    @Override
    protected void init() {
        doubleHotbarToggle = CyclingButtonWidget.onOffBuilder(config.DOUBLE_HOTBAR)
                .build(Text.translatable("config.doublehotbar"), ((button, value) -> config.DOUBLE_HOTBAR = !config.DOUBLE_HOTBAR));

        betterMountHudToggle = CyclingButtonWidget.onOffBuilder(config.BETTER_MOUNT_HUD)
                .build(Text.translatable("config.bettermounthud"), (button, value) -> config.BETTER_MOUNT_HUD = !config.BETTER_MOUNT_HUD);

        armorHudToggle = CyclingButtonWidget.onOffBuilder(config.ARMOR_HUD)
                .build(Text.translatable("config.armorvisible"), (button, value) -> config.ARMOR_HUD = !config.ARMOR_HUD);

        rightToLeftToggle = CyclingButtonWidget.onOffBuilder(config.RTL)
                .build(Text.translatable("config.righttoleft"), (button, value) -> config.RTL = !config.RTL);

        disableArmorBar = CyclingButtonWidget.onOffBuilder(config.DISABLE_ARMOR_BAR)
                .build(Text.translatable("config.disablearmorbar"), ((button, value) -> config.DISABLE_ARMOR_BAR = !config.DISABLE_ARMOR_BAR));

        OptionListWidget optionListWidget = this.addDrawableChild(new OptionListWidget(this.client, this.width, this));

        optionListWidget.addWidgetEntry(doubleHotbarToggle, betterMountHudToggle);
        optionListWidget.addWidgetEntry(armorHudToggle, rightToLeftToggle);
        optionListWidget.addWidgetEntry(disableArmorBar, null);
    }

    @Override
    protected void addOptions() {
        super.init();
    }

    @Override
    public void close() {
        assert this.client != null;

        config.save();
        this.client.setScreen(this.parent);
    }

}
