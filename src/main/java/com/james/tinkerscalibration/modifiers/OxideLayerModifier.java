package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.TinkersCalibration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;

public class OxideLayerModifier extends Modifier {
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "oxide_mod");
    private static final Component UNBREAKING = TConstruct.makeTranslation("modifier", "oxide.unbreaking");

    @Override
    public int getPriority() {
        return 125;
    }
/*
    @Override
    public int onDamageTool(IToolStackView tool, int level, int amount, @javax.annotation.Nullable LivingEntity holder) {
        ModDataNBT persistantData = tool.getPersistentData();
        if (persistantData.contains(KEY, 5)) {
            float value = persistantData.getFloat(KEY);
            if (value >= 1.5 * level) {
                return damageReinforced(amount, (float) (0.15 * level));
            } else {
                return damageReinforced(amount, value / 10);
            }
        }
        return amount;
    }

    @Override
    public void onRemoved(IToolStackView tool) {
        tool.getPersistentData().remove(KEY);
    }

    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, int level, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        ;
        ModDataNBT persistantData = tool.getPersistentData();
        if (!world.isClientSide && holder.tickCount % (300 / level) == 0 && holder.getUseItem() != stack && isSelected) {
            if (persistantData.getFloat(KEY) <= 1.5 * level && RANDOM.nextFloat() <= 0.6f * level)
                persistantData.putFloat(KEY, persistantData.getFloat(KEY) + 0.1f);
        }
    }

    @Override
    public void addInformation(@Nonnull IToolStackView tool, int level, @Nullable Player player, @Nonnull List<Component> tooltip, @Nonnull TooltipKey tooltipKey, @Nonnull TooltipFlag tooltipFlag) {
        if (player != null) {
            ModDataNBT persistantData = tool.getPersistentData();
            if (persistantData.contains(KEY, 5)) {
                float value = persistantData.getFloat(KEY);
                if (value >= 1.5 * level) {
                    addPercentTooltip(UNBREAKING, 0.15 * level, tooltip);

                } else if (value < 1.5 * level) {
                    addPercentTooltip(UNBREAKING, value / 10, tooltip);
                }
            }
        }
    }

 */
}
