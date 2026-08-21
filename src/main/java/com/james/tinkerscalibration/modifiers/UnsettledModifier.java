package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class UnsettledModifier extends Modifier {

    /*@Override
    public void onInventoryTick(IToolStackView tool, int level, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (holder.tickCount % 16 != 0 || !holder.isAlive() || !ToolDamageUtil.isBroken(stack) || !(holder instanceof Player))
            return;
//		final int currentDurability = tool.getCurrentDurability();
//		final int maxDurability = currentDurability + currentDamage;
        final Player player = (Player) holder;
        final Inventory inv = player.getInventory();

        int currentDamage = tool.getDamage();
        int sizeInv = inv.getContainerSize();
        int countAbsorb = 0;
        while (sizeInv-- > 0) {
            ItemStack stackInv = inv.getItem(sizeInv);
            if (stackInv.isEmpty() || !stackInv.is(TinkerTags.Items.MODIFIABLE)) continue;

            ToolStack toolInv = ToolStack.from(stackInv);
            ToolDamageUtil.directDamage(toolInv, 2, holder, stackInv);
            ToolDamageUtil.repair(tool, 1);
            currentDamage--;
            countAbsorb++;
            if (currentDamage <= 0 || countAbsorb >= 4) return;
        }
    }

     */
}
