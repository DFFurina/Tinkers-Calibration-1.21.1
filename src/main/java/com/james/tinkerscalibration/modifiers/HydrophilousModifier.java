package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nullable;
import java.util.List;

public class HydrophilousModifier extends Modifier implements BreakSpeedModifierHook, MeleeDamageModifierHook, TooltipModifierHook {
    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        Player player = event.getEntity();
        if (player != null && player.isEyeInFluid(FluidTags.WATER)) {
            if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                event.setNewSpeed(event.getNewSpeed()); //this part is done by mixin
            } else if (EnchantmentHelper.getEnchantmentLevel(player.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getHolderOrThrow(net.minecraft.world.item.enchantment.Enchantments.AQUA_AFFINITY), player) == 0) {
                event.setNewSpeed(event.getNewSpeed() * 5);
            } else {
                event.setNewSpeed(event.getNewSpeed() + 5 * modifier.getLevel());
            }
        }
    }

    @Override
    public int getPriority() {
        return 60;
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        Player player = context.getPlayerAttacker();
        if (player != null && player.isEyeInFluid(FluidTags.WATER)) {
            return damage + modifier.getLevel() * 5;
        }
        return damage;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE, ModifierHooks.BREAK_SPEED, ModifierHooks.TOOLTIP);
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null && player.isEyeInFluid(FluidTags.WATER)) {
            TooltipModifierHook.addStatBoost(tool, modifier.getModifier(), ToolStats.ATTACK_DAMAGE, TinkerTags.Items.MELEE, 5 * modifier.getLevel(), tooltip);
            if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                int amplifier = player.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier() + 1;
                double scale, percent;
                if (amplifier <= 3) {
                    scale = Math.pow(3, amplifier);
                    percent = Math.pow(0.3, amplifier);
                } else {
                    scale = Math.pow(3, 4);
                    percent = Math.pow(0.3, 4);
                }
                TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.hydrophilous.mining_speed1"), percent * scale, tooltip);
            } else if (EnchantmentHelper.getEnchantmentLevel(player.level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT).getHolderOrThrow(net.minecraft.world.item.enchantment.Enchantments.AQUA_AFFINITY), player) == 0) {
                TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.hydrophilous.mining_speed2"), 5f, tooltip);
            } else {
                TooltipModifierHook.addStatBoost(tool, modifier.getModifier(), ToolStats.MINING_SPEED, TinkerTags.Items.HARVEST, 5 * modifier.getLevel(), tooltip);
            }


        }
    }
}
