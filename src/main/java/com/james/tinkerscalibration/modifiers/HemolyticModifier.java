package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class HemolyticModifier extends Modifier {
    /*@Override
    public float getEntityDamage(IToolStackView tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        return damage * (1 + 0.1f * level);
    }

    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        Level world = target.getCommandSenderWorld();
        if (!world.isClientSide) {
            ToolDamageUtil.repair(tool, (int) (damageDealt / 6));
        }
        return 0;
    }

     */

}
