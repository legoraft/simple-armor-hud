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

    public int getPieces(ClientPlayerEntity player) {
        int armorCount = 0;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmorSlot()) {
                if (!player.getEquippedStack(slot).isEmpty()) {
                    armorCount++;
                }
            }
        }

        return armorCount;
    }
}
