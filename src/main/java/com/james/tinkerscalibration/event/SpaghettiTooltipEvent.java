package com.james.tinkerscalibration.event;

import com.james.tinkerscalibration.TinkersCalibration;
import com.james.tinkerscalibration.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.bus.api.SubscribeEvent;
import slimeknights.mantle.client.SafeClientAccess;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class SpaghettiTooltipEvent {
    private static final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "spaghetti");
    private static final Component SPAGHETTI = TConstruct.makeTranslation("modifier", "spaghetti");
    private static final Component SPAGHETTI1 = TConstruct.makeTranslation("modifier", "spaghetti1");
    private static final Component SPAGHETTI1_1 = TConstruct.makeTranslation("modifier", "spaghetti1_1");
    private static final Component SPAGHETTI2 = TConstruct.makeTranslation("modifier", "spaghetti2");
    private static final Component SPAGHETTI2_1 = TConstruct.makeTranslation("modifier", "spaghetti2_1");
    private static final Component SPAGHETTI3 = TConstruct.makeTranslation("modifier", "spaghetti3");

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof IModifiable) {
            ToolStack tool = ToolStack.from(stack);
            if (tool.getPersistentData().contains(KEY, 3)) {
                if (!SafeClientAccess.getTooltipKey().equals(TooltipKey.SHIFT)) {
                    event.getToolTip().add(1, Component.literal("").append(SPAGHETTI).append(TooltipBuilder.formatPartialAmount(tool.getPersistentData().getInt(KEY), 100)));
                    int length = event.getToolTip().size();
                    switch (tool.getModifierLevel(Utils.spaghetti.get())) {
                        case 1:
                            event.getToolTip().add(length - 1, Component.translatable("").append(SPAGHETTI1).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
                            event.getToolTip().add(length, Component.translatable("").append(SPAGHETTI1_1).withStyle(ChatFormatting.GRAY));
                            break;
                        case 2:
                            event.getToolTip().add(length - 1, Component.translatable("").append(SPAGHETTI2).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
                            event.getToolTip().add(length, Component.translatable("").append(SPAGHETTI2_1).withStyle(ChatFormatting.GRAY));
                            break;
                        case 3:
                            event.getToolTip().add(length - 1, Component.translatable("").append(SPAGHETTI3).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
                            break;
                    }
                }
            }
        }
    }
}
