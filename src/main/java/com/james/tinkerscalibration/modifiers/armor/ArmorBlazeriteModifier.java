package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

public class ArmorBlazeriteModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> BLAZERITE = TConstruct.createKey("blazerite_armor");

    public ArmorBlazeriteModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorBlazeriteModifier::onUpdateApply);
        NeoForge.EVENT_BUS.addListener(ArmorBlazeriteModifier::onLivingIncomingDamageEvent);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(BLAZERITE, false, null));
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (living.isAlive()) {
                    TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                        int level = holder.get(BLAZERITE, 0);
                        if (level > 0 && living instanceof Player player) {
                            player.clearFire();
                            //if (UpgradedNetheriteConfig.EnableLavaSpeed && player.isInLava() && !player.getAbilities().flying) {
                            //    player.setDeltaMovement(player.getDeltaMovement().multiply(1.659999966621399, 1.0, 1.659999966621399));
                            //}
                        }
                    });
                }

            }
        }
    }

    public static void onLivingIncomingDamageEvent(LivingIncomingDamageEvent event) {
        LivingEntity living = event.getEntity();
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (living.isAlive()) {
                    TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                        int level = holder.get(BLAZERITE, 0);
                        if (level > 0 && living instanceof Player player) {
                            //if (event.getSource().isFire()) {
                                //if (UpgradedNetheriteConfig.EnableFireImmune) {
                                //    if (event.isCancelable()) {
                                //        event.setCanceled(true);
                                //    }
                                //    player.clearFire();
                                //}
                            //}
                        }
                    });
                }
            }
        }
    }
}
