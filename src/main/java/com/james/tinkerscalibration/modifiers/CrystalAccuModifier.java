package com.james.tinkerscalibration.modifiers;

import net.minecraft.network.chat.Component;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.mantle.client.TooltipKey;

import javax.annotation.Nullable;
import java.util.List;

import static slimeknights.tconstruct.library.tools.stat.ToolStats.USE_ITEM_SPEED;

public class CrystalAccuModifier extends Modifier implements TooltipModifierHook, ToolStatsModifierHook, ConditionalStatModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.TOOL_STATS, ModifierHooks.CONDITIONAL_STAT);
    }
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.ACCURACY.add(builder, 0.1 * modifier.getLevel());
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        if (stat == USE_ITEM_SPEED) {
            return (float) (baseValue * (1 - modifier.getLevel() * 0.2));
        }
        return 0;
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        boolean ranged = tool.hasTag(TinkerTags.Items.RANGED);
        if (tooltipKey == TooltipKey.SHIFT) {
            if (ranged) {
                TooltipModifierHook.addStatBoost(tool, modifier.getModifier(), ToolStats.ACCURACY, TinkerTags.Items.RANGED, 0.1f * modifier.getLevel(), tooltip);
                TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.crystalaccuracy.shoot_speed"), -0.2f * modifier.getLevel(), tooltip);
            }
        }
    }
}
