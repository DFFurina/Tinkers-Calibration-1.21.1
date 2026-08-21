package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
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
import java.util.Collection;

public class PiercingModifier extends Modifier implements ProjectileHitModifierHook {
    /*@Override
    public float getEntityDamage(IToolStackView tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        int count = 0;
        LivingEntity target = context.getLivingTarget();
        if (target != null) {
            Collection<MobEffectInstance> listEffect = target.getActiveEffects();
            for (MobEffectInstance ei : listEffect) {
                if (ei.getEffect().getCategory() == MobEffectCategory.HARMFUL)
                    count++;
            }
        }
        return damage + count * 2f;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        int count = 0;
        if (target != null) {
            Collection<MobEffectInstance> listEffect = target.getActiveEffects();
            for (MobEffectInstance ei : listEffect) {
                if (ei.getEffect().getCategory() == MobEffectCategory.HARMFUL)
                    count++;
            }
        }
        if (projectile instanceof AbstractArrow arrow) {
            arrow.setBaseDamage(arrow.getBaseDamage() + count * 0.2f);
        }
        return false;
    }

     */
}