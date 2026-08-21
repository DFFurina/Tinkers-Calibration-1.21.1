package com.james.tinkerscalibration.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

public class BloodThirstyArmorEffect extends NoMilkEffect {
    public static final String uuidSpeed = "10ecd25b-1d43-4b75-88a8-a65d232041e9";

    public BloodThirstyArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x5A603E, true);
        addAttributeModifier(Attributes.ATTACK_SPEED, uuidSpeed, 0.2, AttributeModifier.Operation.ADD_VALUE);
    }
}
