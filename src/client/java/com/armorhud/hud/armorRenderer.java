package com.armorhud.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class armorRenderer {
    public static void renderArmorPiece(GuiGraphicsExtractor context, Minecraft minecraft, Player player, float x, float y, ItemStack stack) {
        if ( stack.isEmpty() ) return;

        context.pose().pushMatrix();
        context.pose().translate(x, y);

        context.item(player, stack, 0, 0, 1);

        context.itemDecorations(minecraft.font, stack, 0,0);
        context.pose().popMatrix();
    }
}
