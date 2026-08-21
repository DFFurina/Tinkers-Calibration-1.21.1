package com.james.tinkerscalibration.modifiers.armor;

import com.james.tinkerscalibration.TinkersCalibration;
import com.james.tinkerscalibration.TinkersCalibrationArmorModifiers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import twilightforest.init.TFDimension;

import java.util.UUID;
import java.util.function.BiConsumer;

public class ArmorTwilitModifier extends Modifier implements AttributesModifierHook {
    private static final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "armor_twilit");
    private static final TinkerDataCapability.TinkerDataKey<Integer> TWILIT = TConstruct.createKey("twilit_armor");

    public ArmorTwilitModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorTwilitModifier::onUpdateApply);
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (tool.getPersistentData().getFloat(KEY) == 1) {
            consumer.accept(Attributes.ARMOR_TOUGHNESS.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_twilit_toughness"), 2, AttributeModifier.Operation.ADD_VALUE));
        }
        else
        {
            consumer.accept(Attributes.ARMOR.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_twilit_armor"), 2, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES);
        hookBuilder.addModule(new ArmorLevelModule(TWILIT, false, null));
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide && living.isAlive()) {
                    TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                        int level = holder.get(TWILIT, 0);
                        if (level > 0) {
                            addPersistentData(context, living);
                        }
                    });
                }
            }
        }
    }
    public static void addPersistentData(EquipmentContext context, LivingEntity living)
    {
        ModDataNBT persistentData;
        Level world = living.getCommandSenderWorld();
        if(context.getToolInSlot(EquipmentSlot.LEGS) instanceof ToolStack tool)
        {
            persistentData = tool.getPersistentData();
            int level = tool.getModifierLevel(TinkersCalibrationArmorModifiers.igneous.get());
            if(level > 0) {
                if (world.dimension().equals(TFDimension.DIMENSION_KEY)) {
                    if (persistentData.getFloat(KEY) == 0)
                        persistentData.putFloat(KEY, 1);
                } else {
                    persistentData.remove(KEY);
                }
            }
        }
        if(context.getToolInSlot(EquipmentSlot.HEAD) instanceof ToolStack tool)
        {
            persistentData = tool.getPersistentData();
            int level = tool.getModifierLevel(TinkersCalibrationArmorModifiers.igneous.get());
            if(level > 0) {
                if (world.dimension().equals(TFDimension.DIMENSION_KEY)) {
                    if (persistentData.getFloat(KEY) == 0)
                        persistentData.putFloat(KEY, 1);
                } else {
                    persistentData.remove(KEY);
                }
            }
        }
        if(context.getToolInSlot(EquipmentSlot.CHEST) instanceof ToolStack tool)
        {
            persistentData = tool.getPersistentData();
            int level = tool.getModifierLevel(TinkersCalibrationArmorModifiers.igneous.get());
            if(level > 0) {
                if (world.dimension().equals(TFDimension.DIMENSION_KEY)) {
                    if (persistentData.getFloat(KEY) == 0)
                        persistentData.putFloat(KEY, 1);
                } else {
                    persistentData.remove(KEY);
                }
            }
        }
        if(context.getToolInSlot(EquipmentSlot.FEET) instanceof ToolStack tool)
        {
            persistentData = tool.getPersistentData();
            int level = tool.getModifierLevel(TinkersCalibrationArmorModifiers.igneous.get());
            if(level > 0) {
                if (world.dimension().equals(TFDimension.DIMENSION_KEY)) {
                    if (persistentData.getFloat(KEY) == 0)
                        persistentData.putFloat(KEY, 1);
                } else {
                    persistentData.remove(KEY);
                }
            }
        }
    }
}
