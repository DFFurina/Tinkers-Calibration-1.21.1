package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
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

public class PyroelectricModifier extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        LivingEntity holder = context.getAttacker();
        if (target == null) return;

        BlockPos pos = target.getOnPos();
        Level world = target.getCommandSenderWorld();
        float temp = world.getBiome(pos).value().getBaseTemperature();
        if (target.isOnFire()) temp += 0.3f; // new flavor

        if (temp < 0.15) return;

        final float damage = temp * 3;
        int level = modifier.getLevel();
        List<Mob> list = world.getEntitiesOfClass(Mob.class, new AABB(target.getX() - 5 * level, target.getY() - 5 * level, target.getZ() - 5 * level, target.getX() + 5 * level, target.getY() + 5 * level, target.getZ() + 5 * level));
        if (!world.isClientSide) // server - deal damage
        {
            for (Mob en : list) {
                if (en == holder) continue;
                en.invulnerableTime = 0;
                en.hurt(new DamageSource(holder.getCommandSenderWorld().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.LIGHTNING_BOLT)), damage);
            }
        }
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target == null) return false;

        BlockPos pos = target.getOnPos();
        Level world = target.getCommandSenderWorld();
        float temp = world.getBiome(pos).value().getBaseTemperature();
        if (target.isOnFire()) temp += 0.3F; // new flavor

        if (temp < 0.15) return false;

        final float damage = temp * 0.5f;
        int level = modifier.getLevel();
        List<Mob> list = world.getEntitiesOfClass(Mob.class, new AABB(target.getX() - 5 * level, target.getY() - 5 * level, target.getZ() - 5 * level, target.getX() + 5 * level, target.getX() + 5 * level, target.getX() + 5 * level));
        if (!world.isClientSide && attacker != null) // server - deal damage
        {
            for (Mob en : list) {
                if (en == attacker) continue;
                en.invulnerableTime = 0;
                en.hurt(new DamageSource(attacker.getCommandSenderWorld().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.LIGHTNING_BOLT)), damage);
            }
        }
        return false;
    }
}