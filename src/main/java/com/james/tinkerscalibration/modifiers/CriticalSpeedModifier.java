package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.TinkersCalibration;
import com.james.tinkerscalibration.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.function.BiConsumer;

public class CriticalSpeedModifier extends Modifier implements MeleeHitModifierHook, AttributesModifierHook, InventoryTickModifierHook, ModifierRemovalHook {
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "critical_speed");

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        // 8 hits gets you to max, levels faster at higher levels
        if (!context.isExtraAttack() && context.isCritical()) {
            LivingEntity attacker = context.getAttacker();
            int effectLevel = Math.min(7, Utils.criticalspeedEffect.get().getLevel(attacker) + 1);
            Utils.criticalspeedEffect.get().apply(attacker, 5 * 20, effectLevel, false);
            ModDataNBT persistentData = tool.getPersistentData();
            persistentData.putFloat(KEY, effectLevel + 1);
        }
    }

    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder.getUseItem() != stack && isSelected) {
            ModDataNBT persistentData = tool.getPersistentData();
            if (Utils.criticalspeedEffect.get().getLevel(holder) == -1) {
                persistentData.remove(KEY);
            }
        }
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES, ModifierHooks.MELEE_HIT, ModifierHooks.INVENTORY_TICK, ModifierHooks.REMOVE);
    }
    @Override
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        tool.getPersistentData().remove(modifier.getId());
        return null;
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (slot == EquipmentSlot.MAINHAND) {
            double boost = tool.getPersistentData().getFloat(KEY) * modifier.getLevel() / 8f;
            if (boost != 0) {
                consumer.accept(Attributes.ATTACK_SPEED.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "critical_speed"), boost, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
        }
    }

}