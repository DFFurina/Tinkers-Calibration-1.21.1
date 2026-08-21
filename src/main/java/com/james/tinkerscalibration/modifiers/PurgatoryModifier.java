package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static slimeknights.tconstruct.library.tools.stat.ToolStats.DRAW_SPEED;

public class PurgatoryModifier extends Modifier implements TooltipModifierHook, BreakSpeedModifierHook, MeleeDamageModifierHook, ConditionalStatModifierHook {
    public boolean isNetherDimension(Entity entity) {
        return entity != null && isNetherDimension(entity.getCommandSenderWorld());
    }

    public boolean isNetherDimension(Level level) {
        return level.dimension().equals(Level.NETHER);
    }

    @Override
    public float getMeleeDamage(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull ToolAttackContext context, float baseDamage, float damage) {
        if (context.getLivingTarget() != null) {
            LivingEntity target = context.getLivingTarget();
            if (isNetherDimension(target.getCommandSenderWorld())) {
                return damage * (1 + modifier.getLevel() * 0.08f);
            }
        }
        return damage;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.MELEE_DAMAGE, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.BREAK_SPEED);
    }
    @Override
    public void onBreakSpeed(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull PlayerEvent.BreakSpeed event, @Nonnull Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if (isNetherDimension(event.getEntity())) {
            event.setNewSpeed(event.getNewSpeed() * (1 + modifier.getLevel() * 0.08f));
        }
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        int level = modifier.getLevel();
        if (isNetherDimension(living)) {
            if (stat == DRAW_SPEED) {
                return baseValue * 1.08f * level;
            }
        }
        return baseValue;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            if (isNetherDimension(player)) {
                TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.modifier.purgatory.attack_damage"), 0.08f * modifier.getLevel(), tooltip);
                TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.modifier.purgatory.mining_speed"), 0.08f * modifier.getLevel(), tooltip);
            }
        }
    }

}