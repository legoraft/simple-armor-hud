package com.armorhud.config;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.option.SpruceBooleanOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class configScreen extends SpruceScreen {

    private final Screen parent;

    private final SpruceOption doubleHotbarToggle;
    private final SpruceOption betterMountHudToggle;
    private final SpruceOption armorHudToggle;

    public configScreen(@Nullable Screen parent) {
        super(Text.literal("Armorhud test GUI"));
        this.parent = parent;

        this.doubleHotbarToggle = new SpruceBooleanOption("config.doublehotbar", () -> config.DOUBLE_HOTBAR, newValue -> config.DOUBLE_HOTBAR = newValue, Text.translatable("config.description.doublehotbar"));
        this.betterMountHudToggle = new SpruceBooleanOption("config.bettermounthud", () -> config.BETTER_MOUNT_HUD, newValue -> config.BETTER_MOUNT_HUD = newValue, Text.translatable("config.description.bettermounthud"));
        this.armorHudToggle = new SpruceBooleanOption("config.armorvisible", () -> config.ARMOR_HUD, newValue -> config.ARMOR_HUD = newValue, Text.translatable("config.description.armorvisible"));
    }

    @Override
    protected void init() {
        super.init();

        SpruceOptionListWidget list = new SpruceOptionListWidget(Position.of(0, 34), this.width, this.height - 69);
        list.addOptionEntry(this.armorHudToggle, this.doubleHotbarToggle);
        list.addOptionEntry(this.betterMountHudToggle, null);

        this.addDrawableChild(list);

        this.addDrawableChild(new SpruceButtonWidget(Position.of(this.width / 2 - 100, this.height - 30), 200, 20, Text.translatable("config.done"), button -> close()));
    }

    @Override
    public void renderTitle(DrawContext context, int MouseX, int MouseY, float delta) {
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 16777215);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
    }

    public void close() {
        config.save();
        this.client.setScreen(parent);
    }

}
