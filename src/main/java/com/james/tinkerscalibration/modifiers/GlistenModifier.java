package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class GlistenModifier extends Modifier implements ProcessLootModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT);
    }
    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        int level = modifier.getLevel();
        int ran = RANDOM.nextInt(999);
        if(ran == 0 && RANDOM.nextFloat() <= 0.1f * level)
        {
            generatedLoot.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
        }
        else if(ran == 1 && RANDOM.nextFloat() <= 0.1f * level)
        {
            generatedLoot.add(new ItemStack(Items.NETHER_STAR));
        }
        else if(ran <= 11 && RANDOM.nextFloat() <= 0.15f * level)
        {
            generatedLoot.add(new ItemStack(Items.DIAMOND));
        }
        else if(ran <= 21 && RANDOM.nextFloat() <= 0.15f * level)
        {
            generatedLoot.add(new ItemStack(Items.EMERALD));
        }
        else if(ran <= 121)
        {
            generatedLoot.add(new ItemStack(Items.GOLD_NUGGET));
        }
        else if(ran <= 221)
        {
            generatedLoot.add(new ItemStack(Items.IRON_NUGGET));
        }
        
    }
}