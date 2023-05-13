package com.armorhud;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class config {

    public static boolean BETTER_MOUNT_HUD = false;
    public static boolean DOUBLE_HOTBAR = false;

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("armorhud.properties");

    public void write(Properties properties) {
        properties.setProperty("better_mount_hud", Boolean.toString(BETTER_MOUNT_HUD));
        properties.setProperty("double_hotbar", Boolean.toString(DOUBLE_HOTBAR));
    }

    public void read(Properties properties) {
        BETTER_MOUNT_HUD =  Boolean.parseBoolean(properties.getProperty("better_mount_hud"));
        DOUBLE_HOTBAR =  Boolean.parseBoolean(properties.getProperty("double_hotbar"));
    }

    public void save() {
        Properties properties = new Properties();
        write(properties);
        if (!Files.exists(CONFIG_PATH)) {
            try {
                Files.createFile(CONFIG_PATH);
            } catch (IOException e) {
                LogManager.getLogger("Simple Survival Tweaks").error("Failed to create config file");
                e.printStackTrace();
            }
        }
        try {
            properties.store(Files.newOutputStream(CONFIG_PATH), "Simple Survival Tweaks config file");
        } catch (IOException e) {
            LogManager.getLogger("Simple Survival Tweaks").error("Failed to write config");
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
        try {
            properties.load(Files.newInputStream(CONFIG_PATH));
        } catch (IOException e) {
            LogManager.getLogger("Simple Survival Tweaks").error("Failed to read config");
            e.printStackTrace();
        }
        read(properties);
    }

}
