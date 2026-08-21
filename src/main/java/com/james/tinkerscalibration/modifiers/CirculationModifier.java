package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CirculationModifier extends Modifier implements TooltipModifierHook, MeleeDamageModifierHook, BreakSpeedModifierHook, ConditionalStatModifierHook {
    public boolean isOverworld(Level level) {
        return level.dimension().equals(Level.OVERWORLD);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.MELEE_DAMAGE, ModifierHooks.BREAK_SPEED, ModifierHooks.CONDITIONAL_STAT);
    }
    @Override
    public void onBreakSpeed(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull PlayerEvent.BreakSpeed event, @Nonnull Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        Player player = event.getEntity();
        Level world = player.getCommandSenderWorld();
        if (isOverworld(world)) {
            event.setNewSpeed((float) (event.getNewSpeed() * (1 + 0.1 * modifier.getLevel())));
        }
    }

    @Override
    public float getMeleeDamage(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull ToolAttackContext context, float baseDamage, float damage) {
        Player player = context.getPlayerAttacker();
        if (player != null) {
            Level world = player.getCommandSenderWorld();
            if (!isOverworld(world)) {
                return (float) (damage * (1 + 0.08 * modifier.getLevel()));
            }
        }
        return damage;
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        Level world = living.getCommandSenderWorld();
        int level = modifier.getLevel();
        if (isOverworld(world)) {
            if (stat == ToolStats.DRAW_SPEED) {
                return baseValue * (1 + 0.3f * level);
            }
        } else {
            if (stat == ToolStats.VELOCITY) {
                return baseValue + (1 + 0.3f * level);
            }
        }
        return baseValue;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        boolean harvest = tool.hasTag(TinkerTags.Items.HARVEST);
        if (player != null) {
            if (harvest || tool.hasTag(TinkerTags.Items.RANGED)) {
                Level world = player.getCommandSenderWorld();
                if (tooltipKey == TooltipKey.SHIFT) {
                    if (harvest) {
                        if (isOverworld(world)) {
                            TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.circulation.mining_speed"), 0.1 * modifier.getLevel(), tooltip);
                        } else {
                            TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.circulation.attack_damage"), 0.08 * modifier.getLevel(), tooltip);
                        }
                    } else {
                        if (isOverworld(world)) {
                            TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.circulation.draw_speed"), 0.3 * modifier.getLevel(), tooltip);
                        } else {
                            TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.circulation.velocity"), 0.3 * modifier.getLevel(), tooltip);
                        }
                    }
                }
            }
        }
    }
}
