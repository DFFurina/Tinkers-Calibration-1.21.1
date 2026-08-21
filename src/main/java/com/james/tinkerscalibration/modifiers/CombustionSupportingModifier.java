package com.james.tinkerscalibration.modifiers;

import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class CombustionSupportingModifier extends Modifier {
    /*@Override
    public float getEntityDamage(IToolStackView tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        if (context.getTarget().isOnFire()) {
            ToolDamageUtil.damageAnimated(tool, 5, context.getAttacker());
            return damage + baseDamage / 2;
        }
        return damage;
    }

     */
}
