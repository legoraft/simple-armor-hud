/*
package com.armorhud.armor;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

public class TrinketsArmorAccessor implements ArmorAccessor {

    private final ArmorAccessor fallback;
    private final List<SlotFunction> slotFunctions;
    private int pieces;

    public TrinketsArmorAccessor(ArmorAccessor fallback) {
        this.fallback = fallback;
        this.slotFunctions = new ArrayList<>();
    }

    @Override
    public void initialize(ClientPlayerEntity player) {
        slotFunctions.clear();

        // Populate functions with fallback entries first
        pieces = fallback.getPieces(player);
        for (int i = 0; i < pieces; i++) {
            int slot = i;
            slotFunctions.add(entity -> fallback.getArmorPiece(entity, slot));
        }

        Optional<TrinketComponent> componentOpt = TrinketsApi.getTrinketComponent(player);
        if (componentOpt.isEmpty()) {
            return;
        }

        TrinketComponent component = componentOpt.get();
        List<Pair<SlotReference, ItemStack>> equipped = component.getEquipped($ -> true);
        Map<Integer, Integer> offsetMap = new HashMap<>();

        // Populate functions with additional trinkets slots
        for (int i = 0; i < equipped.size(); i++) {
            Pair<SlotReference, ItemStack> pair = equipped.get(i);
            int groupSlotIdx = groupToSlot(pair.getLeft().inventory().getSlotType().getGroup());
            int offset = offsetMap.compute(groupSlotIdx, (k, v) -> v == null ? 1 : v + 1);
            int slot = i;
            slotFunctions.add(groupSlotIdx + offset, entity -> getTrinketsItem(entity, slot));
        }
        pieces += equipped.size();
    }

    private ItemStack getTrinketsItem(ClientPlayerEntity player, int idx) {
        Optional<TrinketComponent> componentOpt = TrinketsApi.getTrinketComponent(player);
        if (componentOpt.isEmpty()) {
            return ItemStack.EMPTY;
        }
        TrinketComponent component = componentOpt.get();
        Pair<SlotReference, ItemStack> pair = component.getEquipped($ -> true).get(idx);
        return pair.getRight();
    }

    @Override
    public ItemStack getArmorPiece(ClientPlayerEntity player, int slotIndex) {
        return slotFunctions.get(slotIndex).apply(player);
    }

    @Override
    public int getPieces(ClientPlayerEntity player) {
        return pieces;
    }

    private int groupToSlot(String group) {
        return switch (group) {
            case "feet" -> 0;
            case "legs" -> 1;
            case "chest" -> 2;
            case "head" -> 3;
            default -> -1;
        };
    }

    private interface SlotFunction extends Function<ClientPlayerEntity, ItemStack> {
    }
}

*/
