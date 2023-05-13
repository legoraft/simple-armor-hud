package com.survivaltweaks;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class config {

    public static boolean SURVIVAL_DEBUG_STICK = false;
    public static boolean NO_EXPENSIVE = false;
    public static boolean CHEAP_RENAME = false;

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("survivaltweaks.properties");

    public void write(Properties properties) {
        properties.setProperty("survival_debug_stick", Boolean.toString(SURVIVAL_DEBUG_STICK));
        properties.setProperty("no_too_expensive", Boolean.toString(NO_EXPENSIVE));
        properties.setProperty("cheap_rename", Boolean.toString(CHEAP_RENAME));
    }

    public void read(Properties properties) {
        SURVIVAL_DEBUG_STICK = Boolean.parseBoolean(properties.getProperty("survival_debug_stick"));
        NO_EXPENSIVE = Boolean.parseBoolean(properties.getProperty("no_too_expensive"));
        CHEAP_RENAME = Boolean.parseBoolean(properties.getProperty("cheap_rename"));
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
