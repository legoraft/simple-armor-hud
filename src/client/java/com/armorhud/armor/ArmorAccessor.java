package com.armorhud.armor;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public interface ArmorAccessor {

    default void initialize(LocalPlayer player) {
    }

    ItemStack getArmorPiece(LocalPlayer player, EquipmentSlot slot);
}
