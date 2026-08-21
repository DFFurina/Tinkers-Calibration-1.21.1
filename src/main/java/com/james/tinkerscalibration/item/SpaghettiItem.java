package com.james.tinkerscalibration.item;

import com.james.tinkerscalibration.TinkersCalibration;
import com.james.tinkerscalibration.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.client.SafeClientAccess;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.Util;

import javax.annotation.Nullable;
import java.util.List;

import static slimeknights.tconstruct.library.tools.helper.TooltipUtil.TOOLTIP_HOLD_SHIFT;
import static slimeknights.tconstruct.library.tools.helper.TooltipUtil.addModifierNames;


public class SpaghettiItem extends ModifiableItem {
    private static final Component SPAGHETTI = TConstruct.makeTranslation("modifier", "spaghetti");

    public SpaghettiItem(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "spaghetti");

    public boolean isBarVisible(@NotNull ItemStack stack) {
        return ToolStack.from(stack).getPersistentData().getInt(KEY) < 100;
    }

    public int getBarColor(@NotNull ItemStack pStack) {
        float f = Math.max(0.0F, (float) ToolStack.from(pStack).getPersistentData().getInt(KEY) / 100);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);

    }

    public int getBarWidth(ItemStack pStack) {
        int uses = ToolStack.from(pStack).getPersistentData().getInt(KEY);
        return uses > 0 ? DurabilityDisplayModifierHook.getWidthFor(uses, 100) : 0;

    }
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        ToolStack tool = ToolStack.from(stack);
        int level = tool.getModifierLevel(Utils.spaghetti.get());
        if(SafeClientAccess.getTooltipKey() == TooltipKey.SHIFT) {
            tooltip.add(Component.literal("").append(SPAGHETTI).append(TooltipBuilder.formatPartialAmount(tool.getPersistentData().getInt(KEY), 100)));
            tooltip.add(Component.translatable("modifier.tinkerscalibration.spaghetti.food_level").append(Component.literal(Util.COMMA_FORMAT.format((long) level < 3 ? 3 : 5)).withStyle((style) -> style.withColor(-2661276))));
            tooltip.add(Component.translatable("modifier.tinkerscalibration.spaghetti.saturation_level").append(Component.literal(Util.COMMA_FORMAT.format((long) level < 3 ? level * 1.2f + 1.2f : 6)).withStyle(style -> style.withColor(-8871731))));
            if(tool.getFreeSlots(SlotType.UPGRADE) > 0) {
                tooltip.add(Component.translatable("modifier.tinkerscalibration.spaghetti.upgrades").append(Component.literal(Util.COMMA_FORMAT.format(tool.getFreeSlots(SlotType.UPGRADE))).withStyle(style -> style.withColor(-3360185))));
            }
            if (level > 1) {
                tooltip.add(Component.translatable("modifier.tinkerscalibration.spaghetti.sauce").withStyle(style -> style.withColor(-8042548)));
                if (level > 2)
                    tooltip.add(Component.translatable("modifier.tinkerscalibration.spaghetti.meat").withStyle(style -> style.withColor(-8042548)));
            }
        }
        else
        {
            addModifierNames(stack, tool, null, tooltip, flag);
            tooltip.add(TOOLTIP_HOLD_SHIFT);
        }

    }
}
