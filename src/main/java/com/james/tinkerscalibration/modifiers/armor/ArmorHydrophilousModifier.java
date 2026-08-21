package com.james.tinkerscalibration.modifiers.armor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.neoforged.neoforge.common.NeoForgeMod;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.UUID;
import java.util.function.BiConsumer;

public class ArmorHydrophilousModifier extends Modifier implements AttributesModifierHook {

  private static final String ATTRIBUTE_BONUS = "3af0e77b-d0f3-4300-a5a9-d142cce63b3c";

  @Override
  public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute,AttributeModifier> consumer) {
    consumer.accept(NeoForgeMod.SWIM_SPEED.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_hydrophilous_swim_speed"),  0.15f * modifier.getEffectiveLevel(), Operation.ADD_MULTIPLIED_TOTAL));
  }
  @Override
  protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
    hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES);
  }
}
