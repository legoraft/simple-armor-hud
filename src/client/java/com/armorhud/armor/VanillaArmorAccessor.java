package com.armorhud.armor;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class VanillaArmorAccessor implements ArmorAccessor {

    public ItemStack getArmorPiece(LocalPlayer player, EquipmentSlot slot) {
        if (!slot.isArmor()) {
            throw new IllegalArgumentException("Invalid slot type: " + slot);
        }

        return player.getItemBySlot(slot);
    }
}
