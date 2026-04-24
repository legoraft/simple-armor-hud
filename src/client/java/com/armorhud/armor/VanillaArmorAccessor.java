package com.armorhud.armor;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VanillaArmorAccessor implements ArmorAccessor {

    @Override
    public String getName() {
        return "Vanilla";
    }

    @Override
    public List<ItemStack> getArmorPieces(LocalPlayer player) {
        List<ItemStack> armorList = new ArrayList<>();

        for ( EquipmentSlot slot : EquipmentSlot.values() ) {
            if ( slot.isArmor() ) {
                armorList.add(player.getItemBySlot(slot));
            }
        }

        return armorList;
    }
}
