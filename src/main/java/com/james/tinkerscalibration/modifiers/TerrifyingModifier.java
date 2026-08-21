package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nullable;

public class TerrifyingModifier extends Modifier implements ProjectileHitModifierHook {
    //*@Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        if (damageDealt > 0 && target != null && !target.getCommandSenderWorld().isClientSide && target.isAlive()) {
            target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 205, 0));
            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 205, 0));
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 205, 0));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 205, 0));
        }

        return 0;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null && !target.getCommandSenderWorld().isClientSide && target.isAlive()) {
            target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 205, 0));
            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 205, 0));
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 205, 0));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 205, 0));
        }
        return false;
    }
}
