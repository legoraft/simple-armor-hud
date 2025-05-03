package com.armorhud.armor;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

public class VanillaArmorAccessor implements ArmorAccessor {

    @Override
    public ItemStack getArmorPiece(ClientPlayerEntity player, int slotIndex) {
        if (slotIndex < 0 || slotIndex >= getPieces(player)) {
            throw new IllegalArgumentException("Invalid slot index: " + slotIndex);
        }
        return player.getInventory().getArmorStack(slotIndex);
    }

    @Override
    public int getPieces(ClientPlayerEntity player) {
        return player.getInventory().armor.size();
    }
}
