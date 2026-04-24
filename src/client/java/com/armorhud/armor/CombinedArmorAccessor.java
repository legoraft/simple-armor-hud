package com.armorhud.armor;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CombinedArmorAccessor implements ArmorAccessor {
    private final List<ArmorAccessor> accessors = new ArrayList<>();

    public void addAccessor(ArmorAccessor accessor) {
        accessors.add(accessor);
    }

    @Override
    public List<ItemStack> getArmorPieces(LocalPlayer player) {
        List<ItemStack> armorList = new ArrayList<>();

        for ( ArmorAccessor accessor : accessors ) {
            armorList.addAll(accessor.getArmorPieces(player));
        }

        return armorList;
    }

    @Override
    public ItemStack getArmorPiece(LocalPlayer player, EquipmentSlot slot) {
        return null;
    }
}
