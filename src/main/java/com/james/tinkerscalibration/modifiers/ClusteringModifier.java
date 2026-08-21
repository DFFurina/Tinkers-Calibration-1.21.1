package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nullable;
import java.util.List;

public class ClusteringModifier extends NoLevelsModifier implements MeleeDamageModifierHook, BreakSpeedModifierHook, TooltipModifierHook, ConditionalStatModifierHook {
    public float getPercentage(Inventory i) {
        int sizeInv = i.getContainerSize();
        float current = 0;
        float sum = 0;
        while (sizeInv-- > 0) {
            ItemStack stackInv = i.getItem(sizeInv);
            if (stackInv.isEmpty() || !stackInv.is(TinkerTags.Items.MODIFIABLE)) continue;
            ToolStack toolInv = ToolStack.from(stackInv);
            current += toolInv.getCurrentDurability();
            sum += toolInv.getStats().get(ToolStats.DURABILITY);
        }
        return current / sum;
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        Player player = event.getEntity();
        Inventory inv = player.getInventory();
        if (getPercentage(inv) <= 0.45) {
            event.setNewSpeed(event.getNewSpeed() * (1 + (0.45f - getPercentage(inv)) * 0.4f));
        }
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        Player player = context.getPlayerAttacker();
        if(player != null) {
            Inventory inv = player.getInventory();
            float percentage = getPercentage(inv);
            if (percentage > 0.45) {
                return damage * (1 + (percentage - 0.45f) * 0.4f);
            }
        }
        return damage;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.MELEE_DAMAGE, ModifierHooks.BREAK_SPEED, ModifierHooks.CONDITIONAL_STAT);
    }
    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        Player player = (Player) living;
        Inventory inv = player.getInventory();
        float percentage = getPercentage(inv);
        if (percentage > 0.45) {
            if (stat == ToolStats.PROJECTILE_DAMAGE) {
                return baseValue * (1 + (percentage - 0.45f) * 0.4f);
            }
        } else {
            if (stat == ToolStats.DRAW_SPEED) {
                return baseValue * (1 + (0.45f - getPercentage(inv)) * 0.4f);
            }
        }
        return baseValue;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        boolean harvest = tool.hasTag(TinkerTags.Items.HARVEST);
        if (player != null) {
            if (harvest || tool.hasTag(TinkerTags.Items.RANGED)) {
                if (tooltipKey == TooltipKey.SHIFT) {
                    Inventory inv = player.getInventory();
                    float percentage = getPercentage(inv);
                    if (harvest) {
                        if (percentage > 0.45) {
                            TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.clustering.attack_damage"), (percentage - 0.45f) * 0.4f, tooltip);
                        } else {
                            TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.clustering.mining_speed"), (0.45f - percentage) * 0.4f, tooltip);
                        }
                    } else {
                        if (percentage > 0.45) {
                            TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.clustering.projectile_damage"), (percentage - 0.45f) * 0.4f, tooltip);
                        } else {
                            TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.clustering.draw_speed"), (0.45f - percentage) * 0.4f, tooltip);
                        }
                    }
                }
            }
        }
    }
}
