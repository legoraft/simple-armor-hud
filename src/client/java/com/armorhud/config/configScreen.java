package com.armorhud.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class configScreen extends OptionsSubScreen {
    private final Screen parent;

    public configScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, Component.translatable("config.title"));
        this.parent = parent;
    }

    @Override
    protected void addOptions() {
        assert this.list != null;

        OptionInstance<config.Position> armorPositionOptions = new OptionInstance<>(
                "config.armorposition",
                OptionInstance.noTooltip(),
                (component, value) -> value.getDisplayName(),
                new OptionInstance.Enum<>(java.util.List.of(config.Position.values()), null),
                config.position,
                value -> config.position = value
        );

        this.list.addHeader(Component.translatable("config.header.general"));
        this.list.addSmall(
                OptionInstance.createBoolean("config.armorvisible", config.ARMOR_HUD, v -> config.ARMOR_HUD = v),
                OptionInstance.createBoolean("config.disablearmorbar", config.DISABLE_ARMOR_BAR, v -> config.DISABLE_ARMOR_BAR = v)
        );

        this.list.addHeader(Component.translatable("config.header.compatibility"));
        this.list.addSmall(
                OptionInstance.createBoolean("config.doublehotbar", config.DOUBLE_HOTBAR, v -> config.DOUBLE_HOTBAR = v),
                OptionInstance.createBoolean("config.bettermounthud", config.BETTER_MOUNT_HUD, v -> config.BETTER_MOUNT_HUD = v)
        );

        this.list.addHeader(Component.translatable("config.header.display"));
        this.list.addSmall(
                armorPositionOptions,
                OptionInstance.createBoolean("config.righttoleft", config.RTL, v -> config.RTL = v),
                OptionInstance.createBoolean("config.trimemptyslots", config.TRIM_EMPTY_SLOTS, v -> config.TRIM_EMPTY_SLOTS = v)
        );
    }

    @Override
    public void onClose() {
        config.save();
        this.minecraft.setScreen(this.parent);
    }
}
