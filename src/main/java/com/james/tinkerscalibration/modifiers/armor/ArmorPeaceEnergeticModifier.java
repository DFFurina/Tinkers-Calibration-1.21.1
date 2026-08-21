package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

public class ArmorPeaceEnergeticModifier extends Modifier {

    private static final TinkerDataCapability.TinkerDataKey<Integer> PEACE = TConstruct.createKey("peace_growing_armor");

    public ArmorPeaceEnergeticModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorPeaceEnergeticModifier::onUpdateApply);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(PEACE, false, null));
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide && living.isAlive() && living.tickCount % 20 == 0) {
                    TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                        int level = holder.get(PEACE, 0);
                        if (level > 0) {
                            Vec3 motion = living.getDeltaMovement();
                            if (motion.x() == 0 && motion.z() == 0)
                                living.heal((float) level / 2);
                        }
                    });
                }
            }
        }
    }
}
