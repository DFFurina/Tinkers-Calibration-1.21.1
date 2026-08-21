package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
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

import java.util.Collection;

public class ArmorOracularModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> ORACULAR = TConstruct.createKey("oracular_armor");

    public ArmorOracularModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArmorOracularModifier::onHurt);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(ORACULAR, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            Collection<MobEffectInstance> listEffect1;
            Collection<MobEffectInstance> listEffect2;
            int level = holder.get(ORACULAR, 0);
            if (level > 0) {
                if (attacker instanceof LivingEntity attackerl && living != attackerl) {
                    listEffect1 = attackerl.getActiveEffects();
                    if (!listEffect1.isEmpty()) {
                        for (int i = 0; i < listEffect1.size(); i++) {
                            MobEffectInstance effect = listEffect1.stream().toList().get(i);
                            if (effect != null) {
                                if (effect.getEffect().value().isBeneficial()) {
                                    attackerl.removeEffect(effect.getEffect());
                                    attackerl.getCommandSenderWorld().addParticle(
                                            ParticleTypes.HAPPY_VILLAGER,
                                            attacker.getX() + RANDOM.nextDouble() - 0.5,
                                            attacker.getY() + 1,
                                            attacker.getZ() + RANDOM.nextDouble() - 0.5,
                                            0, 0, 0);
                                }
                            }
                        }
                    }
                }
                listEffect2 = living.getActiveEffects();
                if (!listEffect2.isEmpty()) {
                    for (int i = 0; i < listEffect2.size(); i++) {
                        MobEffectInstance effect = listEffect2.stream().toList().get(i);
                        if(effect != null) {
                            if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                                living.removeEffect(effect.getEffect());
                                living.getCommandSenderWorld().addParticle(
                                        ParticleTypes.HAPPY_VILLAGER,
                                        living.getX() + RANDOM.nextDouble() - 0.5,
                                        living.getY() + 1,
                                        living.getZ() + RANDOM.nextDouble() - 0.5,
                                        0, 0, 0);
                            }
                        }
                    }
                }

            }
        });
    }
}
