package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
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
import java.util.Random;

public class WarModifier extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT, ModifierHooks.MELEE_HIT);
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        Player player = context.getPlayerAttacker();
        if (player == null) return;
        LivingEntity target = context.getLivingTarget();
        if (target == null) return;

        Level world = player.getCommandSenderWorld();

        if (damageDealt > 0 && !world.isClientSide && RANDOM.nextFloat() <= 0.3f * modifier.getLevel()) {
            world.explode(target, target.getX(), target.getY(), target.getZ(), damageDealt / 10 + 1, Level.ExplosionInteraction.NONE);
            AreaEffectCloud cloud = new AreaEffectCloud(world, target.getX(), target.getY(), target.getZ());
            cloud.setParticle(ParticleTypes.SMOKE);
            cloud.setRadius(damageDealt / 10);
            cloud.setDuration(40);
            cloud.addEffect(new MobEffectInstance(MobEffects.WITHER, modifier.getLevel() - 1, 10));
        }
        if(context.isCritical())
        {
            world.addParticle(ParticleTypes.SMOKE, player.getX(), player.getY(), player.getZ(), 0, 0, 0);
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null && attacker != null && projectile instanceof AbstractArrow arrow) {
            float damageDealt = (float) ((float) arrow.getBaseDamage() * arrow.getDeltaMovement().length());
            if (damageDealt > 0 && !attacker.getCommandSenderWorld().isClientSide && target.isAlive() && RANDOM.nextFloat() <= 0.3f * modifier.getLevel()) {
                target.invulnerableTime = 0;
                target.hurt(new DamageSource(attacker.getCommandSenderWorld().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.ARROW)), damageDealt);
                Random random = (Random) target.getCommandSenderWorld().random;
                //target.getCommandSenderWorld().addParticle(Utils.birefringentParticle.get(),
                //        target.getX() + random.nextDouble() - 0.5,
                //        target.getY() + random.nextDouble(),
                //        target.getZ() + random.nextDouble() - 0.5,
                //        0.0D, 0.25D, 0.0D);

            }
        }
        return false;
    }
}
