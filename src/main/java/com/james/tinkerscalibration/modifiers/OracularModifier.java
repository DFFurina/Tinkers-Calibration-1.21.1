package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
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
import java.util.Collection;

public class OracularModifier extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget(), holder = context.getAttacker();

        Collection<MobEffectInstance> listEffect1 = null;
        if (target != null) {
            listEffect1 = target.getActiveEffects();
        }
        if (listEffect1 != null) {
            for (MobEffectInstance effect : listEffect1) {
                if (effect.getEffect().value().isBeneficial()) {
                    target.removeEffect(effect.getEffect());
                    target.getCommandSenderWorld().addParticle(
                            ParticleTypes.HAPPY_VILLAGER,
                            target.getX() + RANDOM.nextDouble() - 0.5,
                            target.getY() + 1,
                            target.getZ() + RANDOM.nextDouble() - 0.5,
                            0, 0, 0);
                }
            }
        }
        Collection<MobEffectInstance> listEffect2 = holder.getActiveEffects();
        for (MobEffectInstance effect : listEffect2) {
            if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                holder.removeEffect(effect.getEffect());
                holder.getCommandSenderWorld().addParticle(
                        ParticleTypes.HAPPY_VILLAGER,
                        holder.getX() + RANDOM.nextDouble() - 0.5,
                        holder.getY() + 1,
                        holder.getZ() + RANDOM.nextDouble() - 0.5,
                        0, 0, 0);
            }
        }
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if(target != null) {
            Collection<MobEffectInstance> listEffect1 = target.getActiveEffects();
            for (MobEffectInstance effect : listEffect1) {
                if (effect.getEffect().value().isBeneficial()) {
                    target.removeEffect(effect.getEffect());
                    target.getCommandSenderWorld().addParticle(
                            ParticleTypes.HAPPY_VILLAGER,
                            target.getX() + RANDOM.nextDouble() - 0.5,
                            target.getY() + 1,
                            target.getZ() + RANDOM.nextDouble() - 0.5,
                            0, 0, 0);
                }
            }
        }
        if(attacker != null) {
            Collection<MobEffectInstance> listEffect2 = attacker.getActiveEffects();
            for (MobEffectInstance effect : listEffect2) {
                if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
                    attacker.removeEffect(effect.getEffect());
                    attacker.getCommandSenderWorld().addParticle(
                            ParticleTypes.HAPPY_VILLAGER,
                            attacker.getX() + RANDOM.nextDouble() - 0.5,
                            attacker.getY() + 1,
                            attacker.getZ() + RANDOM.nextDouble() - 0.5,
                            0, 0, 0);
                }
            }
        }
        return false;
    }
}