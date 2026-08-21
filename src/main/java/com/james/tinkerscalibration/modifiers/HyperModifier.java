package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.Utils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nullable;

public class HyperModifier extends Modifier implements MeleeHitModifierHook, BlockBreakModifierHook, ProjectileHitModifierHook {
    public void addEffects(LivingEntity living) {
        if (living != null && !living.getCommandSenderWorld().isClientSide) {
            int effectSpeed = 0;
            if(living.hasEffect(MobEffects.MOVEMENT_SPEED)) {
                effectSpeed = Math.min(2, living.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1);
            }
            int effectHaste = 0;
            if(living.hasEffect(MobEffects.DIG_SPEED)) {
                effectHaste = Math.min(2, living.getEffect(MobEffects.DIG_SPEED).getAmplifier() + 1);
            }
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, effectSpeed));
            living.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 60, effectHaste));
        }
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.BLOCK_BREAK, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        addEffects(context.getAttacker());
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        addEffects(context.getLiving());
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null && attacker != null && (!(projectile instanceof AbstractArrow arrow) || arrow.isCritArrow())) {
            addEffects(attacker);
        }
        return false;
    }
}
