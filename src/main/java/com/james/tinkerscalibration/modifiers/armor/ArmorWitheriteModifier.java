package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

public class ArmorWitheriteModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> WITHER = TConstruct.createKey("witherite_armor");

    public ArmorWitheriteModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorWitheriteModifier::onApplyEffect);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(WITHER, false, null));
    }
    private static void onApplyEffect(MobEffectEvent.Applicable event) {
        LivingEntity living = event.getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(WITHER, 0);
            if (level > 0 && living instanceof ServerPlayer && event.getEffectInstance() != null) {
                if (event.getEffectInstance().getEffect().value() == MobEffects.WITHER) {
                    event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
                }
            }
        });
    }
}