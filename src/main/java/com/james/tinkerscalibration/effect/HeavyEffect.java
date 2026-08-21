package com.james.tinkerscalibration.effect;

import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

public class HeavyEffect extends NoMilkEffect {
    public HeavyEffect() {
        super(MobEffectCategory.NEUTRAL, ChatFormatting.DARK_GREEN.getColor(), true);

    }

    public boolean isDurationEffectTick(int tick, int level) {
        return tick > 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity living, int level) {
        if (living instanceof Player && ((Player) living).isCreative()) {
            return false;
        }

        level++;
        Vec3 motion = living.getDeltaMovement();
        living.setDeltaMovement(new Vec3(
                    motion.x,
                    motion.y - 0.08f * level,
                    motion.z
        ));
        return true;
    }
}