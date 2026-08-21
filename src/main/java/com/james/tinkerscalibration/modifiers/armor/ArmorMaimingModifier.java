package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

public class ArmorMaimingModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> MAIMING = TConstruct.createKey("maiming_armor");

    public ArmorMaimingModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, ArmorMaimingModifier::onHurt);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(MAIMING, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(MAIMING, 0);
            if (level > 0 && attacker instanceof LivingEntity attackerl) {
                if (event.getOriginalDamage() != 0) {
                    attackerl.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
                    attackerl.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, 2));
                }
            }
        });
    }
}
