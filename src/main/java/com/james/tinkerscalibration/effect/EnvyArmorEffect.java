package com.james.tinkerscalibration.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

public class EnvyArmorEffect extends NoMilkEffect {
    private static final String SOURCE_KEY = TConstruct.prefix("envy_armor");
    public static final String uuidMovementSpeed = "13e37470-4456-4501-a1a4-9ca8bf5ebce2";

    public EnvyArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x5A603E, true);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, uuidMovementSpeed, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }
}
