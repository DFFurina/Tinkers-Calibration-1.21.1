package com.james.tinkerscalibration.modifiers.armor;

import com.james.tinkerscalibration.TinkersCalibration;
import com.james.tinkerscalibration.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.mantle.client.TooltipKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ArmorVibratingModifier extends Modifier implements OnAttackedModifierHook, AttributesModifierHook, InventoryTickModifierHook, ModifierRemovalHook, TooltipModifierHook {
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "vibrating_armor");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED, ModifierHooks.ATTRIBUTES, ModifierHooks.INVENTORY_TICK);
        hookBuilder.addHook(this, ModifierHooks.REMOVE, ModifierHooks.TOOLTIP);
    }
    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        LivingEntity user = context.getEntity();
        Entity attacker = source.getEntity();
        if (isDirectDamage && !user.getCommandSenderWorld().isClientSide && attacker instanceof LivingEntity livingAttacker) {
            context.getTinkerData().ifPresent(data -> {
                int effectLevel = Math.min(7, Utils.vibratingArmorEffect.get().getLevel(user) + 1);
                Utils.vibratingArmorEffect.get().apply(user, 5 * 20, effectLevel, true);
                ModDataNBT persistentData = tool.getPersistentData();
                persistentData.putFloat(KEY, effectLevel + 1);
                float angle = attacker.getYRot() * (float) Math.PI / 180F;
                livingAttacker.knockback(effectLevel, -Math.sin(angle), Math.cos(angle));
            });
        }
    }
    public boolean isArmor(EquipmentSlot slot)
    {
        return slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (isArmor(slot)) {
            double boost = tool.getPersistentData().getFloat(KEY) * 0.05;
            if (boost != 0) {
                consumer.accept(Attributes.MOVEMENT_SPEED.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_vibrating_speed"), -boost, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }
    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide) {
            ModDataNBT persistentData = tool.getPersistentData();
            if (Utils.vibratingArmorEffect.get().getLevel(holder) == -1) {
                persistentData.remove(KEY);
            }
        }
    }
    @Override
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        tool.getPersistentData().remove(getId());
        return null;
    }
    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        boolean harvest = tool.hasTag(TinkerTags.Items.ARMOR);
        if (harvest) {
            float bonus = tool.getPersistentData().getFloat(KEY);
            if (player != null && tooltipKey == TooltipKey.SHIFT && bonus > 0) {
                tooltip.add(Component.translatable("modifier.tinkerscalibration.vibrating_armor.knockback").append(" ").append(String.valueOf(bonus)).withStyle(style -> style.withColor(TextColor.fromRgb(0xE5DA85))));
            }
        }
    }
}
