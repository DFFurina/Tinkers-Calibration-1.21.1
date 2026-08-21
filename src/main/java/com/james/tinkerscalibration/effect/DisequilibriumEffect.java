package com.james.tinkerscalibration.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

public class DisequilibriumEffect extends NoMilkEffect {
    private static final String SOURCE_KEY = TConstruct.prefix("disequilibrium");

    public DisequilibriumEffect() {
        super(MobEffectCategory.HARMFUL, 0x737173, true);
    }

    public boolean isDurationEffectTick(int tick, int level) {
        return tick % 2 == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity target, int level) {
        Vec3 motion = target.getDeltaMovement();
        if (!target.onGround()) {
            target.setDeltaMovement(motion.x,
                    motion.y - 0.8f * level,
                    motion.z);
        }
        return true;
    }
}
