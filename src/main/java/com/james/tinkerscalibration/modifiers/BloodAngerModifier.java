package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;

import javax.annotation.Nonnull;

import static slimeknights.tconstruct.library.tools.stat.ToolStats.DRAW_SPEED;
import static slimeknights.tconstruct.library.tools.stat.ToolStats.VELOCITY;

public class BloodAngerModifier extends Modifier {
    /*@Override
    public void onInventoryTick(@Nonnull IToolStackView tool, int level, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder.tickCount % 20 == 0 && holder.getUseItem() != stack && isSelected && holder.getHealth() <= 6) {
            holder.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 2));
        }
    }
     */
}
