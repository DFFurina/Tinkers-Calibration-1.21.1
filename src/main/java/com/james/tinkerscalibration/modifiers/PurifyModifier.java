package com.james.tinkerscalibration.modifiers;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Iterator;
import java.util.List;

public class PurifyModifier extends Modifier {
    /*@Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        Iterator<ItemStack> iterator = generatedLoot.iterator();
        float chance = modifier.getLevel() * 0.20f;
        while (iterator.hasNext()) {
            ItemStack stack = iterator.next();
            if (stack.is(Item.byBlock(BlockInit.VINTEUM_ORE.get()))) {
                if (chance >= 1.0f) {
                    generatedLoot.add(new ItemStack(ItemInit.PURIFIED_VINTEUM_DUST.get()));
                } else {
                    for (int i = 0; i < stack.getCount(); i++) {
                        if (RANDOM.nextFloat() < chance) {
                            generatedLoot.add(new ItemStack(ItemInit.PURIFIED_VINTEUM_DUST.get()));
                        }
                    }
                }
            }
        }
        if (context.hasParam(LootContextParams.DAMAGE_SOURCE)) {
            Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
            if (entity != null) {
                if (RANDOM.nextFloat() <= chance) {
                    generatedLoot.add(new ItemStack(ItemInit.CHIMERITE_GEM.get()));
                }
            }
        }
        
    }

     */
}
