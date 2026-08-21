package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nonnull;

public class VegetateModifier extends Modifier {
    /*@Override
    public void onInventoryTick(@Nonnull IToolStackView tool, int level, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        ;
        if (!world.isClientSide && holder.tickCount % 80 == 0 && holder.getUseItem() != stack && isSelected) {
            if (RANDOM.nextFloat() < (level * 0.25) && !tool.isBroken()) {
                ToolDamageUtil.repair(tool, 1);
            }
        }
    }

     */
}
