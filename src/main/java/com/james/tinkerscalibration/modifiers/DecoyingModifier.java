package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nullable;


public class DecoyingModifier extends Modifier implements BlockBreakModifierHook, MeleeHitModifierHook, ProjectileHitModifierHook {
    public void spawnAnimal(Level world, Entity entity, Entity summoner) {
        world.addFreshEntity(entity);
        entity.moveTo(summoner.getX(), summoner.getY(), summoner.getZ());
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.BLOCK_BREAK, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT);
    }
    public void spawn(Level world, Entity attacker, @Nullable Entity target) {
        int random = RANDOM.nextInt(1, 5);
        if (random == 1) {
            Cow entity = EntityType.COW.create(world);
            spawnAnimal(world, entity, attacker);
        } else if (random == 2) {
            Sheep entity = EntityType.SHEEP.create(world);
            spawnAnimal(world, entity, attacker);
        } else if (random == 3) {
            Chicken entity = EntityType.CHICKEN.create(world);
            spawnAnimal(world, entity, attacker);
        } else if (random == 4) {
            Pig entity = EntityType.PIG.create(world);
            spawnAnimal(world, entity, attacker);
        }
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        Level world = context.getAttacker().getCommandSenderWorld();
        if (RANDOM.nextFloat() <= modifier.getLevel() * 0.2f && context.isFullyCharged()) {
            spawn(world, context.getAttacker(), null);
        }
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        Level world = context.getLiving().getCommandSenderWorld();
        if (RANDOM.nextFloat() <= modifier.getLevel() * 0.2f && context.isEffective()) {
            spawn(world, context.getLiving(), null);
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if(attacker != null) {
            Level world = attacker.getCommandSenderWorld();
            if (RANDOM.nextFloat() <= modifier.getLevel() * 0.2f && projectile instanceof AbstractArrow arrow && arrow.isCritArrow()) {
                spawn(world, attacker, null);
            }
        }
        return false;
    }
}
