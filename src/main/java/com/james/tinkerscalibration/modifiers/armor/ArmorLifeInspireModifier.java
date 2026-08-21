package com.james.tinkerscalibration.modifiers.armor;


import com.james.tinkerscalibration.TinkersCalibrationArmorModifiers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

public class ArmorLifeInspireModifier extends Modifier {

    private static final TinkerDataCapability.TinkerDataKey<Integer> LIFE = TConstruct.createKey("life_armor");

    public ArmorLifeInspireModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorLifeInspireModifier::onUpdateApply);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(LIFE, false, null));
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide && living instanceof Player player && player.totalExperience >= 1 && player.getHealth() <= 4 && player.isAlive()) {
                    TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                        int level = holder.get(LIFE, 0);
                        if (level > 0) {
                            if (context.getToolInSlot(EquipmentSlot.LEGS) instanceof ToolStack tool) {
                                if (tool.getModifierLevel(TinkersCalibrationArmorModifiers.lifeinspire.get()) > 0 && !tool.isBroken()) {
                                    ToolDamageUtil.damageAnimated(tool, level + 1, player);
                                    player.giveExperiencePoints(-10 - level * 10);
                                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * level, level - 1));
                                }
                            }
                            if (context.getToolInSlot(EquipmentSlot.HEAD) instanceof ToolStack tool) {
                                if (tool.getModifierLevel(TinkersCalibrationArmorModifiers.lifeinspire.get()) > 0 && !tool.isBroken()) {
                                    ToolDamageUtil.damageAnimated(tool, level + 1, player);
                                    player.giveExperiencePoints(-10 - level * 10);
                                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * level, level - 1));
                                }
                            }
                            if (context.getToolInSlot(EquipmentSlot.CHEST) instanceof ToolStack tool) {
                                if (tool.getModifierLevel(TinkersCalibrationArmorModifiers.lifeinspire.get()) > 0 && !tool.isBroken()) {
                                    ToolDamageUtil.damageAnimated(tool, level + 1, player);
                                    player.giveExperiencePoints(-10 - level * 10);
                                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * level, level - 1));
                                }
                            }
                            if (context.getToolInSlot(EquipmentSlot.FEET) instanceof ToolStack tool) {
                                if (tool.getModifierLevel(TinkersCalibrationArmorModifiers.lifeinspire.get()) > 0 && !tool.isBroken()) {
                                    ToolDamageUtil.damageAnimated(tool, level + 1, player);
                                    player.giveExperiencePoints(-10 - level * 10);
                                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * level, level - 1));
                                }
                            }

                        }
                    });
                }

            }
        }
    }
}
