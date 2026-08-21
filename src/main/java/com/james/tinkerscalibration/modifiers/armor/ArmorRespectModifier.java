package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.EventPriority;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

public class ArmorRespectModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> RESPECT = TConstruct.createKey("respect_armor");

    public ArmorRespectModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArmorRespectModifier::onHurt);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(RESPECT, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(RESPECT, 0);
            if(level > 0 && living instanceof Player player && attacker != null) {
                Vec3 vec3 = player.getViewVector(1.0F).normalize();
                Vec3 vec31 = new Vec3(living.getX() - attacker.getX(), living.getEyeY() - attacker.getEyeY(), living.getZ() - attacker.getZ());
                double d0 = vec31.length();
                vec31 = vec31.normalize();
                double d1 = vec3.dot(vec31);
                if (d1 <= 1.0D - 0.025D / d0 && !player.hasLineOfSight(attacker)) {
                    event.setNewDamage(event.getOriginalDamage() * (1 - 0.2f * Math.min(3, level)));
                }
            }
        });
    }
}
