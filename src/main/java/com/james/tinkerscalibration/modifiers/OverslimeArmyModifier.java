package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class OverslimeArmyModifier extends Modifier implements TooltipModifierHook, MeleeDamageModifierHook, BreakSpeedModifierHook, ConditionalStatModifierHook {
    @Override
    public int getPriority() {
        return 70;
    }

    @Override
    public void onBreakSpeed(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull PlayerEvent.BreakSpeed event, @Nonnull Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        OverslimeModifier overslime = TinkerModifiers.overslime.get();
        int current = overslime.getShield(tool);
        if (current > 0) {
            event.setNewSpeed((float) (event.getNewSpeed() + Math.cbrt(current * tool.getMultiplier(ToolStats.MINING_SPEED))));
        }

    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.MELEE_DAMAGE, ModifierHooks.BREAK_SPEED, ModifierHooks.CONDITIONAL_STAT);
    }

    @Override
    public float getMeleeDamage(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull ToolAttackContext context, float baseDamage, float damage) {
        OverslimeModifier overslime = TinkerModifiers.overslime.get();
        int current = overslime.getShield(tool);
        if (current > 0) {
            return (float) (damage + Math.cbrt(current * tool.getMultiplier(ToolStats.ATTACK_DAMAGE)));
        }
        return damage;
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        if (stat == ToolStats.DRAW_SPEED) {
            OverslimeModifier overslime = TinkerModifiers.overslime.get();
            float current = overslime.getShield(tool);
            return (float) (baseValue + Math.cbrt(current * 0.004 * tool.getMultiplier(ToolStats.DRAW_SPEED)));
        }
        return baseValue;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        OverslimeModifier overslime = TinkerModifiers.overslime.get();
        int current = overslime.getShield(tool);
        boolean harvest = tool.hasTag(TinkerTags.Items.HARVEST);
        if (harvest || tool.hasTag(TinkerTags.Items.RANGED)) {
            if (player != null && tooltipKey == TooltipKey.SHIFT) {
                if (harvest) {
                    TooltipModifierHook.addStatBoost(tool, modifier.getModifier(), ToolStats.ATTACK_DAMAGE, TinkerTags.Items.MELEE, (float) Math.cbrt(current), tooltip);
                    TooltipModifierHook.addStatBoost(tool, modifier.getModifier(), ToolStats.MINING_SPEED, TinkerTags.Items.HARVEST, (float) Math.cbrt(current), tooltip);
                } else {
                    TooltipModifierHook.addStatBoost(tool, modifier.getModifier(), ToolStats.DRAW_SPEED, TinkerTags.Items.RANGED, (float) Math.cbrt(current * 0.004), tooltip);
                }
            }
        }
    }

}


