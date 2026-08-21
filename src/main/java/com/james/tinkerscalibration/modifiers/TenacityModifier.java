package com.james.tinkerscalibration.modifiers;

import net.minecraft.network.chat.Component;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nullable;
import java.util.List;


public class TenacityModifier extends Modifier {
    /*@Override
    public int onDamageTool(IToolStackView tool, int level, int amount, @javax.annotation.Nullable LivingEntity holder) {
        int current = tool.getCurrentDurability();
        if (current <= tool.getStats().get(ToolStats.DURABILITY) * 0.4) {
            float percentage = current / tool.getStats().get(ToolStats.DURABILITY);
            if (percentage >= 0.1f)
                return damageReinforced(amount, 1 / percentage / 20 + 0.05f * (level - 1));
            else {
                return damageReinforced(amount, 0.5f + 0.05f * (level - 1));
            }
        }
        return amount;
    }

    @Override
    public void addInformation(IToolStackView tool, int level, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag tooltipFlag) {
        boolean harvest = tool.hasTag(TinkerTags.Items.HARVEST);
        if (harvest || tool.hasTag(TinkerTags.Items.RANGED)) {
            if (player != null && key == TooltipKey.SHIFT) {
                int current = tool.getCurrentDurability();
                if (current <= ToolStats.DURABILITY.getMaxValue() * 0.4f) {
                    float percentage = current / ToolStats.DURABILITY.getMaxValue();
                    if (percentage >= 0.1f)
                        addPercentTooltip(Component.translatable("modifier.tinkerscalibration.tenacity.unbreaking"), 1 / percentage / 20 + 0.05f * (level - 1), tooltip);
                    else {
                        addPercentTooltip(Component.translatable("modifier.tinkerscalibration.tenacity.unbreaking"), 0.5f + 0.05f * (level - 1), tooltip);
                    }
                }
            }
        }
    }

     */
}
