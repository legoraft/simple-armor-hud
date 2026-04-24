package com.armorhud.armor;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ArmorAccessor {

    default void initialize(LocalPlayer player) { }
    default String getName() {
        return this.getClass().getSimpleName();
    }

    List<ItemStack> getArmorPieces(LocalPlayer player);
}
