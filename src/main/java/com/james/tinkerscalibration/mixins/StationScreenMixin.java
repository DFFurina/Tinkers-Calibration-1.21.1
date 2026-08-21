package com.james.tinkerscalibration.mixins;

import com.james.tinkerscalibration.TinkersCalibration;
import com.james.tinkerscalibration.Utils;
import com.james.tinkerscalibration.item.SpaghettiItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.mantle.client.SafeClientAccess;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.item.ITinkerStationDisplay;
import slimeknights.tconstruct.library.tools.layout.StationSlotLayout;
import slimeknights.tconstruct.library.tools.nbt.LazyToolStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.TinkerTooltipFlags;
import slimeknights.tconstruct.library.utils.Util;
import slimeknights.tconstruct.tables.client.inventory.BaseTabbedScreen;
import slimeknights.tconstruct.tables.client.inventory.TinkerStationScreen;
import slimeknights.tconstruct.tables.client.inventory.ToolTableScreen;
import slimeknights.tconstruct.tables.client.inventory.module.InfoPanelScreen;
import slimeknights.tconstruct.tables.menu.TabbedContainerMenu;

import java.util.ArrayList;
import java.util.List;

@Mixin(ToolTableScreen.class)
public abstract class StationScreenMixin <T extends BlockEntity, C extends TabbedContainerMenu<T>> extends BaseTabbedScreen<T,C> {
    @Shadow(remap = false) @Final protected InfoPanelScreen<ToolTableScreen<T, C>, C> tinkerInfo;
    private static final Component SPAGHETTI = TConstruct.makeTranslation("modifier", "spaghetti");
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "spaghetti");

    public StationScreenMixin(C c, Inventory playerInventory, Component title) {
        super(c, playerInventory, title);
    }


    @Inject(remap = false, method = "updateToolPanel", at = @At("RETURN"))
    private void getDescription(LazyToolStack tool, CallbackInfo ci) {
        if (tool.getItem() instanceof ITinkerStationDisplay display && tool.getItem() instanceof SpaghettiItem) {
            int level = tool.getTool().getModifierLevel(Utils.spaghetti.get());
            List<Component> list = new ArrayList<>();
            list.add(Component.literal("").append(SPAGHETTI).append(TooltipBuilder.formatPartialAmount(tool.getTool().getPersistentData().getInt(KEY), 100)));
            list.add(Component.translatable("modifier.tinkerscalibration.spaghetti.food_level").append(Component.literal(Util.COMMA_FORMAT.format((long) level < 3 ? 3 : 5)).withStyle((style) -> style.withColor(-2661276))));
            list.add(Component.translatable("modifier.tinkerscalibration.spaghetti.saturation_level").append(Component.literal(Util.COMMA_FORMAT.format((long) level < 3 ? level * 1.2f + 1.2f : 6)).withStyle(style -> style.withColor(-8871731))));

            if (level > 1) {
                list.add(Component.translatable("modifier.tinkerscalibration.spaghetti.sauce").withStyle(style -> style.withColor(-8042548)));
                if (level > 2)
                    list.add(Component.translatable("modifier.tinkerscalibration.spaghetti.meat").withStyle(style -> style.withColor(-8042548)));
            }
            tinkerInfo.setText(list);

        }
    }
}