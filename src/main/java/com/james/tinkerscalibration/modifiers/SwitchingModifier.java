package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
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

public class SwitchingModifier extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        LivingEntity holder = context.getAttacker();
        if (damageDealt > 0 && target != null && holder.isAlive() && target.isAlive()) {
            BlockPos posHolder = holder.getOnPos();
            BlockPos posTarget = target.getOnPos();
            target.moveTo(posHolder.getX(), posHolder.getY(), posHolder.getZ());
            holder.moveTo(posTarget.getX(), posTarget.getY(), posTarget.getZ());
        }
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null && attacker.isAlive() && target.isAlive()) {
            BlockPos posHolder = attacker.getOnPos();
            BlockPos posTarget = target.getOnPos();
            target.moveTo(posHolder.getX(), posHolder.getY(), posHolder.getZ());
            attacker.moveTo(posTarget.getX(), posTarget.getY(), posTarget.getZ());
        }
        return false;
    }
}