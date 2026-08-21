package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;


public class CreakyModifier extends Modifier {
    /*@Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        Level world = context.getAttacker().getCommandSenderWorld();
        if (RANDOM.nextFloat() <= level * 0.1f) {
            Silverfish silverfish = EntityType.SILVERFISH.create(world);
            world.addFreshEntity(silverfish);
            LivingEntity living = context.getAttacker();
            silverfish.moveTo(living.getX(), living.getY(), living.getZ());
            silverfish.spawnAnim();
        }
        return 0;
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, int level, ToolHarvestContext context) {
        Level world = context.getLiving().getCommandSenderWorld();
        if (RANDOM.nextFloat() <= level * 0.1f) {
            Silverfish silverfish = EntityType.SILVERFISH.create(world);
            world.addFreshEntity(silverfish);
            LivingEntity living = context.getLiving();
            silverfish.moveTo(living.getX(), living.getY(), living.getZ());
            silverfish.spawnAnim();
        }
    }

     */
}
