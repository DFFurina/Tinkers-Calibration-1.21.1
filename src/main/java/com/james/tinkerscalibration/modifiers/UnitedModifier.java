package com.james.tinkerscalibration.modifiers;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierTraitHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolDataNBT;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class UnitedModifier extends Modifier implements ToolStatsModifierHook, VolatileDataModifierHook {
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        int level = Math.min(modifier.getLevel(), 2);
        ToolStats.DRAW_SPEED.multiply(builder, 1 + 0.3 * level);
        ToolStats.MINING_SPEED.multiply(builder, 1 + 0.3 * level);
        ToolStats.DURABILITY.multiply(builder, 1 + 0.3 * level);
        ToolStats.ATTACK_SPEED.multiply(builder, 1 + 0.3 * level);
        ToolStats.ATTACK_DAMAGE.multiply(builder, 1 + 0.3 * level);
        ToolStats.VELOCITY.multiply(builder, 1 + 0.3 * level);
        ToolStats.ACCURACY.multiply(builder, 1 + 0.3 * level);
        ToolStats.PROJECTILE_DAMAGE.multiply(builder, 1 + 0.3 * level);
        ToolStats.ARMOR.multiply(builder, 1 + 0.4 * level);
        ToolStats.ARMOR_TOUGHNESS.multiply(builder, 1 + 0.4 * level);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS, ModifierHooks.VOLATILE_DATA);
    }

    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ToolDataNBT toolDataNBT) {
        int level = Math.min(modifier.getLevel(), 2);
        toolDataNBT.addSlots(SlotType.UPGRADE, 3 * level);
        toolDataNBT.addSlots(SlotType.ABILITY, 2 * level);
        if (context.hasTag(TinkerTags.Items.ARMOR))
        {
            toolDataNBT.addSlots(SlotType.DEFENSE, 2 * level);
        }
    }
}
