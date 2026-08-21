package com.james.tinkerscalibration.modifiers;

import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nonnull;

public class MossyModifier extends Modifier implements BreakSpeedModifierHook {
    @Override
    public int getPriority() {
        return 170;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED);
    }
    @Override
    public void onBreakSpeed(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull PlayerEvent.BreakSpeed event, @Nonnull Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        boolean isMossy = event.getState().is(BlockTags.MOSS_REPLACEABLE);
        if (isMossy) {
            event.setNewSpeed(event.getNewSpeed() * 1.5f * modifier.getLevel());
        }
    }
}

