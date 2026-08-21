package com.james.tinkerscalibration.modifiers.armor;

import com.james.tinkerscalibration.TinkersCalibration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ArmorClusteringModifier extends Modifier implements InventoryTickModifierHook, AttributesModifierHook {
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "clustering_armor");
    public boolean isArmor(EquipmentSlot slot)
    {
        return slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES, ModifierHooks.INVENTORY_TICK);
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (isArmor(slot)) {
            float percent = tool.getPersistentData().getFloat(KEY);
            if (percent > 0.45) {
                consumer.accept(Attributes.ARMOR.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_clustering"), (percent - 0.45) * 0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
            else
            {
                consumer.accept(Attributes.ARMOR_TOUGHNESS.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_clustering_toughness"), (0.45 - percent) * 0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
        }
    }
    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        ModDataNBT persistentData = tool.getPersistentData();
        if (!world.isClientSide) {
            persistentData.putFloat(KEY, getPercentage(((Player)holder).getInventory()));
        }
    }
    public float getPercentage(Inventory i) {
        int sizeInv = i.getContainerSize();
        float current = 0;
        float sum = 0;
        while (sizeInv-- > 0) {
            ItemStack stackInv = i.getItem(sizeInv);
            if (stackInv.isEmpty() || !stackInv.is(TinkerTags.Items.MODIFIABLE)) continue;
            ToolStack toolInv = ToolStack.from(stackInv);
            current += toolInv.getCurrentDurability();
            sum += toolInv.getStats().get(ToolStats.DURABILITY);
        }
        return current / sum;
    }
}
