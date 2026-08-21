package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nonnull;

import static net.minecraft.world.level.block.FireBlock.AGE;
import static net.minecraft.world.level.block.RedStoneWireBlock.POWER;

public class FireModifier extends Modifier {
    /*@Override
    public void onInventoryTick(@Nonnull IToolStackView tool, int level, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        ;
        int range = 6 * level;
        if (!world.isClientSide && holder.getUseItem() != stack && isSelected) {
            for (int i = -range; i <= range; i++) {
                for (int j = -range; j <= range; j++) {
                    for (int k = -range; k <= range; k++) {
                        BlockPos pos = new BlockPos(i + holder.getX(), j + holder.getY(), k + holder.getZ());
                        BlockState state = world.getBlockState(pos);
                        if (state.is(BlockTags.FIRE)) {
                            state.setValue(AGE, 0);
                        }
                        if (state.is(Blocks.REDSTONE_WIRE)) {
                            state.setValue(POWER, 15);
                        }
                    }
                }
            }
        }
    }

     */
}
