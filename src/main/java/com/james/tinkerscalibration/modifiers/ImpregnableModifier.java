package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TooltipFlag;
import slimeknights.mantle.registration.RegistryObject;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.mantle.client.TooltipKey;

import javax.annotation.Nullable;
import java.util.List;

public class ImpregnableModifier extends Modifier implements TooltipModifierHook, BlockBreakModifierHook, ToolDamageModifierHook, ProjectileLaunchModifierHook, ConditionalStatModifierHook {
    private static final Component UNBREAKING = TConstruct.makeTranslation("modifier", "impregnable.unbreaking");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.BLOCK_BREAK, ModifierHooks.TOOL_DAMAGE, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.PROJECTILE_LAUNCH);
    }

    private static float getBonus(@Nullable LivingEntity living, RegistryObject<? extends TinkerEffect> effect, int level, float scale) {
        // without an entity there is no effect to read (tool damage without a holder)
        if (living == null) {
            return 0;
        }
        int effectLevel = effect.get().getLevel(living) + 1;
        return level * effectLevel / scale;
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        if (context.canHarvest() && context.isEffective() && !context.isAOE()) {
            // 32 blocks gets you to max, effect is stronger at higher levels
            LivingEntity living = context.getLiving();
            int effectLevel = Math.min(31, Utils.impregnableEffect.get().getLevel(living) + 1);
            Utils.impregnableEffect.get().apply(living, 160, effectLevel, true);
        }
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        float bonus = getBonus(holder, Utils.impregnableEffect, modifier.getLevel(), 6);
        int off = 0;
        for (int i = 0; i < amount; i++) {
            if (RANDOM.nextFloat() >= 1 / bonus) {
                off++;
            }
        }
        return amount - off;
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if (primary && (arrow == null || arrow.isCritArrow())) {
            // 16 arrows gets you to max
            int effectLevel = Math.min(15, Utils.impregnableEffect.get().getLevel(shooter) + 1);
            Utils.impregnableEffect.get().apply(shooter, 5 * 20, effectLevel, true);
        }
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        return baseValue;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        boolean harvest = tool.hasTag(TinkerTags.Items.HARVEST);
        // player is null when the tooltip is built without an entity (creative search, recipe viewers)
        if (player != null && tooltipKey == TooltipKey.SHIFT && (harvest || tool.hasTag(TinkerTags.Items.RANGED))) {
            float bonus = getBonus(player, Utils.impregnableEffect, modifier.getLevel(), 6);
            if (bonus > 0) {
                TooltipModifierHook.addPercentBoost(modifier.getModifier(), UNBREAKING, 1 / bonus, tooltip);
            }
        }
    }
}
