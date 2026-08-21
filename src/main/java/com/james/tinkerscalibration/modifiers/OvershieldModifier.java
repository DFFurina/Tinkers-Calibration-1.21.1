package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.TinkersCalibration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.DurabilityShieldModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.modifiers.slotless.OverslimeModifier;

import javax.annotation.Nonnull;

public class OvershieldModifier extends DurabilityShieldModifier implements InventoryTickModifierHook, DurabilityDisplayModifierHook, ModifyDamageModifierHook {
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "overshield_cooldown");
    @Override
    public int getPriority() {
        return 1000;
    }
    public boolean fireImmune(Player player)
    {
        return player.fireImmune() || player.hasEffect(MobEffects.FIRE_RESISTANCE);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK, ModifierHooks.DURABILITY_DISPLAY, ModifierHooks.MODIFY_DAMAGE);
    }

    public int getShieldCurrent(IToolStackView tool) {
        return getShield(tool);
    }
    @Override
    public void onInventoryTick(@Nonnull IToolStackView tool, ModifierEntry modifier, @Nonnull Level world, @Nonnull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide && holder.tickCount % 10 == 0 && holder instanceof Player player) {
            OverslimeModifier overslime = TinkerModifiers.overslime.get();
            int level = modifier.getLevel();
            int current = overslime.getShield(tool);
            if (current > 0 && getShield(tool) < getShieldCapacity(tool, modifier) && !player.getCooldowns().isOnCooldown(tool.getItem())) {
                addShield(tool, modifier, 1);
                overslime.addOverslime(tool, modifier, -1);
            }
        }
    }

    @Nullable
    @Override
    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        return getShield(tool) > 0;
    }


    @Override
    public int getShieldCapacity(IToolStackView tool, ModifierEntry modifier) {
        return 40 + 15 * modifier.getLevel();
    }

    @Override
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        int level = modifier.getLevel();
        if (getShield(tool) > 0.5f * getShieldCapacity(tool, modifier)) {
            return 0xadff2f;
        }
        else if (getShield(tool) > 0.25f * getShieldCapacity(tool, modifier)) {
            return 0xffdd00;
        }
        else if (getShield(tool) > 0) {
            return 0xff0000;
        }
        return -1;
    }

    @Override
    public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount, boolean b) {
        if(context.getEntity() instanceof Player player && !player.isCreative()) {
            int current = getShield(tool);
            int level = modifier.getLevel();
            int value = Math.round(amount);
            if (current >= value && !source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
                if (amount <= 40) {
                    if(player.invulnerableTime == 0 || source.is(DamageTypes.ON_FIRE) && !fireImmune(player)) {
                        addShield(tool, modifier, -value);
                        player.invulnerableTime += 10;
                    }
                    return 0;
                } else {
                    addShield(tool, modifier, -getShieldCapacity(tool, modifier));
                    player.getCooldowns().addCooldown(tool.getItem(), 100);
                    return amount - 40;
                }
            }
            addShield(tool, modifier, -getShieldCapacity(tool, modifier));
            player.getCooldowns().addCooldown(tool.getItem(), 100);
            return amount - current;
        }
        return amount;
    }
}
