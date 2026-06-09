package com.armorhud.hud;

import com.armorhud.armor.ArmorAccessor;
import com.armorhud.armorHud;
import com.armorhud.config.config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class armorRenderer {
    public static void renderArmor(GuiGraphicsExtractor graphics, Minecraft minecraft, int armorHeight, int startXPosition) {
        int scaledWidth = graphics.guiWidth();

        assert minecraft.player != null;
        ArmorAccessor armorAccessor = armorHud.getArmorAccessor();
        List<ItemStack> armorPieces = armorAccessor.getArmorPieces(minecraft.player);

        final int hungerWidth = 14; // Magic number to center 4 armor pieces
        final int armorWidth = 15;

//      If we trim empty slots, check all slots if they're empty
        int emptyArmorSlots = 0;
        if ( config.TRIM_EMPTY_SLOTS ) {
            for ( ItemStack stack : armorPieces ) {
                if ( stack.isEmpty() ) {
                    emptyArmorSlots++;
                }
            }
        }

//      Magical starting positions
        float hungerX = scaledWidth / 2f + startXPosition;
        float x = hungerX + hungerWidth - (7 * emptyArmorSlots) + 2 - (armorWidth * 2);

//      Armor x position calculations
        if (config.RTL) {
            if ( config.TRIM_EMPTY_SLOTS ) {
                x -= (float) (( (float) armorWidth / 2 ) + 0.5);
            }

            x += armorWidth;
            armorPieces = armorPieces.reversed();

            for ( ItemStack armor : armorPieces ) {
                if ( config.TRIM_EMPTY_SLOTS && armor.isEmpty() ) continue;

                x -= armorWidth;
                renderArmorPiece(graphics, minecraft, minecraft.player, x, armorHeight, armor);
            }
        } else {
            if ( config.TRIM_EMPTY_SLOTS ) {
                x += ( (float) hungerWidth / 2 );
            }

            for ( ItemStack armor : armorPieces ) {
                if (config.TRIM_EMPTY_SLOTS && armor.isEmpty()) continue;

                x -= armorWidth;
                renderArmorPiece(graphics, minecraft, minecraft.player, x, armorHeight, armor);
            }
        }
    }

    public static void renderArmorPiece(GuiGraphicsExtractor graphics, Minecraft minecraft, Player player, float x, float y, ItemStack stack) {
        if ( stack.isEmpty() ) return;

        graphics.pose().pushMatrix();
        graphics.pose().translate(x, y);

        graphics.item(player, stack, 0, 0, 1);

        graphics.itemDecorations(minecraft.font, stack, 0,0);
        graphics.pose().popMatrix();
    }
}
