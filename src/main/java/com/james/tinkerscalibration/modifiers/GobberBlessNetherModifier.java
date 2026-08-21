package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.TinkersCalibration;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class GobberBlessNetherModifier extends Modifier implements ToolStatsModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS);
    }
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.DRAW_SPEED.multiply(builder, 1 + 0.15 * modifier.getLevel());
        ToolStats.MINING_SPEED.multiply(builder, 1 + 0.15 * modifier.getLevel());
        ToolStats.DURABILITY.multiply(builder, 1 + 0.15 * modifier.getLevel());
        ToolStats.ATTACK_SPEED.multiply(builder, 1 + 0.15 * modifier.getLevel());
        ToolStats.ATTACK_DAMAGE.multiply(builder, 1 + 0.15 * modifier.getLevel());
        ToolStats.VELOCITY.multiply(builder, 1 + 0.15 * modifier.getLevel());
        ToolStats.ACCURACY.multiply(builder, 1 + 0.15 * modifier.getLevel());
        ToolStats.PROJECTILE_DAMAGE.multiply(builder, 1 + 0.15 * modifier.getLevel());
        ToolStats.ARMOR.multiply(builder, 1 + 0.3 * modifier.getLevel());
        ToolStats.ARMOR_TOUGHNESS.multiply(builder, 1 + 0.3 * modifier.getLevel());
    }
}
