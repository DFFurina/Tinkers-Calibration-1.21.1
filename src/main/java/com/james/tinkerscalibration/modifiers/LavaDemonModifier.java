package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nonnull;

public class LavaDemonModifier extends Modifier {
    /*@Override
    public void onInventoryTick(@Nonnull IToolStackView tool, int level, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder.tickCount % 20 == 0 && holder.getUseItem() != stack && isSelected) {
            world.addParticle(ParticleTypes.FLAME, holder.getX() + RANDOM.nextDouble() - 0.5,
                    holder.getY() + 1,
                    holder.getZ() + RANDOM.nextDouble() - 0.5,
                    0, 0, 0);
            if(holder.isOnFire() && !holder.hasEffect(MobEffects.FIRE_RESISTANCE))
            {
                holder.heal(1);
                ToolDamageUtil.repair(tool, 2);
            }
        }
    }
    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        if (target != null) {
            target.removeEffect(MobEffects.FIRE_RESISTANCE);
            target.hurt(DamageSource.LAVA, 5 * level);
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 160 * level));
            target.setSecondsOnFire(8 * level);
        }
        return 0;
    }

     */
}
