package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.EventPriority;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class ArmorEnderferenceModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> ENDERFERENCE = TConstruct.createKey("heavy");

    public ArmorEnderferenceModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, ArmorEnderferenceModifier::onHurt);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(ENDERFERENCE, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(ENDERFERENCE, 0);
            if (level > 0 && attacker instanceof LivingEntity attackerl) {
                if (event.getOriginalDamage() != 0) {
                     attackerl.addEffect(new MobEffectInstance((net.minecraft.core.Holder<MobEffect>)(net.minecraft.core.Holder<?>)TinkerModifiers.enderferenceEffect.getHolder(), 60));
                }
            }
        });
    }
}
