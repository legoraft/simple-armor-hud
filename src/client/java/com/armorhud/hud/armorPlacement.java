package com.armorhud.hud;

import com.armorhud.config.config;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class armorPlacement {
    private static final int FOODBAR_X = 91;
    private static final int HEALTHBAR_X = -10;
    private static final int HOTBAR_LEFT_X = -83;
    private static final int HOTBAR_RIGHT_X = 165;

    public static void place(GuiGraphicsExtractor graphics, Minecraft minecraft) {
        int armorHeight = 0;
        armorHeight = calculateArmorHeight(graphics, minecraft, armorHeight);

        switch ( config.position.name() ) {
            case "FOODBAR":
                armorRenderer.renderArmor(graphics, minecraft, armorHeight, FOODBAR_X);
                break;
            case "HEALTHBAR":
                armorRenderer.renderArmor(graphics, minecraft, armorHeight, HEALTHBAR_X);
                break;
            case "HOTBAR_LEFT":
                if ( !minecraft.player.getOffhandItem().isEmpty() ) {
                    armorRenderer.renderArmor(graphics, minecraft, armorHeight, HOTBAR_LEFT_X - 28);
                } else {
                    armorRenderer.renderArmor(graphics, minecraft, armorHeight, HOTBAR_LEFT_X);
                }
                break;
            case "HOTBAR_RIGHT":
                if ( minecraft.options.attackIndicator().get() == AttackIndicatorStatus.HOTBAR ) {
                    armorRenderer.renderArmor(graphics, minecraft, armorHeight, HOTBAR_RIGHT_X + 25);
                } else {
                    armorRenderer.renderArmor(graphics, minecraft, armorHeight, HOTBAR_RIGHT_X);
                }
                break;
        }
    }

    private static int calculateArmorHeight(GuiGraphicsExtractor graphics, Minecraft minecraft, int armorHeight) {
        int scaledHeight = graphics.guiHeight();
        armorHeight = heightPosition(scaledHeight, armorHeight);

        if ( config.position == config.Position.HEALTHBAR ) {
            armorHeight += healthBarPosition(minecraft);
        } else {
            armorHeight += adjustment(minecraft);
        }

        return armorHeight;
    }

    public static int heightPosition(int scaledHeight, int armorHeight) {
        if ( config.position != config.Position.FOODBAR && config.position != config.Position.HEALTHBAR ) {
            switch ( config.position.name() ) {
                case "HOTBAR_LEFT", "HOTBAR_RIGHT":
                    armorHeight = scaledHeight - 19;
                    break;
            }
        } else {
            armorHeight = scaledHeight - ( config.DOUBLE_HOTBAR ? 76 : 55 );
        }

        return armorHeight;
    }

    public static int healthBarPosition(Minecraft minecraft) {
        int armorAdjustment = 0;
        int healthDisplacement = 0;

        if ( minecraft.player.getMaxHealth() + minecraft.player.getMaxAbsorption() < 180 ) {
            int playerHealthRows = (int) Math.ceil( (minecraft.player.getMaxHealth() + minecraft.player.getMaxAbsorption() ) / 20);
            healthDisplacement = (10 * playerHealthRows) - ((playerHealthRows > 2) ? (playerHealthRows - 2) * (playerHealthRows - 1) : 0);

            armorAdjustment = -healthDisplacement;
        }

        if ( minecraft.player.isCreative() ) {
            armorAdjustment = 16 + healthDisplacement;
        } else if ( config.DISABLE_ARMOR_BAR ) {
            armorAdjustment = 10;
        }

        return armorAdjustment;
    }

    private static int adjustment(Minecraft minecraft) {
        int armorAdjustment = 0;
        if ( minecraft.player.getAirSupply() < minecraft.player.getMaxAirSupply() || minecraft.player.isUnderWater() && !minecraft.player.isCreative() ) {
            armorAdjustment = -10;
        }

        if ( minecraft.player.isCreative() ) {
            armorAdjustment = 16;
        }

        if ( minecraft.player.isPassenger() && minecraft.player.getVehicle() != null ) {
            Entity vehicle = minecraft.player.getVehicle();
            float mountHealth = 0;

            if ( vehicle instanceof LivingEntity mount ) {
                mountHealth = mount.getMaxHealth();
            }

            armorAdjustment = -entityMovement.mountAdjustment(minecraft, mountHealth, armorAdjustment);
        }

        return armorAdjustment;
    }
}
