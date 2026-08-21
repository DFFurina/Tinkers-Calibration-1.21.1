package com.james.tinkerscalibration.modifiers.armor;

import com.james.tinkerscalibration.TinkersCalibration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.utils.Util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ArmorDominateModifier extends Modifier implements AttributesModifierHook, InventoryTickModifierHook, TooltipModifierHook, ModifierRemovalHook {
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "dominate_armor");
    private static final Component ATTACK_DAMAGE = TConstruct.makeTranslation("modifier", "dominate.attack_damage");
    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        ModDataNBT persistentData = tool.getPersistentData();
        if (!world.isClientSide) {
            persistentData.putFloat(KEY, holder.getHealth());
        }
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK, ModifierHooks.REMOVE, ModifierHooks.ATTRIBUTES, ModifierHooks.TOOLTIP);
    }
    @Override
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        tool.getPersistentData().remove(getId());
        return null;
    }
    public boolean isArmor(EquipmentSlot slot)
    {
        return slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (isArmor(slot)) {
            ModDataNBT persistentData = tool.getPersistentData();
            float value = persistentData.getFloat(KEY);
            if (value != 0) {
                consumer.accept(Attributes.ATTACK_DAMAGE.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_dominate_attack_damage"), Math.cbrt(value), AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (tool.hasTag(TinkerTags.Items.ARMOR)) {
            ModDataNBT persistentData = tool.getPersistentData();
            float value = persistentData.getFloat(KEY);
            double boost = Math.cbrt(value);
            if (boost != 0) {
                tooltip.add(applyStyle(Component.translatable(Util.PERCENT_BOOST_FORMAT.format(boost)).append(" ").append(ATTACK_DAMAGE)));
            }
        }
    }
}
