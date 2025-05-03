package com.armorhud.armor;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public interface ArmorAccessor {

    default void initialize(ClientPlayerEntity player) {
    }

    ItemStack getArmorPiece(ClientPlayerEntity player, EquipmentSlot slot);

    int getPieces(ClientPlayerEntity player);
}
