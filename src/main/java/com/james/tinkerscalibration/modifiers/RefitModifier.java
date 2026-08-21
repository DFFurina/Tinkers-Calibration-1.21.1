package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.impl.DurabilityShieldModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RefitModifier {
    /*@Override
    protected int getShieldCapacity(IToolStackView tool, int level) {
        return level;
    }

    @Override
    public int getPriority() {
        return 300;
    }

    @Nullable
    @Override
    public Boolean showDurabilityBar(IToolStackView tool, int level) {
        // only show if we have any shield
        return getShield(tool) > 0 ? true : null;
    }

    @Override
    public int getDurabilityRGB(IToolStackView tool, int level) {
        if (getShield(tool) > 0) {
            return 0x7662E5;
        }
        return -1;
    }
    public int getFalsite(IToolStackView tool)
    {
        return getShield(tool);
    }
    public void addFalsite(IToolStackView tool, int amount) {
        addShield(tool, tool.getModifierLevel(this), amount);
    }
    @Override
    public int onDamageTool(IToolStackView tool, int level, int amount, @Nullable LivingEntity holder) {
        int treeWall = getFalsite(tool);
        if (treeWall > 0) {
            addFalsite(tool, -1);
            return 0;
        }
        return amount;
    }

     */
}
