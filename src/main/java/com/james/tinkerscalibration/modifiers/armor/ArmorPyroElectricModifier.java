package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.EventPriority;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

import java.util.List;

public class ArmorPyroElectricModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> PYRO = TConstruct.createKey("pyro_armor");

    public ArmorPyroElectricModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, ArmorPyroElectricModifier::onHurt);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(PYRO, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(PYRO, 0);
            if (level > 0) {
                if (event.getOriginalDamage() != 0) { 
                    BlockPos pos = living.getOnPos();
                    Level world = living.getCommandSenderWorld();
                    float temp = world.getBiome(pos).value().getBaseTemperature();
                    if (living.isOnFire()) temp += 0.3f; // new flavor

                    if (temp < 0.15) return;

                    final float damage = temp * 3;

                    List<Mob> list = world.getEntitiesOfClass(Mob.class, new AABB(living.getX() - 5 * level, living.getY() - 5 * level, living.getZ() - 5 * level, living.getX() + 5 * level, living.getY() + 5 * level, living.getZ() + 5 * level));
                    if (!world.isClientSide) // server - deal damage
                    {
                        for (Mob en : list) {
                            if (en == living) continue;
                            en.invulnerableTime = 0;
                            en.hurt(living.damageSources().lightningBolt(), damage);
                        }
                    }
                }
            }
        });
    }
}
