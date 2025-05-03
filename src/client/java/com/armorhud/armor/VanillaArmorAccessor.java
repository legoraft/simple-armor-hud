package com.armorhud.armor;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class VanillaArmorAccessor implements ArmorAccessor {

    public ItemStack getArmorPiece(ClientPlayerEntity player, EquipmentSlot slot) {
        if (!slot.isArmorSlot()) {
            throw new IllegalArgumentException("Invalid slot type: " + slot);
        }

        return player.getEquippedStack(slot);
    }
}
