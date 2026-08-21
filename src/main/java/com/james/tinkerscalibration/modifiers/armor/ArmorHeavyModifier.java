package com.james.tinkerscalibration.modifiers.armor;


import com.james.tinkerscalibration.Utils;
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

public class ArmorHeavyModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> HEAVY = TConstruct.createKey("heavy");

    public ArmorHeavyModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, ArmorHeavyModifier::onHurt);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(HEAVY, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(HEAVY, 0);
            if (level > 0 && attacker instanceof LivingEntity attackerl) {
                if (event.getOriginalDamage() != 0) {
                    Utils.heavyEffect.get().apply(attackerl, 5 * 20, level, true);
                }
            }
        });
    }
}
