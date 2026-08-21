package com.james.tinkerscalibration.modifiers.armor;

import com.james.tinkerscalibration.TinkersCalibrationArmorModifiers;
import com.james.tinkerscalibration.TinkersCalibration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.behavior.ReduceToolDamageModule;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.UUID;
import java.util.function.BiConsumer;

public class ArmorIgneousModifier extends Modifier implements ToolDamageModifierHook, AttributesModifierHook {
    private static final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "igneous_mod");

    private static final TinkerDataCapability.TinkerDataKey<Integer> IGNEOUS = TConstruct.createKey("igneous_armor");

    public ArmorIgneousModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorIgneousModifier::onUpdateApply);
    }

    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide && living.isAlive()) {
                    TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                        ModDataNBT persistentData;
                        if(context.getToolInSlot(EquipmentSlot.LEGS) instanceof ToolStack tool)
                        {
                            persistentData = tool.getPersistentData();
                            int level = tool.getModifierLevel(TinkersCalibrationArmorModifiers.igneous.get());
                            if (level > 0 && persistentData.getFloat(KEY) <= 5 * level && living.isOnFire() && RANDOM.nextFloat() <= level * 0.6f && living.tickCount % 100 == 0) {
                                persistentData.putFloat(KEY, persistentData.getFloat(KEY) + 0.1f);
                            }
                        }
                        if(context.getToolInSlot(EquipmentSlot.HEAD) instanceof ToolStack tool)
                        {
                            persistentData = tool.getPersistentData();
                            int level = tool.getModifierLevel(TinkersCalibrationArmorModifiers.igneous.get());
                            if (level > 0 && persistentData.getFloat(KEY) <= 5 * level && living.isOnFire() && RANDOM.nextFloat() <= level * 0.6f && living.tickCount % 100 == 0) {
                                persistentData.putFloat(KEY, persistentData.getFloat(KEY) + 0.1f);
                            }
                        }
                        if(context.getToolInSlot(EquipmentSlot.CHEST) instanceof ToolStack tool)
                        {
                            persistentData = tool.getPersistentData();
                            int level = tool.getModifierLevel(TinkersCalibrationArmorModifiers.igneous.get());
                            if (level > 0 && persistentData.getFloat(KEY) <= 5 * level && living.isOnFire() && RANDOM.nextFloat() <= level * 0.6f && living.tickCount % 100 == 0) {
                                persistentData.putFloat(KEY, persistentData.getFloat(KEY) + 0.1f);
                            }
                        }
                        if(context.getToolInSlot(EquipmentSlot.FEET) instanceof ToolStack tool)
                        {
                            persistentData = tool.getPersistentData();
                            int level = tool.getModifierLevel(TinkersCalibrationArmorModifiers.igneous.get());
                            if (level > 0 && persistentData.getFloat(KEY) <= 5 * level && living.isOnFire() && RANDOM.nextFloat() <= level * 0.6f && living.tickCount % 100 == 0) {
                                persistentData.putFloat(KEY, persistentData.getFloat(KEY) + 0.1f);
                            }
                        }
                    });
                }

            }
        }
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.ATTRIBUTES, ModifierHooks.TOOL_DAMAGE);
        hookBuilder.addModule(new ArmorLevelModule(IGNEOUS, false, null));
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @javax.annotation.Nullable LivingEntity holder) {
        ModDataNBT persistentData = tool.getPersistentData();
        if (persistentData.contains(KEY, 5)) {
            float value = persistentData.getFloat(KEY);
            int level = modifier.getLevel();
            if (value >= level / 5f) {
                return ReduceToolDamageModule.reduceDamage(amount, (float) (0.1 * level));
            } else {
                return ReduceToolDamageModule.reduceDamage(amount, value / 10);
            }
        }
        return amount;
    }
    public boolean isArmor(EquipmentSlot slot)
    {
        return slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
    }
    @Override
    public void addAttributes(IToolStackView tool, ModifierEntry modifier, EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> consumer) {
        if (isArmor(slot)) {
            float current = tool.getPersistentData().getFloat(KEY);
            if (current != 0) {
                consumer.accept(Attributes.ARMOR.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_igneous_armor"), current, AttributeModifier.Operation.ADD_VALUE));
                consumer.accept(Attributes.ARMOR_TOUGHNESS.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_igneous_toughness"), current, AttributeModifier.Operation.ADD_VALUE));
                consumer.accept(Attributes.KNOCKBACK_RESISTANCE.value(), new AttributeModifier(ResourceLocation.fromNamespaceAndPath("tinkerscalibration", "armor_igneous_knockback"), current, AttributeModifier.Operation.ADD_VALUE));
            }
        }
    }
}
