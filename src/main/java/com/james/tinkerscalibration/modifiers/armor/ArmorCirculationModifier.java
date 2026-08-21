package com.james.tinkerscalibration.modifiers.armor;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.DamageDealtModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ArmorCirculationModifier extends Modifier implements OnAttackedModifierHook, DamageDealtModifierHook {
    public boolean isOverworld(Level level) {
        return level.dimension().equals(Level.OVERWORLD);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.DAMAGE_DEALT, ModifierHooks.ON_ATTACKED);
    }
    @Override
    public void onDamageDealt(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, LivingEntity target, DamageSource source, float amount, boolean isDirectDamage) {
        if (isDirectDamage) {
            LivingEntity attacker = context.getEntity();
            int level = modifier.getLevel();
            if (isOverworld(attacker.getCommandSenderWorld()) && RANDOM.nextFloat() <= 0.2f * level)
            {
                attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60 * level, Math.min(level - 1, 2)));
            }
            else if (!isOverworld(attacker.getCommandSenderWorld()) && RANDOM.nextFloat() <= 0.2f * level)
            {
                attacker.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60 * level, Math.min(level - 1, 2)));
            }
        }
    }
    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        Entity entity = source.getEntity();
        int level = modifier.getLevel();
        if (isDirectDamage && entity instanceof LivingEntity attacker)
        {
            if (isOverworld(attacker.getCommandSenderWorld()) && RANDOM.nextFloat() <= 0.2f * level)
            {
                attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60 * level, Math.min(level - 1, 2)));
            }
            else if (!isOverworld(attacker.getCommandSenderWorld()) && RANDOM.nextFloat() <= 0.2f * level)
            {
                attacker.setRemainingFireTicks(3 * level * 20);
            }
        }
    }
}
