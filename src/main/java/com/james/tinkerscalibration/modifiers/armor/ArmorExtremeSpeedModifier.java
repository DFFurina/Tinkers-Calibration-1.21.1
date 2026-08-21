package com.james.tinkerscalibration.modifiers.armor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.UUID;
import java.util.function.BiConsumer;

public class ArmorExtremeSpeedModifier extends Modifier implements AttributesModifierHook, ToolDamageModifierHook {
    public boolean isArmor(EquipmentSlot slot)
    {
        return slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (isArmor(slot)) {
            consumer.accept(Attributes.MOVEMENT_SPEED.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_extreme_speed"), modifier.getLevel() * 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES, ModifierHooks.TOOL_DAMAGE);
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, LivingEntity holder) {
        return amount + modifier.getLevel();
    }

}