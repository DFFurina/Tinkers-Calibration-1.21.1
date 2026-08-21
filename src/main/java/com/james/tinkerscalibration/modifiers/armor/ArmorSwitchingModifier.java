package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.core.BlockPos;
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

public class ArmorSwitchingModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> SWITCH = TConstruct.createKey("switching_armor");

    public ArmorSwitchingModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArmorSwitchingModifier::onHurt);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(SWITCH, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(SWITCH, 0);
            if (level > 0 && attacker != null && event.getOriginalDamage() > 0 && living.isAlive() && attacker.isAlive()) {
                BlockPos posHolder = living.getOnPos();
                BlockPos posTarget = attacker.getOnPos();
                attacker.moveTo(posHolder.getX(), posHolder.getY(), posHolder.getZ());
                living.moveTo(posTarget.getX(), posTarget.getY(), posTarget.getZ());
            }
        });
    }
}
