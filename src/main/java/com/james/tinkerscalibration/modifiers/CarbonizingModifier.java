package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.common.Tags;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Iterator;
import java.util.List;


public class CarbonizingModifier extends Modifier implements ProcessLootModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT);
    }
    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        Iterator<ItemStack> iterator = generatedLoot.iterator();
        float chance = modifier.getLevel() * 0.05f;
        while (iterator.hasNext()) {
            ItemStack stack = iterator.next();
            if (stack.is(net.neoforged.neoforge.common.Tags.Items.STONES)) {
                if (chance >= 1.0f) {
                    generatedLoot.add(new ItemStack(Items.COAL));
                } else {
                    for (int i = 0; i < stack.getCount(); i++) {
                        if (RANDOM.nextFloat() < chance) {
                            generatedLoot.add(new ItemStack(Items.COAL));
                        }
                    }
                }
            }
        }
    }
}
