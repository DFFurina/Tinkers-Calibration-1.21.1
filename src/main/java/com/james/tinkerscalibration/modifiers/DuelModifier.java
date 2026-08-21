package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.Utils;
import com.sun.jna.Memory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public class DuelModifier extends Modifier implements MeleeHitModifierHook, MeleeDamageModifierHook, ProjectileHitModifierHook, ConditionalStatModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_HIT, ModifierHooks.CONDITIONAL_STAT);
    }
    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        Player player = context.getPlayerAttacker();
        if(player != null)
        {
            if(getAnger(player) > 5)
            {
                return (float) (damage + 5 * (getAnger(player) - 5) * Math.cbrt(modifier.getLevel()));
            }
        }
        return damage;
    }
    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        Player player = context.getPlayerAttacker();
        if(player != null)
        {
            if(getAnger(player) <= 5)
            {
                return knockback * (1 + (6 - getAnger(player)) * 0.2f);
            }
        }
        return knockback;
    }
    public int getAnger(Player player)
    {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        List<Mob> targets = player.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x - 10, y - 10, z - 10, x + 10, y + 10, z + 10));
        int sum = 0;
        for (Mob target : targets) {
            if (target instanceof Monster) {
                Brain<?> brain = target.getBrain();
                if(brain.getMemory(MemoryModuleType.ANGRY_AT).orElse(null) == player.getUUID())
                    sum++;
            }
        }
        return sum;
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        if (stat == ToolStats.PROJECTILE_DAMAGE) {
            if(living instanceof Player player)
            {
                if(getAnger(player) > 5)
                {
                    return (float) (baseValue * (1 + (getAnger(player) - 5) * Math.cbrt(modifier.getLevel()) * 0.1f));
                }
            }
        }
        return baseValue;
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if(attacker instanceof Player player && projectile instanceof AbstractArrow arrow)
        {
            if(getAnger(player) <= 5)
            {
                // knockback modification removed - AbstractArrow API changed in 1.21.1
            }
        }
        return false;
    }
}
