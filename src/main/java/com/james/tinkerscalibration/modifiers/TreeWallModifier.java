package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.DurabilityShieldModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TreeWallModifier extends DurabilityShieldModifier implements InventoryTickModifierHook, DurabilityDisplayModifierHook, ToolDamageModifierHook {
    @Override
    public int getShieldCapacity(IToolStackView tool, ModifierEntry modifier) {
        return modifier.getLevel();
    }

    @Override
    public int getPriority() {
        return 300;
    }

    @Nullable
    @Override
    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        // only show if we have any shield
        return getShield(tool) > 0 ? true : null;
    }

    @Override
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        return 0;
    }

    @Override
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        if (getShield(tool) > 0) {
            return 0x8fbc8f;
        }
        return -1;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK, ModifierHooks.DURABILITY_DISPLAY, ModifierHooks.TOOL_DAMAGE);
    }
    public int getTreeWall(IToolStackView tool)
    {
        return getShield(tool);
    }
    public void addTreeWall(IToolStackView tool, ModifierEntry modifier, int amount) {
        addShield(tool, modifier, amount);
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        int treeWall = getTreeWall(tool);
        if (treeWall > 0) {
            addTreeWall(tool,modifier,  -1);
            return 0;
        }
        return amount;
    }

    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder.tickCount % 120 == 0 && holder.getUseItem() != stack) {
            if(RANDOM.nextFloat() <= 0.4f * modifier.getLevel())
            {
                addTreeWall(tool, modifier, modifier.getLevel());
            }
        }
    }
}
