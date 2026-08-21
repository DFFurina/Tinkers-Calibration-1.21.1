package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nullable;
import java.util.List;


public class ExtremeFreezingModifier extends Modifier implements MeleeHitModifierHook, InventoryTickModifierHook, ProjectileHitModifierHook {
    public static boolean checkFreeze(Player player) {
        return player.tickCount % 80 == 0;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
    }
    public static void freeze(LivingEntity center, int level) {
        float range = 5 + 3 * level;
        List<Mob> ens = center.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(center.getX() - range, center.getY() - range, center.getZ() - range, center.getX() + range, center.getY() + range, center.getZ() + range));
        if (!ens.isEmpty())
            for (Mob en : ens) {
                if (en == null) continue;
                LivingEntity enlb = en;
                enlb.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                enlb.setTicksFrozen(85);
            }
    }

    public static boolean checkParticle(Player player) {
        return player.tickCount % 4 == 0;
    }

    public static void particle(LivingEntity entity) {
        entity.getCommandSenderWorld().addParticle(ParticleTypes.SNOWFLAKE,
                entity.getX() + RANDOM.nextDouble() - 0.5,
                entity.getY() + RANDOM.nextDouble(),
                entity.getZ() + RANDOM.nextDouble() - 0.5,
                0.0D, 0.25D, 0.0D);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (isSelected) {
            if (checkFreeze((Player) holder)) freeze(holder, modifier.getLevel());
            if (checkParticle((Player) holder)) particle(holder);
        }
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        if (target != null && !target.getCommandSenderWorld().isClientSide && target.isAlive()) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            target.setTicksFrozen(target.getTicksRequiredToFreeze() + 40 * modifier.getLevel());
        }
    }
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null && !target.getCommandSenderWorld().isClientSide && target.isAlive()) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            target.setTicksFrozen(target.getTicksRequiredToFreeze() + 40 * modifier.getLevel());
        }
        return false;
    }
}
