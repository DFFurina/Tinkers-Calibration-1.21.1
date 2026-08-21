package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
import java.util.Random;

public class BlowingModifier extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        Player player = context.getPlayerAttacker();
        LivingEntity target = context.getLivingTarget();
        if (player == null || target == null) return knockback;

        if (target instanceof Player) return knockback;
        Level world = target.getCommandSenderWorld();
        if (!world.isClientSide && RANDOM.nextFloat() <= 0.2f * modifier.getLevel()) {
            ItemStack stack2drop;
            stack2drop = target.getMainHandItem();
            if (stack2drop.isEmpty()) // 主手没找到
            {
                stack2drop = target.getOffhandItem();
                target.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            } else if (!stack2drop.isEmpty()) // 主手找到了
            {
                target.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            } else { // 都没找到
                return knockback;
            }
            ItemEntity item = new ItemEntity(world, target.getX(), target.getY(), target.getZ(), stack2drop);
            world.addFreshEntity(item);
        }

        return knockback;
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null && attacker != null && projectile instanceof AbstractArrow) {
            if (!attacker.getCommandSenderWorld().isClientSide && RANDOM.nextFloat() <= 0.2f * modifier.getLevel()) {
                ItemStack stack2drop;
                stack2drop = target.getMainHandItem();
                if (stack2drop.isEmpty()) // 主手没找到
                {
                    stack2drop = target.getOffhandItem();
                    target.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                } else if (!stack2drop.isEmpty()) // 主手找到了
                {
                    target.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                } else { // 都没找到
                    return false;
                }
                ItemEntity item = new ItemEntity(attacker.getCommandSenderWorld(), target.getX(), target.getY(), target.getZ(), stack2drop);
                attacker.getCommandSenderWorld().addFreshEntity(item);
            }
        }
        return false;
    }
}
