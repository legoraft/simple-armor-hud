package com.armorhud.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class configScreen extends GameOptionsScreen {
    private final Screen parent;

    public configScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.translatable("config.title"));
        this.parent = parent;
    }

    public CyclingButtonWidget<?> doubleHotbarToggle;
    public CyclingButtonWidget<?> betterMountHudToggle;
    public CyclingButtonWidget<?> armorHudToggle;
    public CyclingButtonWidget<?> disableArmorBar;
    public CyclingButtonWidget<?> armorPosition;
    public CyclingButtonWidget<?> rightToLeftToggle;
    public CyclingButtonWidget<?> trimEmptySlots;

    public ButtonWidget doneButton;

    @Override
    protected void init() {
        doubleHotbarToggle = CyclingButtonWidget.onOffBuilder(config.DOUBLE_HOTBAR)
                .build(Text.translatable("config.doublehotbar"), ((button, value) -> config.DOUBLE_HOTBAR = !config.DOUBLE_HOTBAR));

        betterMountHudToggle = CyclingButtonWidget.onOffBuilder(config.BETTER_MOUNT_HUD)
                .build(Text.translatable("config.bettermounthud"), (button, value) -> config.BETTER_MOUNT_HUD = !config.BETTER_MOUNT_HUD);

        armorHudToggle = CyclingButtonWidget.onOffBuilder(config.ARMOR_HUD)
                .build(Text.translatable("config.armorvisible"), (button, value) -> config.ARMOR_HUD = !config.ARMOR_HUD);

        disableArmorBar = CyclingButtonWidget.onOffBuilder(config.DISABLE_ARMOR_BAR)
                .build(Text.translatable("config.disablearmorbar"), ((button, value) -> config.DISABLE_ARMOR_BAR = !config.DISABLE_ARMOR_BAR));

        armorPosition = CyclingButtonWidget.builder(config.Position::displayName, config.Position.valueOf(String.valueOf(config.position))).values(config.Position.values())
                .build(Text.translatable("config.armorposition"), ((button, value) -> config.position = value));

        rightToLeftToggle = CyclingButtonWidget.onOffBuilder(config.RTL)
                .build(Text.translatable("config.righttoleft"), (button, value) -> config.RTL = !config.RTL);

        trimEmptySlots = CyclingButtonWidget.onOffBuilder(config.TRIM_EMPTY_SLOTS)
                .build(Text.translatable("config.trimemptyslots"), ((button, value) -> config.TRIM_EMPTY_SLOTS = !config.TRIM_EMPTY_SLOTS));

        doneButton = ButtonWidget
                .builder(Text.translatable("config.done"), button -> close())
                .dimensions(width / 2 - 100, height - 25, 200, 20)
                .build();

        OptionListWidget optionListWidget = this.addDrawableChild(new OptionListWidget(this.client, this.width, this));
        optionListWidget.addHeader(Text.translatable("config.header.general"));
        optionListWidget.addWidgetEntry(armorHudToggle, disableArmorBar);

        optionListWidget.addHeader(Text.translatable("config.header.compatibility"));
        optionListWidget.addWidgetEntry(betterMountHudToggle, doubleHotbarToggle);

        optionListWidget.addHeader(Text.translatable("config.header.display"));
        optionListWidget.addWidgetEntry(armorPosition, rightToLeftToggle);
        optionListWidget.addWidgetEntry(trimEmptySlots, null);

        addDrawableChild(doneButton);
    }

    @Override
    protected void addOptions() { }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("config.title"), this.width / 2, 12, 0xffffff);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        assert this.client != null;

        config.save();
        this.client.setScreen(this.parent);
    }
}
