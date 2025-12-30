package com.armorhud.config;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class config {

    public static boolean BETTER_MOUNT_HUD = false;
    public static boolean DOUBLE_HOTBAR = false;
    public static boolean ARMOR_HUD = true;
    public static boolean DISABLE_ARMOR_BAR = false;
    public static Position position = Position.FOODBAR;
    public static boolean RTL = false;
    public static boolean TRIM_EMPTY_SLOTS = false; // toggles trimming space between empty armor slots

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("armorhud.properties");

    public enum Position {
        FOODBAR,
        HEALTHBAR,
        HOTBAR_LEFT,
        HOTBAR_RIGHT;

        public Text displayName() {
            return Text.translatable("config.armorposition." + name().toLowerCase());
        }
    }

    public static void write(Properties properties) {
        properties.setProperty("better_mount_hud", Boolean.toString(BETTER_MOUNT_HUD));
        properties.setProperty("double_hotbar", Boolean.toString(DOUBLE_HOTBAR));
        properties.setProperty("armor_hud", Boolean.toString(ARMOR_HUD));
        properties.setProperty("disable_armor_bar", Boolean.toString(DISABLE_ARMOR_BAR));
        properties.setProperty("position", position.name());
        properties.setProperty("right_to_left", Boolean.toString(RTL));
        properties.setProperty("trim_empty_slots", Boolean.toString(TRIM_EMPTY_SLOTS));
    }

    public void read(Properties properties) {
        BETTER_MOUNT_HUD =  Boolean.parseBoolean(properties.getProperty("better_mount_hud", "false"));
        DOUBLE_HOTBAR =  Boolean.parseBoolean(properties.getProperty("double_hotbar", "false"));
        ARMOR_HUD = Boolean.parseBoolean(properties.getProperty("armor_hud", "true"));
        DISABLE_ARMOR_BAR = Boolean.parseBoolean(properties.getProperty("disable_armor_bar", "false"));
        position = Position.valueOf(properties.getProperty("position", "FOODBAR"));
        RTL = Boolean.parseBoolean(properties.getProperty("right_to_left", "false"));
        TRIM_EMPTY_SLOTS = Boolean.parseBoolean(properties.getProperty("trim_empty_slots", "false"));
    }

    public static void save() {
        Properties properties = new Properties();
        write(properties);

        if (!Files.exists(CONFIG_PATH)) {
            try {
                Files.createFile(CONFIG_PATH);
            } catch (IOException e) {
                LogManager.getLogger("Simple Survival Tweaks").error("Failed to create config file");
            }
        }

        try {
            properties.store(Files.newOutputStream(CONFIG_PATH), "Simple Survival Tweaks config file");
        } catch (IOException e) {
            LogManager.getLogger("Simple Survival Tweaks").error("Failed to write config");
        }
    }

    public void load() {
        Properties properties = new Properties();

        if (!Files.exists(CONFIG_PATH)) {
            try {
                Files.createFile(CONFIG_PATH);
                save();
            } catch (IOException e) {
                LogManager.getLogger("Simple Survival Tweaks").error("Failed to create config file");
            }
        }

        try {
            properties.load(Files.newInputStream(CONFIG_PATH));
        } catch (IOException e) {
            LogManager.getLogger("Simple Survival Tweaks").error("Failed to read config");
        }

        read(properties);
    }

}
