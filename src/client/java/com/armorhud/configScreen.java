package com.armorhud;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.option.SpruceBooleanOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.Properties;

public class configScreen extends SpruceScreen {

    private final Screen parent;

    private final SpruceOption doubleHotbarToggle;

    public configScreen(Screen screen) {
        super(Text.literal("Armorhud test GUI"));
        parent = screen;

        this.doubleHotbarToggle = new SpruceBooleanOption("Double hotbar", () -> config.DOUBLE_HOTBAR, newValue -> config.DOUBLE_HOTBAR = newValue, Text.literal("Enables double hotbar config"));

    }

    @Override
    protected void init() {
        super.init();

        SpruceOptionListWidget list = new SpruceOptionListWidget(Position.of(0, 34), this.width, this.height - 69);
        list.addOptionEntry(this.doubleHotbarToggle, null);

        this.addDrawableChild(list);

        this.addDrawableChild(new SpruceButtonWidget(Position.of(this.width / 2 - 50, this.height - 20), 100, 20, Text.literal("Done"), button -> this.applyChanges()));
    }

    public void applyChanges() {
        config config = armorHud.CONFIG;
        Properties properties = new Properties();
        config.write(properties);
        config.save();
        this.client.setScreen(parent);
    }

}
