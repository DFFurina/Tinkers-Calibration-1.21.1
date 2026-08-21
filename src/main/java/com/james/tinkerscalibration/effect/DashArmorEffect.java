package com.james.tinkerscalibration.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

public class DashArmorEffect extends NoMilkEffect {
    public static final String uuidArmor = "9a35acc8-11c9-4213-af49-76decfe7a8db";

    public DashArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x5A603E, true);
        addAttributeModifier(Attributes.ARMOR, uuidArmor, 0.2, AttributeModifier.Operation.ADD_VALUE);
    }
}
