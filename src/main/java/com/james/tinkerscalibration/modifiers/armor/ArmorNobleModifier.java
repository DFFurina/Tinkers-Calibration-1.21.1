package com.james.tinkerscalibration.modifiers.armor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.bus.api.Event;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.UUID;
import java.util.function.BiConsumer;

public class ArmorNobleModifier extends Modifier implements AttributesModifierHook {
    private static final TinkerDataCapability.TinkerDataKey<Integer> NOBLE = TConstruct.createKey("noble");

    public ArmorNobleModifier() {
        NeoForge.EVENT_BUS.addListener(ArmorNobleModifier::onApplyEffect);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES);
    }

    private static void onApplyEffect(MobEffectEvent.Applicable event) {
        LivingEntity living = event.getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(NOBLE, 0);
            if (level > 0 && living instanceof ServerPlayer && event.getEffectInstance() != null) {
                MobEffectInstance instance = event.getEffectInstance();
                if (instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                    if (instance.getAmplifier() + 1 <= level) {
                        event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                    } else
                        event.getEffectInstance().update(new MobEffectInstance(instance.getEffect(), (int) (Math.max(5, (1 - 0.2f * level) * instance.getDuration())), instance.getAmplifier() - level));
                }
            }
        });
    }

    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        consumer.accept(Attributes.LUCK.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_noble_luck"), modifier.getLevel(), AttributeModifier.Operation.ADD_VALUE));
    }
}