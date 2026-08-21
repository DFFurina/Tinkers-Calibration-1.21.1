package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.EventPriority;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

public class ArmorFamiliarModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> FAMILIAR = TConstruct.createKey("familiar_armor");

    public ArmorFamiliarModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArmorFamiliarModifier::onHurt);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(FAMILIAR, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(FAMILIAR, 0);
            if(level > 0)
                if (living instanceof Player player && attacker != null && player.getLastHurtByMob() != null && attacker.getType() == player.getLastHurtByMob().getType()) {
                    event.setNewDamage(event.getOriginalDamage() * (1 - 0.15f * level));
                }
        });
    }
}
