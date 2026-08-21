package com.james.tinkerscalibration.modifiers.armor;


import com.james.tinkerscalibration.Utils;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

public class ArmorBloodThirstyModifier extends Modifier{
    private static final TinkerDataCapability.TinkerDataKey<Integer> BLOOD = TConstruct.createKey("bloodthirsty_armor");

    public ArmorBloodThirstyModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorBloodThirstyModifier::onHurt);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(BLOOD, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int levels = holder.get(BLOOD, 0);
            if (levels > 0 && event.getOriginalDamage() > 0) {
                int effectLevel = Math.min(7, Utils.bloodArmorEffect.get().getLevel(living) + 1);
                Utils.bloodArmorEffect.get().apply(living, 100, effectLevel, true);
            }
        });
    }
}
