package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nonnull;

public class ChainingModifier extends Modifier {
    boolean chain = false;

    /*@Override
    public float getEntityDamage(@Nonnull IToolStackView tool, int level, @Nonnull ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity attacker = context.getAttacker();
        LivingEntity target = context.getLivingTarget();
        if (!chain && target != null && !target.isAlive()) {
            chain = true;
            return damage;
        } else if (chain) {
            chain = false;
            return damage * (1 + 0.6f * level);
        }
        return damage;
    }
*/
}
