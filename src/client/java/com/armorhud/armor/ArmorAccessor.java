package com.armorhud.armor;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ArmorAccessor {

    default void initialize(LocalPlayer player) { }

    List<ItemStack> getArmorPieces(LocalPlayer player);
    ItemStack getArmorPiece(LocalPlayer player, EquipmentSlot slot);
}
