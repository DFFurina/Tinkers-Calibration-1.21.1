package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
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
import java.util.List;

public class ArroganceModifier extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        Player player = context.getPlayerAttacker();
        if (context.isFullyCharged() && player != null) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            double range = 5 + modifier.getLevel() * 5;
            List<Mob> targets = player.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x - range, y - range, z - range, x + range, y + range, z + range));
            int sum = 1;
            for (Mob target : targets) {
                if (sum <= 30 && target.getType() == context.getTarget().getType()) {
                    target.hurt(new DamageSource(player.getCommandSenderWorld().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK)), damageDealt * 0.4f);
                    target.getCommandSenderWorld().addParticle(
                            ParticleTypes.SMOKE,
                            target.getX() + RANDOM.nextDouble() - 0.5,
                            target.getY() + 1,
                            target.getZ() + RANDOM.nextDouble() - 0.5,
                            0, 0, 0);
                    sum++;
                }
            }
            return;
        }
    }
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null && attacker != null && (!(projectile instanceof AbstractArrow arrow) || arrow.isCritArrow())) {
            int level = modifier.getLevel();
            double x = target.getX();
            double y = target.getY();
            double z = target.getZ();
            double range = 5 + level * 5;
            List<Mob> targets = target.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x - range, y - range, z - range, x + range, y + range, z + range));
            int sum = 1;
            for (Mob target1 : targets) {
                if (target1 != null && projectile instanceof AbstractArrow arrow && sum <= 30 && target.getType() == target1.getType()) {
                    target.hurt(new DamageSource(attacker.getCommandSenderWorld().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.PLAYER_ATTACK)), (float) (arrow.getBaseDamage() * 0.4f));
                    target.getCommandSenderWorld().addParticle(
                            ParticleTypes.SMOKE,
                            target.getX() + RANDOM.nextDouble() - 0.5,
                            target.getY() + 1,
                            target.getZ() + RANDOM.nextDouble() - 0.5,
                            0, 0, 0);
                    sum++;
                }
            }
        }
        return false;
    }
}
