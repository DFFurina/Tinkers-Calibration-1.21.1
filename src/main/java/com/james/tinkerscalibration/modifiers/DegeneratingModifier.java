package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class DegeneratingModifier extends Modifier {

    /*@Override
    public void onInventoryTick(IToolStackView tool, int level, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder.tickCount % 80 == 0 && !tool.isBroken()) {
            int amount = 0;

            int durMax = tool.getStats().getInt(ToolStats.DURABILITY);
            int durNow = tool.getCurrentDurability();
            int durHalf = durMax / 2;
            if (durNow > durHalf) amount = 1;
            else if (durNow < durHalf) amount = -1;

            if (amount != 0)
                ToolDamageUtil.directDamage(tool, amount, holder, stack);
        }
    }

     */
}
