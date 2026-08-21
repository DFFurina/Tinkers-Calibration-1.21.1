package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DisplayNameModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DominateModifier extends Modifier implements DisplayNameModifierHook, MeleeDamageModifierHook, ProjectileHitModifierHook {
    @Override
    public float getMeleeDamage(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity target = context.getLivingTarget();
        if (target != null) {
            if(modifier.getLevel() == 1)
                return 0.25f * target.getHealth() + damage;
            if(modifier.getLevel() >= 2)
                return 0.4f * target.getHealth() + damage;
        }
        return damage;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.DISPLAY_NAME, ModifierHooks.MELEE_DAMAGE, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (projectile instanceof AbstractArrow arrow && target != null) {
            int level = modifier.getLevel();
            if (level == 1)
                arrow.setBaseDamage(arrow.getBaseDamage() + 0.25 * target.getHealth());
            if (level >= 2)
                arrow.setBaseDamage(arrow.getBaseDamage() + 0.4 * target.getHealth());
        }
        return false;
    }

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry modifier, Component name, @org.jetbrains.annotations.Nullable RegistryAccess access) {
        if(modifier.getLevel() >= 2)
            return getDisplayName(2);
        return getDisplayName(modifier.getLevel());
    }

}
