package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Iterator;
import java.util.List;

public class PetramorModifier extends Modifier {
    /*@Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        Iterator<ItemStack> iterator = generatedLoot.iterator();
        while (iterator.hasNext()) {
            ItemStack stack = iterator.next();
            if (stack.is(TinkerTags.Items.STONESHIELDS)) {
                if (RANDOM.nextFloat() <= 0.1f * level)
                    ToolDamageUtil.repair(tool, 5);
            }
        }
        
    }

     */
}
