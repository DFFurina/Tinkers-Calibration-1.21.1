package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RobberModifier extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook {

    @Override
    public int getPriority() {
        return 150;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget(), holder = context.getAttacker();

        Collection<MobEffectInstance> listEffect1 = null;
        if (target != null) {
            listEffect1 = target.getActiveEffects();
        }
        if (listEffect1 != null && RANDOM.nextFloat() <= 0.2f * modifier.getLevel()) {
            List<MobEffectInstance> listEffectBeneficial = new ArrayList<>();
            for (MobEffectInstance effect : listEffect1) {
                if (effect.getEffect().value().isBeneficial()) {
                    listEffectBeneficial.add(effect);
                }
            }
            int i = RANDOM.nextInt(listEffectBeneficial.size());
            MobEffectInstance effect = listEffectBeneficial.get(i);
            if (effect != null) {
                holder.addEffect(effect);
                target.removeEffect(effect.getEffect());
            }
        }
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        Collection<MobEffectInstance> listEffect1 = null;
        if (target != null && attacker != null) {
            listEffect1 = target.getActiveEffects();
        }
        if (listEffect1 != null && RANDOM.nextFloat() <= 0.2f * modifier.getLevel()) {
            List<MobEffectInstance> listEffectBeneficial = new ArrayList<>();
            for (MobEffectInstance effect : listEffect1) {
                if (effect.getEffect().value().isBeneficial()) {
                    listEffectBeneficial.add(effect);
                }
            }
            int i = RANDOM.nextInt(listEffectBeneficial.size());
            MobEffectInstance effect = listEffectBeneficial.get(i);
            if (effect != null) {
                attacker.addEffect(effect);
                target.removeEffect(effect.getEffect());
            }
        }
        return false;
    }
}