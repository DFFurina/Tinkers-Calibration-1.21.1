package com.james.tinkerscalibration.modifiers.armor;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ProtectionModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nonnull;

public class ArmorBlacksmithModifier extends Modifier implements ToolStatsModifierHook, ProtectionModifierHook {
    public float getProtectionModifier(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull EquipmentContext context, @Nonnull EquipmentSlot slotType, DamageSource source, float modifierValue) {
        if (!source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            LivingEntity living = context.getEntity();
            if (living.getY() <= 64 && living.getY() >= 0) {
                modifierValue *= 1 + (float)(64 - living.getY()) * 0.01f;
            }
        }

        return modifierValue;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS, ModifierHooks.PROTECTION);
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.DURABILITY.multiply(builder, 1 + 0.08 * modifier.getLevel());
    }
}

