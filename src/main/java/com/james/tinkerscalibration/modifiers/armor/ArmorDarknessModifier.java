package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

import static com.james.tinkerscalibration.modifiers.DarknessModifier.getLight;

public class ArmorDarknessModifier extends Modifier{
    private static final TinkerDataCapability.TinkerDataKey<Integer> DARKNESS = TConstruct.createKey("darkness_armor");

    public ArmorDarknessModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorDarknessModifier::onUpdateApply);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(DARKNESS, false, null));
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide) {
                    TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                        int levels = holder.get(DARKNESS, 0);
                        if (levels > 0) {
                            int light = getLight(living.getCommandSenderWorld(), living.blockPosition());
                            if(light <= 9) {
                                if(living.hasEffect(MobEffects.DAMAGE_RESISTANCE) && living.getEffect(MobEffects.DAMAGE_RESISTANCE).getDuration() <= 100 || !living.hasEffect(MobEffects.DAMAGE_RESISTANCE))
                                    living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200));
                            }
                        }
                    });
                }

            }
        }
    }

}
