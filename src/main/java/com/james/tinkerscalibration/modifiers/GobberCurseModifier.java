package com.james.tinkerscalibration.modifiers;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolDataNBT;

public class GobberCurseModifier extends NoLevelsModifier implements VolatileDataModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.VOLATILE_DATA);
    }


    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ToolDataNBT toolDataNBT) {
        IModDataView persistentData = context.getPersistentData();
        int number = toolDataNBT.getSlots(SlotType.UPGRADE);
        if (number >= 2) {
            toolDataNBT.addSlots(SlotType.UPGRADE, -number);
        } else {
            toolDataNBT.addSlots(SlotType.UPGRADE, -2);
        }
    }
}
