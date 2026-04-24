package com.armorhud.armor;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AccessoriesArmorAccessor extends ArmorAccessor {
    @Override
    public String getName() {
        return "Accessories";
    }

    @Override
    public List<ItemStack> getArmorPieces(LocalPlayer player) {
        return null;
    }
}
