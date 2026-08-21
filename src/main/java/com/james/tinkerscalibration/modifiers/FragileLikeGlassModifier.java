package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.contents.TinkersCalibrationDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class FragileLikeGlassModifier extends Modifier implements ToolDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOL_DAMAGE);
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, LivingEntity holder) {
        if(holder != null) {
            Level world = holder.getCommandSenderWorld();
            float chance = 0.15f * modifier.getLevel();
            if (holder instanceof Player player) {
                if (RANDOM.nextFloat() < chance) {
                    ToolDamageUtil.directDamage(tool, 5 * modifier.getLevel(), player, player.getUseItem());
                    player.level().broadcastEntityEvent(player, (byte) 48);
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
                    holder.hurt(holder.damageSources().magic(), 2 * modifier.getLevel());
                    holder.addEffect(new MobEffectInstance((net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect>)(net.minecraft.core.Holder<?>)TinkerModifiers.bleeding.getHolder(), 40));
                }
            }
        }
        return amount;
    }
}