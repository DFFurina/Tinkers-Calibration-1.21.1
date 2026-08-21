package com.james.tinkerscalibration.tiers;

import moze_intel.projecte.gameObjs.PETags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class DarkMatterTier implements Tier {

    public static Tier instance = new DarkMatterTier();

    @Override
    public int getUses() {
        return 4000;
    }

    @Override
    public float getSpeed() {
        return 4f;
    }

    @Override
    public float getAttackDamageBonus() {
        return 4f;
    }

    public int getLevel() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getEnchantmentValue() {
        return 40;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    @Override
    public net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
        return PETags.Blocks.NEEDS_DARK_MATTER_TOOL;
    }
}
