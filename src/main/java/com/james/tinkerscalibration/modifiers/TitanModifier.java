package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.BreakSpeed;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nullable;
import java.util.List;

public class TitanModifier extends Modifier{
    /*private static final Component MINING_SPEED = TConstruct.makeTranslation("modifier", "dwarven.mining_speed");
    private static final Component VELOCITY = TConstruct.makeTranslation("modifier", "dwarven.velocity");

    private static final float BOOST_DISTANCE = 64f;

    private static final float DEBUFF_RANGE = 128f;

    private static final float MINING_BONUS = 6;

    private static final float VELOCITY_BONUS = 0.05f;

    @Override
    protected void registerHooks(Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.CONDITIONAL_STAT);
    }
/*
    private static float getBoost(Level world, float y, int level, float baseSpeed, float modifier, float bonus) {
        if (y > BOOST_DISTANCE) {
            float bonus1 = (y - BOOST_DISTANCE) * bonus / 64 * modifier;
            return baseSpeed + bonus1;
        }
        return baseSpeed;
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, int level, BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if (!isEffective) {
            return;
        }
        event.setNewSpeed(getBoost(event.getPlayer().level, event.getPos().getY(), level, event.getNewSpeed(), miningSpeedModifier * tool.getMultiplier(ToolStats.MINING_SPEED), MINING_BONUS));
    }
*/
    /*
    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        if (stat == ToolStats.VELOCITY) {
            return getBoost(living.getCommandSenderWorld(), (float) living.getY(), modifier.getLevel(), baseValue, multiplier, VELOCITY_BONUS);
        }
        return baseValue;
    }

    @Override
    public void addInformation(IToolStackView tool, int level, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag tooltipFlag) {
        boolean harvest = tool.hasTag(TinkerTags.Items.HARVEST);
        if (harvest || tool.hasTag(TinkerTags.Items.RANGED)) {
            if (player != null && key == TooltipKey.SHIFT) {
                float y = (float) player.getY();
                if (harvest) {
                    //addStatTooltip(tool, ToolStats.MINING_SPEED, getBoost(player.getCommandSenderWorld(), y, level, 0, miningSpeedModifier * tool.getMultiplier(ToolStats.MINING_SPEED)));
                }
            }
        }
    }
    */
}
