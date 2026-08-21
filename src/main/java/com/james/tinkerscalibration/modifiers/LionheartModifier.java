package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.Utils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nullable;

public class LionheartModifier extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (!context.getTarget().getCommandSenderWorld().isClientSide && damageDealt > 0) {
            LivingEntity holder = context.getAttacker();
            int effectLevel = Math.min(7, Utils.lionheartEffect.get().getLevel(holder) + 1);
            holder.addEffect(new MobEffectInstance((net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect>)(net.minecraft.core.Holder<?>)Utils.lionheartEffect.getHolder(), 60 + 20 * modifier.getLevel(), effectLevel));
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null && !target.getCommandSenderWorld().isClientSide && attacker != null) {
            attacker.addEffect(new MobEffectInstance((net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect>)(net.minecraft.core.Holder<?>)Utils.lionheartEffect.getHolder(), 60 + 20 * modifier.getLevel(), modifier.getLevel() - 1));
        }
        return false;
    }
}