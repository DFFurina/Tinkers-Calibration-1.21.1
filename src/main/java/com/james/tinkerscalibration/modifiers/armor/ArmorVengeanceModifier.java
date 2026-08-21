package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.EventPriority;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

public class ArmorVengeanceModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> VEN = TConstruct.createKey("vengeance_armor");

    public ArmorVengeanceModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArmorVengeanceModifier::onHurt);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(VEN, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(VEN, 0);
            if(level > 0)
                if (living instanceof Player player && attacker != null && living.getLastHurtByMob() != null && attacker.getType() == player.getLastHurtByMob().getType() && RANDOM.nextFloat() <= level * 0.3f && !(attacker instanceof Guardian)) {
                    if(event.getSource().is(DamageTypes.THORNS)) return;
                    attacker.hurt(attacker.damageSources().thorns(player), event.getOriginalDamage() * 1.5f);
                }
        });
    }
}
