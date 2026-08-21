package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class DragonKillerModifier extends Modifier {
    /*@Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        if (damageDealt > 0 && target != null) {
            //String name = target.getName().getString();

            if (target.getHealth() > 0) {
                target.invulnerableTime = 0;
                target.hurt(
                        DamageSource.mobAttack(context.getAttacker()),
                        3 * level + damageDealt * 0.2f
                );
            }
        }
        return 0;
    }

     */
}
