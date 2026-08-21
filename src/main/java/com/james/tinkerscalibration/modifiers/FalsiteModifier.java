package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.TinkersCalibration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.DurabilityShieldModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public class FalsiteModifier extends DurabilityShieldModifier implements TooltipModifierHook {

    private static final Component FALSITE = TConstruct.makeTranslation("modifier", "falsite");
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "falsite");
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.TOOL_DAMAGE);
    }
    public boolean shouldDisplay(boolean advanced) {
        return false;
    }

    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        tool.getPersistentData().remove(KEY);
        return null;
    }

    @Override
    public int onDamageTool(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i, @Nullable LivingEntity livingEntity) {
        int falsiteUses = iToolStackView.getPersistentData().getInt(KEY);
        if (falsiteUses > 0) {
            falsiteUses -= i;
            iToolStackView.getPersistentData().putInt(KEY, falsiteUses);
            if (falsiteUses <= 0) {
                return Math.abs(falsiteUses);
            } else {
                return 0;
            }
        } else {
            return i;
        }
    }

    @Override
    public int getShieldCapacity(IToolStackView iToolStackView, ModifierEntry modifierEntry) {
        return 0;
    }

    @Nullable
    @Override
    public Boolean showDurabilityBar(IToolStackView iToolStackView, ModifierEntry modifierEntry) {
        return iToolStackView.getPersistentData().getInt(KEY) <= Math.max(Math.floorDiv(iToolStackView.getStats().getInt(ToolStats.DURABILITY), 5), 30) ? true : null;
    }

    @Override
    public int getDurabilityRGB(IToolStackView iToolStackView, ModifierEntry modifierEntry) {
        if(iToolStackView.getPersistentData().getInt(KEY) > 0)
        {
            return Mth.color(0.5F, 1.0F, 1.0F);
        }
        return -1;
    }

    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        int shield = tool.getPersistentData().getInt(KEY);
        return shield > 0 ? DurabilityDisplayModifierHook.getWidthFor(shield, Math.max(Math.floorDiv(tool.getStats().getInt(ToolStats.DURABILITY), 5), 30)) : 0;
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @javax.annotation.Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (tool.getPersistentData().getInt(KEY) > 0) {
            if (tooltipKey == TooltipKey.SHIFT) {
                tooltip.add(Component.translatable("").append(FALSITE).append(String.valueOf(tool.getPersistentData().getInt(KEY))).append(" / ").append(String.valueOf(Math.max(Math.floorDiv(tool.getStats().getInt(ToolStats.DURABILITY), 5), 30))).withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
