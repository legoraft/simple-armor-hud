package com.armorhud.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.layouts.*;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class configScreen extends OptionsSubScreen {
    private final Screen parent;

    public configScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, Component.translatable("config.title"));
        this.parent = parent;
    }

    public CycleButton<?> doubleHotbarToggle;
    public CycleButton<?> betterMountHudToggle;
    public CycleButton<?> armorHudToggle;
    public CycleButton<?> disableArmorBar;
    public CycleButton<?> armorPosition;
    public CycleButton<?> rightToLeftToggle;
    public CycleButton<?> trimEmptySlots;

    public Button doneButton;

    @Override
    protected void init() {
        doubleHotbarToggle = CycleButton.onOffBuilder(config.DOUBLE_HOTBAR)
                .create(Component.translatable("config.doublehotbar"), ((button, value) -> config.DOUBLE_HOTBAR = !config.DOUBLE_HOTBAR));

        betterMountHudToggle = CycleButton.onOffBuilder(config.BETTER_MOUNT_HUD)
                .create(Component.translatable("config.bettermounthud"), (button, value) -> config.BETTER_MOUNT_HUD = !config.BETTER_MOUNT_HUD);

        armorHudToggle = CycleButton.onOffBuilder(config.ARMOR_HUD)
                .create(Component.translatable("config.armorvisible"), (button, value) -> config.ARMOR_HUD = !config.ARMOR_HUD);

        disableArmorBar = CycleButton.onOffBuilder(config.DISABLE_ARMOR_BAR)
                .create(Component.translatable("config.disablearmorbar"), ((button, value) -> config.DISABLE_ARMOR_BAR = !config.DISABLE_ARMOR_BAR));

        armorPosition = CycleButton.builder(config.Position::displayName, config.Position.valueOf(String.valueOf(config.position))).withValues(config.Position.values())
                .create(Component.translatable("config.armorposition"), ((button, value) -> config.position = value));

        rightToLeftToggle = CycleButton.onOffBuilder(config.RTL)
                .create(Component.translatable("config.righttoleft"), (button, value) -> config.RTL = !config.RTL);

        trimEmptySlots = CycleButton.onOffBuilder(config.TRIM_EMPTY_SLOTS)
                .create(Component.translatable("config.trimemptyslots"), ((button, value) -> config.TRIM_EMPTY_SLOTS = !config.TRIM_EMPTY_SLOTS));

        doneButton = Button
                .builder(Component.translatable("config.done"), button -> onClose())
                .bounds(width / 2 - 100, height - 25, 200, 20)
                .build();

        OptionsList optionListWidget = this.addRenderableWidget(new OptionsList(this.minecraft, this.width, this));
        optionListWidget.addHeader(Component.translatable("config.header.general"));
        optionListWidget.addSmall(armorHudToggle, disableArmorBar);

        optionListWidget.addHeader(Component.translatable("config.header.compatibility"));
        optionListWidget.addSmall(betterMountHudToggle, doubleHotbarToggle);

        optionListWidget.addHeader(Component.translatable("config.header.display"));
        optionListWidget.addSmall(armorPosition, rightToLeftToggle);
        optionListWidget.addSmall(trimEmptySlots, null);

        addRenderableWidget(doneButton);
    }

    @Override
    protected void addOptions() { }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        context.centeredText(this.font, Component.translatable("config.title"), this.width / 2, 12, 0xffffff);
        super.extractRenderState(context, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;

        config.save();
        this.minecraft.setScreen(this.parent);
    }
}
