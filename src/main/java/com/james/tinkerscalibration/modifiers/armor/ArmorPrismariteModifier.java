package com.james.tinkerscalibration.modifiers.armor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.UUID;
import java.util.function.BiConsumer;

public class ArmorPrismariteModifier extends Modifier implements AttributesModifierHook {
    private static final TinkerDataCapability.TinkerDataKey<Integer> WATER = TConstruct.createKey("prismarite_armor");
/*
    public ArmorPrismariteModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorPrismariteModifier::onUpdateApply);
    }

    private static void onUpdateApply(EntityTickEvent.Post evt) {
        LivingEntity living = evt.getEntity();
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide) {
                    living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                        int levels = holder.get(WATER, 0);
                        if (levels > 0) {
                            if (living.hasEffect(MobEffects.REGENERATION) && living.getEffect(MobEffects.REGENERATION).getDuration() <= 100 || !living.hasEffect(MobEffects.REGENERATION)) {
                                living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200));
                            }
                            if (living.hasEffect(MobEffects.DIG_SLOWDOWN) && UpgradedNetheriteConfig.EnableElderGuardianDebuffImmune) {
                                living.removeEffect(MobEffects.DIG_SLOWDOWN);
                            }
                        }
                    });
                }
            }
        }
    }
    */
    private static final String ATTRIBUTE_BONUS = "7a722435-9301-4e45-8f47-e18c1bb97990";

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        consumer.accept(NeoForgeMod.SWIM_SPEED.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_prismarite_swim_speed"),  0.1f * modifier.getEffectiveLevel(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES);
        hookBuilder.addModule(new ArmorLevelModule(WATER, false, null));
    }
}
