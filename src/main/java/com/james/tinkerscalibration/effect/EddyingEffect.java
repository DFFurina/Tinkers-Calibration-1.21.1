package com.james.tinkerscalibration.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

public class EddyingEffect extends NoMilkEffect {
    public EddyingEffect() {
        super(MobEffectCategory.NEUTRAL, 0x3f516b, true);
    }

    public boolean isDurationEffectTick(int tick, int level) {
        return tick > 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity target, int level) {
        {
            if (!target.getCommandSenderWorld().isClientSide && target.isInWater()) {
                final int cycle = 60;
                final float factorCycle = 360f / cycle;

                final float angle = target.tickCount % cycle * factorCycle * (float) Math.PI / 180;

                Vec3 motion = target.getDeltaMovement();
                Vec3 motionNew = new Vec3(
                        motion.x + Math.cos(angle) * 0.1,
                        motion.y - 0.12,
                        motion.z + Math.sin(angle) * 0.1
                );
                target.setDeltaMovement(motionNew);
            }
        }
        return true;
    }
}
