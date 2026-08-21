package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nonnull;

public class SunPowerModifier extends Modifier {
    /*@Override
    public void onInventoryTick(@Nonnull IToolStackView tool, int level, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder.tickCount % 8 == 0 && holder.getUseItem() != stack && isSelected) {
            if (world.isDay() && RANDOM.nextFloat() < (level * 0.15) && !tool.isBroken() && world.canSeeSky(holder.getOnPos())) {
                ToolDamageUtil.repair(tool, Math.round(world.getBrightness(LightLayer.SKY, holder.getOnPos()) * 0.2f));
            }
        }
    }
    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        if (target != null && damageDealt > 0 && target.getLevel().isDay() && target.getLevel().canSeeSky(target.getOnPos())) {
            target.setSecondsOnFire(Math.round(target.getLevel().getBrightness(LightLayer.SKY, target.getOnPos()) * 0.3f));
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 30, 2));
        }
        return 0;
    }

     */
}

