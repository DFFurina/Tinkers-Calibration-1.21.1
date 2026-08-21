package com.james.tinkerscalibration.modifiers.armor;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ProtectionModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nonnull;

public class ArmorDenseModifier extends Modifier implements ProtectionModifierHook {
    public float getProtectionModifier(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull EquipmentContext context, @Nonnull EquipmentSlot slotType, DamageSource source, float modifierValue) {
        if (!source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            if(context.getEntity().getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
                modifierValue += (float) context.getEntity().getAttribute(Attributes.ARMOR_TOUGHNESS).getValue() * 2f;
        }

        return modifierValue;
    }

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROTECTION);
    }
}
