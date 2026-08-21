package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.NeoForge;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.EventPriority;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

import java.util.List;
import java.util.Random;

public class ArmorExtremeFreezingModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> FREEZING = TConstruct.createKey("extreme_freezing_armor");

    public ArmorExtremeFreezingModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArmorExtremeFreezingModifier::onHurt);
        NeoForge.EVENT_BUS.addListener(ArmorExtremeFreezingModifier::onUpdateApply);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(FREEZING, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(FREEZING, 0);
            if(level > 0 && attacker instanceof LivingEntity attackerl) {

                attackerl.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                attackerl.setTicksFrozen(140 + 60 * level);
            }
        });
    }

    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide && living.isAlive() && living.tickCount % 80 == 0) {
                    TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                        int level = holder.get(FREEZING, 0);
                        if (level > 0) {
                            float range = 5 + 3 * level;
                            List<Mob> ens = living.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(living.getX() - range, living.getY() - range, living.getZ() - range, living.getX() + range, living.getY() + range, living.getZ() + range));
                            if (!ens.isEmpty())
                                for (Mob en : ens) {
                                    if (en == null) continue;
                                    en.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                                    en.setTicksFrozen(140 + 60 * level);
                                }

                            living.getCommandSenderWorld().addParticle(ParticleTypes.SNOWFLAKE,
                                    living.getX() + RANDOM.nextDouble() - 0.5,
                                    living.getY() + RANDOM.nextDouble(),
                                    living.getZ() + RANDOM.nextDouble() - 0.5,
                                    0.0D, 0.25D, 0.0D);
                        }
                    });
                }

            }
        }
    }
}
