package com.james.tinkerscalibration.modifiers.armor;


import com.james.tinkerscalibration.Utils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

public class ArmorDashModifier extends Modifier{
    private static final TinkerDataCapability.TinkerDataKey<Integer> DASH = TConstruct.createKey("dash_armor");

    public ArmorDashModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorDashModifier::onUpdateApply);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(DASH, false, null));
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide && living.isAlive() && living.tickCount % 10 == 0 && living.isSprinting()) {
                    AttributeInstance attributeInstance = living.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (attributeInstance != null) {
                        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                            int levels = holder.get(DASH, 0);
                            if (levels > 0) {
                                int effectLevel = Math.min(31, Utils.dashArmorEffect.get().getLevel(living) + 1);
                                Utils.dashArmorEffect.get().apply(living, 30, effectLevel, true);
                            }

                        });
                    }
                }

            }
        }
    }

}
