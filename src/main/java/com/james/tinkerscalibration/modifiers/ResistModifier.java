package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.Utils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.TinkerModifiers;

import javax.annotation.Nullable;

public class ResistModifier extends Modifier{
    /*@Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_LAUNCH);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, ModDataNBT persistentData, boolean primary) {
        if (primary && (arrow == null || arrow.isCritArrow())) {
            int effectLevel = Math.min(6, TinkerModifiers.momentumRangedEffect.get().getLevel(shooter) + 1);
            Utils.resistEffect.get().apply(shooter, 3 * 20, effectLevel, true);
        }
    }
    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        if (!context.isExtraAttack() && context.isFullyCharged()) {
            LivingEntity attacker = context.getAttacker();
            int effectLevel = Math.min(6, TinkerModifiers.insatiableEffect.get().getLevel(attacker) + 1);
            Utils.resistEffect.get().apply(attacker, 3 * 20, effectLevel, true);
        }
        return 0;
    }
    @Override
    public boolean isSourceBlocked(IToolStackView tool, int level, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount) {
        int effect = Utils.resistEffect.get().getLevel((LivingEntity) source.getEntity());
        if(effect == 0)
            return source.isFall();
        else if(effect == 1)
            return source.isFall() || source.isFire();
        else if(effect == 2)
            return source.isFall() || source.isFire() || source.isExplosion();
        else if(effect == 3)
            return source.isFall() || source.isFire() || source.isExplosion() || source.isProjectile();
        else if(effect == 4)
            return source.isFall() || source.isFire() || source.isExplosion() || source.isProjectile() || source.isBypassArmor();
        else if(effect == 5)
            return source.isFall() || source.isFire() || source.isExplosion() || source.isProjectile() || source.isBypassArmor() || source.isMagic();
        else return effect == 6;
    }

     */
}
