package com.james.tinkerscalibration.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;

public class LionHeartEffect extends NoMilkEffect {
    private static final String SOURCE_KEY = TConstruct.prefix("lion_heart");

    public LionHeartEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xBC8047, true);
        addAttributeModifier(Attributes.ARMOR, uuidArmor, 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    public static final String uuidArmor = "lionheart_armor";
}
