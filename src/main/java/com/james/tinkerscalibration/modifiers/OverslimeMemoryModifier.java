package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

import javax.annotation.Nonnull;

public class OverslimeMemoryModifier extends Modifier {
    /*@Override
    public void onInventoryTick(@Nonnull IToolStackView tool, int level, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        OverslimeModifier overslime = TinkerModifiers.overslime.get();
        int currentslime = overslime.getOverslime(tool);
        int current = tool.getCurrentDurability();
        if (!world.isClientSide && holder.tickCount % 20 == 0 && currentslime >= 0) {
            if (RANDOM.nextFloat() < level * 0.1 && current < tool.getStats().get(ToolStats.DURABILITY)) {
                ToolDamageUtil.repair(tool, 1);
                overslime.addOverslime(tool, -1);
            }
        }
    }

     */
}
