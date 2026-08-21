package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class CherishingModifier extends Modifier {
    /*@Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        Level world = target != null ? target.getCommandSenderWorld() : null;
        if (target == null) return 0;

        final BlockState stateFlower1 = Blocks.SUNFLOWER.defaultBlockState();
        final BlockState stateFlower2 = Blocks.CORNFLOWER.defaultBlockState();
        BlockPos pos = target.getOnPos();
        for (int offsetX = -1; offsetX <= 1; offsetX++) {
            for (int offsetZ = -1; offsetZ <= 1; offsetZ++) {
                if (RANDOM.nextFloat() > 0.35 * level) continue;

                boolean wasDirt = false;
                for (int offsetY = -3; offsetY <= 1; offsetY++) {
                    BlockPos posTemp = pos.offset(offsetX, offsetY, offsetZ);
                    BlockState stateTemp = world.getBlockState(posTemp);
                    if (stateTemp.is(Blocks.AIR) && wasDirt) {
                        boolean color = RANDOM.nextBoolean();
                        world.setBlock(posTemp, color ? stateFlower1 : stateFlower2, 0);
                    }
                    wasDirt = stateTemp.is(Blocks.DIRT);
                }
            }
        }

        return 0;
    }
     */
}
