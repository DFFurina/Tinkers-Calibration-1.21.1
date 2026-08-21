package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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

public class ArmorIncandescentModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> INCANDESCENT = TConstruct.createKey("incandescent_armor");

    public ArmorIncandescentModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArmorIncandescentModifier::onHurt);
        NeoForge.EVENT_BUS.addListener(ArmorIncandescentModifier::onUpdateApply);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(INCANDESCENT, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(INCANDESCENT, 0);
            if(level > 0 && attacker != null) {
                attacker.setRemainingFireTicks((int) (event.getOriginalDamage()) * 20);
                attacker.hurt(attacker.damageSources().magic(), event.getOriginalDamage() * 0.1f * level);
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
                        int level = holder.get(INCANDESCENT, 0);
                        if (level > 0) {
                            float range = 5 + 3 * level;
                            List<Mob> ens = living.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(living.getX() - range, living.getY() - range, living.getZ() - range, living.getX() + range, living.getY() + range, living.getZ() + range));
                            if (!ens.isEmpty())
                                for (Mob en : ens) {
                                    if (en == null) continue;
                                    en.setRemainingFireTicks(2 * level * 20);
                                }

                            living.getCommandSenderWorld().addParticle(ParticleTypes.FLAME,
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
