package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

import java.util.UUID;
import java.util.function.BiConsumer;

public class ArmorOverslimeArmyModifier extends Modifier implements AttributesModifierHook {

    public ArmorOverslimeArmyModifier() {
        super();
    }

    public boolean isArmor(EquipmentSlot slot)
    {
        return slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
    }

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES);
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (isArmor(slot)) {
            OverslimeModifier overslime = TinkerModifiers.overslime.get();
            int current = overslime.getShield(tool);
            if (current != 0) {
                consumer.accept(Attributes.ARMOR.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_overslime_army_armor"), Math.cbrt(current * 0.8), AttributeModifier.Operation.ADD_VALUE));
                consumer.accept(Attributes.ARMOR_TOUGHNESS.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_overslime_army_toughness"), Math.cbrt(current * 0.8), AttributeModifier.Operation.ADD_VALUE));
                consumer.accept(Attributes.KNOCKBACK_RESISTANCE.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_overslime_army_knockback"), Math.cbrt(current * 0.0012), AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }


}
