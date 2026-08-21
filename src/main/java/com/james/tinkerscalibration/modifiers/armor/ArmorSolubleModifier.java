package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class ArmorSolubleModifier extends Modifier {

    private static final TinkerDataCapability.TinkerDataKey<Integer> SOLUBLE = TConstruct.createKey("soluble_armor");

    public ArmorSolubleModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorSolubleModifier::onUpdateApply);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(SOLUBLE, false, null));
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide && living.isAlive() && living.tickCount % 10 == 0) {
                    TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                        int level = holder.get(SOLUBLE, 0);
                        if (level > 0) {
                            int bonus = getBonus(living);
                            if (context.getToolInSlot(EquipmentSlot.LEGS) instanceof ToolStack tool) {
                                if (bonus > 0) {
                                    ToolDamageUtil.damageAnimated(tool, bonus, living);
                                    if (living.isEyeInFluid(FluidTags.WATER))
                                    {
                                        living.setDeltaMovement(living.getLookAngle().multiply(0.01, 0, 0.01));
                                    }
                                }
                            }
                            if (context.getToolInSlot(EquipmentSlot.HEAD) instanceof ToolStack tool) {
                                if (bonus > 0) {
                                    ToolDamageUtil.damageAnimated(tool, bonus, living);
                                    if (living.isEyeInFluid(FluidTags.WATER))
                                    {
                                        living.setDeltaMovement(living.getLookAngle().multiply(0.01, 0, 0.01));
                                    }
                                }
                            }
                            if (context.getToolInSlot(EquipmentSlot.CHEST) instanceof ToolStack tool) {
                                if (bonus > 0) {
                                    ToolDamageUtil.damageAnimated(tool, bonus, living);
                                    if (living.isEyeInFluid(FluidTags.WATER))
                                    {
                                        living.setDeltaMovement(living.getLookAngle().multiply(0.01, 0, 0.01));
                                    }
                                }
                            }
                            if (context.getToolInSlot(EquipmentSlot.FEET) instanceof ToolStack tool) {
                                if (bonus > 0) {
                                    ToolDamageUtil.damageAnimated(tool, bonus, living);
                                    if (living.isEyeInFluid(FluidTags.WATER))
                                    {
                                        living.setDeltaMovement(living.getLookAngle().multiply(0.01, 0, 0.01));
                                    }
                                }
                            }

                        }
                    });
                }

            }
        }
    }
    private static int getBonus(LivingEntity living) {
        int bonus = 0;
        Level world = living.getCommandSenderWorld();
        if (living.isEyeInFluid(FluidTags.WATER)) {
            bonus = 4;
        } else if (living.getCommandSenderWorld().isRainingAt(living.blockPosition())) {
            bonus = 2;
        }
        return bonus * Math.round(world.getBiome(living.getOnPos()).value().getBaseTemperature());
    }
}
