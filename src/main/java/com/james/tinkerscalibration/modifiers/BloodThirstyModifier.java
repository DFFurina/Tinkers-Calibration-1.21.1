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

public class BloodThirstyModifier extends Modifier implements MeleeHitModifierHook, ProjectileLaunchModifierHook, ConditionalStatModifierHook, InventoryTickModifierHook, ModifierRemovalHook, AttributesModifierHook, TooltipModifierHook {

    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "blood_thirsty");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.REMOVE, ModifierHooks.ATTRIBUTES, ModifierHooks.TOOLTIP, ModifierHooks.INVENTORY_TICK);
    }


    @Override
    public int getPriority() {
        return 75;
    }

    /**
     * Gets the bonus for the modifier
     */
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
            int effectLevel = Math.min(7, Utils.bloodthirstyEffect.get().getLevel(attacker) + 1);
            Utils.bloodthirstyEffect.get().apply(attacker, 5 * 20, effectLevel, true);
            ModDataNBT persistentData = tool.getPersistentData();
            persistentData.putFloat(KEY, effectLevel + 1);
        }
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        return stat == ToolStats.VELOCITY ? baseValue * (1 + getBonus(living, Utils.bloodthirstyEffect, modifier.getLevel(), 16.0F) * tool.getMultiplier(ToolStats.VELOCITY)) : baseValue;
    }
    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if (primary && (arrow == null || arrow.isCritArrow())) {
            // 16 arrows gets you to max
            int effectLevel = Math.min(15, Utils.bloodthirstyEffect.get().getLevel(shooter) + 1);
            Utils.bloodthirstyEffect.get().apply(shooter, 5 * 20, effectLevel, true);
        }
    }


    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && isSelected) {
            ModDataNBT persistentData = tool.getPersistentData();
            if (Utils.bloodthirstyEffect.get().getLevel(holder) == -1) {
                persistentData.remove(KEY);
            }
        }
    }

    @Override
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        tool.getPersistentData().remove(modifier.getId());
        return null;
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute,AttributeModifier> consumer) {
        if (slot == EquipmentSlot.MAINHAND) {
            double boost = tool.getPersistentData().getFloat(KEY) * modifier.getLevel() / 8f;
            if (boost != 0) {
                consumer.accept(Attributes.ATTACK_SPEED.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "blood_thirsty_speed"), boost, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
        }
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        boolean harvest = tool.hasTag(TinkerTags.Items.HARVEST);
        // player is null when the tooltip is built without an entity (creative search, recipe viewers)
        if (player != null && tooltipKey == TooltipKey.SHIFT && (harvest || tool.hasTag(TinkerTags.Items.RANGED))) {
            float bonus = getBonus(player, Utils.bloodthirstyEffect, modifier.getLevel(), 16f);
            if (!harvest) {
                TooltipModifierHook.addStatBoost(tool, modifier.getModifier(), ToolStats.VELOCITY, TinkerTags.Items.RANGED, bonus, tooltip);
            }
        }
    }
}

