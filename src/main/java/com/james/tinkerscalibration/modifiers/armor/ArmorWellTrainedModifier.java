package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.EventPriority;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

public class ArmorWellTrainedModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> WELL = TConstruct.createKey("well_trained_armor");

    public ArmorWellTrainedModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArmorWellTrainedModifier::onHurt);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(WELL, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(WELL, 0);
            if(level > 0) {
                if(event.getOriginalDamage() > 0)
                {
                    event.setNewDamage(event.getOriginalDamage() * (1 - Math.min(0.6f, RANDOM.nextFloat(0.4f) * level)));
                }
            }
        });
    }


}
