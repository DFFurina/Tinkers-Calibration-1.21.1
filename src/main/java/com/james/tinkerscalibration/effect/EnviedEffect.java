package com.james.tinkerscalibration.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

import javax.print.attribute.Attribute;

public class EnviedEffect extends NoMilkEffect {
    private static final String SOURCE_KEY = TConstruct.prefix("envied");
    public static final String uuidMovementSpeed = "envied_movement_speed";

    public EnviedEffect() {
        super(MobEffectCategory.HARMFUL, 0x5A603E, true);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, uuidMovementSpeed, -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }
}
