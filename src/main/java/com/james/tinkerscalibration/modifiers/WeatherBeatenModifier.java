package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.behavior.ReduceToolDamageModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class WeatherBeatenModifier extends Modifier implements ToolDamageModifierHook {
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @javax.annotation.Nullable LivingEntity holder) {
        if (holder != null && (holder.getCommandSenderWorld().getBiome(holder.getOnPos()).value().getBaseTemperature() <= 0.5f || holder.getCommandSenderWorld().getBiome(holder.getOnPos()).value().getBaseTemperature() >= 1.5f)) {
            return ReduceToolDamageModule.reduceDamage(amount, Math.abs(holder.getCommandSenderWorld().getBiome(holder.getOnPos()).value().getBaseTemperature() - 0.75f) * (25 + 5 * modifier.getLevel()));
        }
        return amount;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOL_DAMAGE);
    }
}
