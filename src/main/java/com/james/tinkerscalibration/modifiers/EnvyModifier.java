package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.TinkersCalibration;
import com.james.tinkerscalibration.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.mantle.registration.RegistryObject;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.mantle.client.TooltipKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class EnvyModifier extends Modifier implements TooltipModifierHook, AttributesModifierHook, InventoryTickModifierHook, MeleeHitModifierHook, ProjectileLaunchModifierHook, ProjectileHitModifierHook, ConditionalStatModifierHook {
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "envy");
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.ATTRIBUTES, ModifierHooks.INVENTORY_TICK, ModifierHooks.MELEE_HIT, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.PROJECTILE_HIT, ModifierHooks.PROJECTILE_LAUNCH);
    }


    private static MobEffectInstance makeSlownessEffect(int level) {
        return new MobEffectInstance((net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect>)(net.minecraft.core.Holder<?>)Utils.envied.getHolder(), 40, Math.min(10, level));
    }

    private static float getBonus(LivingEntity living, RegistryObject<? extends TinkerEffect> effect, ModifierEntry modifier, float scale) {
        // 25% boost per level at max
        int effectLevel = effect.get().getLevel(living) + 1;
        return modifier.getLevel() * effectLevel / scale;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        if (target != null && !context.isExtraAttack() && context.isFullyCharged()) {
            LivingEntity attacker = context.getAttacker();
            int effectLevel = Math.min(10, Utils.envyEffect.get().getLevel(attacker) + 1);
            Utils.envyEffect.get().apply(attacker, 40, effectLevel, true);
            target.addEffect(makeSlownessEffect(effectLevel));
            ModDataNBT persistentData = tool.getPersistentData();
            persistentData.putFloat(KEY, persistentData.getFloat(KEY) + 1);
        }
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if (primary && (arrow == null || arrow.isCritArrow())) {
            int effectLevel = Math.min(10, Utils.envyEffect.get().getLevel(shooter) + 1);
            Utils.envyEffect.get().apply(shooter, 40, effectLevel, true);
            persistentData.putFloat(KEY, persistentData.getFloat(KEY) + 1);
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null && attacker != null && (!(projectile instanceof AbstractArrow arrow) || arrow.isCritArrow())) {
            target.addEffect(makeSlownessEffect(Utils.envyEffect.get().getLevel(attacker)));
        }
        return false;
    }

    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder.tickCount % 8 == 0 && holder.getUseItem() != stack && isSelected) {
            ModDataNBT persistentData = tool.getPersistentData();
            if (Utils.envyEffect.get().getLevel(holder) == -1) {
                persistentData.remove(KEY);
            }
        }
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (slot == EquipmentSlot.MAINHAND && tool.hasTag(TinkerTags.Items.MELEE)) {
            double boost = Math.min(10, tool.getPersistentData().getFloat(KEY)) * modifier.getLevel() / 8f;
            if (boost != 0) {
                consumer.accept(Attributes.ATTACK_SPEED.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "envy_attack_speed"), boost, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
        }
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        return stat == ToolStats.DRAW_SPEED ? baseValue + getBonus(living, Utils.envyEffect, modifier, 16.0F) * tool.getMultiplier(ToolStats.DRAW_SPEED) : baseValue;
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (tool.hasTag(TinkerTags.Items.RANGED)) {
            if (tooltipKey == TooltipKey.SHIFT) {
                TooltipModifierHook.addStatBoost(tool, modifier.getModifier(), ToolStats.VELOCITY, TinkerTags.Items.RANGED, getBonus(player, Utils.envyEffect, modifier, 16.0F), tooltip);
            }
        }
    }
}
