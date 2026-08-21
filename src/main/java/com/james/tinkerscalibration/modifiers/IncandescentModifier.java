package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.particles.ParticleTypes;
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

public class IncandescentModifier extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.isFullyCharged()) {
            Player player = context.getPlayerAttacker();
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            double range = 5 + modifier.getLevel() * 5;
            List<Mob> targets = player.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x - range, y - range, z - range, x + range, y + range, z + range));
            for (Mob target : targets) {
                if (target != null) {
                    target.setRemainingFireTicks(modifier.getLevel() * 5 * 20);
                    player.getCommandSenderWorld().addParticle(
                            ParticleTypes.FLAME,
                            target.getX() + RANDOM.nextDouble() - 0.5,
                            target.getY() + 1,
                            target.getZ() + RANDOM.nextDouble() - 0.5,
                            0, 0, 0);
                }
            }
        }
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
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
            for (Mob target1 : targets) {
                if (target1 != null) {
                    target1.setRemainingFireTicks(level * 5 * 20);
                    attacker.getCommandSenderWorld().addParticle(
                            ParticleTypes.FLAME,
                            target.getX() + RANDOM.nextDouble() - 0.5,
                            target.getY() + 1,
                            target.getZ() + RANDOM.nextDouble() - 0.5,
                            0, 0, 0);
                }
            }
        }
        return false;
    }
}
