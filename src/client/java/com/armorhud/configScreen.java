package com.armorhud;

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

import java.util.Properties;

public class configScreen extends SpruceScreen {

    private final Screen parent;

    private final SpruceOption doubleHotbarToggle;
    private final SpruceOption betterMountHudToggle;

    public configScreen(@Nullable Screen parent) {
        super(Text.literal("Armorhud test GUI"));
        this.parent = parent;

        this.doubleHotbarToggle = new SpruceBooleanOption("Double hotbar", () -> config.DOUBLE_HOTBAR, newValue -> config.DOUBLE_HOTBAR = newValue, Text.literal("Toggles double hotbar config"));
        this.betterMountHudToggle = new SpruceBooleanOption("Better mount HUD", () -> config.BETTER_MOUNT_HUD, newValue -> config.BETTER_MOUNT_HUD = newValue, Text.literal("Toggles the better mount hud config"));
    }

    @Override
    protected void init() {
        super.init();

        SpruceOptionListWidget list = new SpruceOptionListWidget(Position.of(0, 34), this.width, this.height - 69);
        list.addOptionEntry(this.doubleHotbarToggle, null);
        list.addOptionEntry(this.betterMountHudToggle, null);

        this.addDrawableChild(list);

        this.addDrawableChild(new SpruceButtonWidget(Position.of(this.width / 2 - 50, this.height - 30), 100, 20, Text.literal("Done"), button -> this.applyChanges()));
    }

    @Override
    public void renderTitle(DrawContext context, int MouseX, int MouseY, float delta) {
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 16777215);
    }

    public void applyChanges() {
        config config = armorHud.CONFIG;
        Properties properties = new Properties();
        config.write(properties);
        config.save();
        this.client.setScreen(parent);
    }

}
