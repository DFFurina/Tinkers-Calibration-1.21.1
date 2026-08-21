package com.james.tinkerscalibration.modifiers.rewrite;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.utils.Util;

import javax.annotation.Nullable;
import java.util.List;

public class NecroticModifierRewrite extends Modifier implements ProjectileHitModifierHook, MeleeHitModifierHook, TooltipModifierHook {
    private static final Component LIFE_STEAL_CRITICAL = TConstruct.makeTranslation("modifier", "necroticrewrite.lifesteal_critical");
    private static final Component LIFE_STEAL = TConstruct.makeTranslation("modifier", "necroticrewrite.lifesteal");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT, ModifierHooks.MELEE_HIT, ModifierHooks.TOOLTIP);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (damageDealt > 0) {
            float percent;
            int level = modifier.getLevel();
            if (context.isFullyCharged() && context.isCritical()) {
                percent = 0.12f * level;
            } else {
                percent = 0.08f * level;
            }
            if (percent > 0) {
                LivingEntity attacker = context.getAttacker();
                if(damageDealt <= 50)
                    attacker.heal(percent * damageDealt);
                else
                    attacker.heal(20);
                attacker.getCommandSenderWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), Sounds.NECROTIC_HEAL.getSound(), SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null && attacker != null && projectile instanceof AbstractArrow arrow) {
            float percent;
            if (arrow.isCritArrow()) {
                percent = 0.12f * modifier.getLevel();
            } else {
                percent = 0.08f * modifier.getLevel();
            }
            if (percent > 0) {
                float damageDealt = (float) Math.min(target.getHealth(), arrow.getBaseDamage() * arrow.getDeltaMovement().length());
                if(damageDealt <= 30)
                    attacker.heal(percent * damageDealt);
                else
                    attacker.heal(12);
                attacker.getCommandSenderWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), Sounds.NECROTIC_HEAL.getSound(), SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        }
        return false;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        int level = modifier.getLevel();
        if (level > 0) {
            tooltip.add(applyStyle(Component.translatable(Util.PERCENT_FORMAT.format(level * 0.12f) + " ").append(LIFE_STEAL_CRITICAL)));
            tooltip.add(applyStyle(Component.translatable(Util.PERCENT_FORMAT.format(level * 0.08f) + " ").append(LIFE_STEAL)));
        }
    }
}
