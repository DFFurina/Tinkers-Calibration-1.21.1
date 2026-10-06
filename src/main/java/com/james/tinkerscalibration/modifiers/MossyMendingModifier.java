package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.library.ToolRepairHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.ItemStackUtil;

import javax.annotation.Nonnull;

public class MossyMendingModifier extends Modifier implements InventoryTickModifierHook {

    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder.tickCount % 5 == 0 && holder instanceof Player player && stack.getDamageValue() > 0 && holder.getUseItem() != stack && player.totalExperience >= 1) {
            // 自动修复改用兼容封装：否则 More Resilient Tinkers 会把每 5 tick 一次的自动修复
            // 计入永久耐久损耗，导致工具最高耐久不断下降（详见 ToolRepairHelper）
            ToolRepairHelper.repair(tool, modifier.getLevel() + 1);

            player.giveExperiencePoints(-1);
            // the tool NBT lives in the CUSTOM_DATA component; repairing only mutates the tag in place,
            // which vanilla container sync never detects, so re-set the component to sync the client
            var tag = ItemStackUtil.getTag(stack);
            if (tag != null) {
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            }
        }
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }
}
