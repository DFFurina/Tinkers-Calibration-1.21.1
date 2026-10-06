package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.TinkersCalibration;
import com.james.tinkerscalibration.Utils;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import slimeknights.mantle.registration.RegistryObject;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;

public class VibratingModifier extends Modifier implements TooltipModifierHook, ConditionalStatModifierHook, AttributesModifierHook, InventoryTickModifierHook, ModifierRemovalHook, MeleeHitModifierHook, ProjectileLaunchModifierHook {
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "vibrating");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.TOOLTIP, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.ATTRIBUTES, ModifierHooks.INVENTORY_TICK, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.REMOVE);
    }

    private static float getBonus(@Nullable LivingEntity living, RegistryObject<? extends TinkerEffect> effect, int level, float scale) {
        // 25% boost per level at max; without an entity (e.g. creative search tooltips) there is no effect to read
        if (living == null) {
            return 0;
        }
        int effectLevel = effect.get().getLevel(living) + 1;
        return level * effectLevel / scale;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        // 8 hits gets you to max, levels faster at higher levels
        if (!context.isExtraAttack() && context.isFullyCharged()) {
            LivingEntity attacker = context.getAttacker();
            int effectLevel = Math.min(7, Utils.vibratingEffect.get().getLevel(attacker) + 1);
            Utils.vibratingEffect.get().apply(attacker, 5 * 20, effectLevel, true);
            ModDataNBT persistentData = tool.getPersistentData();
            persistentData.putFloat(KEY, effectLevel + 1);
        }
    }

    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide) {
            ModDataNBT persistentData = tool.getPersistentData();
            if (Utils.vibratingEffect.get().getLevel(holder) == -1) {
                persistentData.remove(KEY);
            }
        }
    }
    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        LivingEntity attacker = context.getAttacker();
        return knockback * (1 + getBonus(attacker, Utils.vibratingEffect, modifier.getLevel(), 8f));
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if (primary && (arrow == null || arrow.isCritArrow())) {
            int effectLevel = Math.min(7, Utils.vibratingEffect.get().getLevel(shooter) + 1);
            Utils.vibratingEffect.get().apply(shooter, 5 * 20, effectLevel, true);
        }
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        if (living == null) {
            return baseValue;
        }
        if (stat == ToolStats.DRAW_SPEED) {
            return baseValue - 0.1f * Utils.vibratingEffect.get().getLevel(living);
        }
        return baseValue;
    }
    @Override
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        tool.getPersistentData().remove(getId());
        return null;
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (slot == EquipmentSlot.MAINHAND) {
            double boost = tool.getPersistentData().getFloat(KEY) * 0.05;
            if (boost != 0) {
                consumer.accept(Attributes.ATTACK_SPEED.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "vibrating_attack_speed"), -boost, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        boolean harvest = tool.hasTag(TinkerTags.Items.HARVEST);
        // player is null when the tooltip is built without an entity (creative search, recipe viewers)
        if (player != null && tooltipKey == TooltipKey.SHIFT && (harvest || tool.hasTag(TinkerTags.Items.RANGED))) {
            float bonus = getBonus(player, Utils.vibratingEffect, modifier.getLevel(), 8f);
            if (bonus > 0) {
                if (harvest) {
                    TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.vibrating.knockback"), bonus, tooltip);
                } else {
                    TooltipModifierHook.addPercentBoost(modifier.getModifier(), Component.translatable("modifier.tinkerscalibration.vibrating.punch"), bonus, tooltip);
                    TooltipModifierHook.addStatBoost(tool, modifier.getModifier(), ToolStats.DRAW_SPEED, TinkerTags.Items.RANGED, -0.1f * Utils.vibratingEffect.get().getLevel(player) / tool.getMultiplier(ToolStats.DRAW_SPEED), tooltip);
                }
            }
        }
    }
}
