package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

import java.util.List;

public class ArmorHyperModifier extends Modifier{
    private static final TinkerDataCapability.TinkerDataKey<Integer> HYPER = TConstruct.createKey("hyper_armor");

    public ArmorHyperModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorHyperModifier::onUpdateApply);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(HYPER, false, null));
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide && living.isAlive() && living.tickCount % 10 == 0 && living.isSprinting()) {
                    AttributeInstance attributeInstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeInstance != null) {
                        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                            int levels = holder.get(HYPER, 0);
                            if (levels > 0) {
                                 List<LivingEntity> mobs = living.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, living.getBoundingBox().inflate(5.0D + levels, 5.0D + levels, 5.0D + levels));
                                 int sum = 0;
                                 for(LivingEntity mob : mobs)
                                     if(mob instanceof Enemy) sum++;
                                 if (sum > 0)
                                 {
                                     living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, Math.round(sum / 3f) - 1));
                                     living.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 60, Math.round(sum / 3f) - 1));
                                 }
                            }

                        });
                    }
                }

            }
        }
    }

}
