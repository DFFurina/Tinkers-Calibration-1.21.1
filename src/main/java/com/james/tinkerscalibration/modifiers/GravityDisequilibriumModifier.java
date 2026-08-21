package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.Utils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class GravityDisequilibriumModifier extends Modifier implements BlockInteractionModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.BLOCK_INTERACT);
    }

    @Override
    public InteractionResult afterBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        Player entity = context.getPlayer();
        if (source == InteractionSource.RIGHT_CLICK && !tool.isBroken() && entity != null) {
            int level = modifier.getLevel();
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            double range = 10 + level * 5;
            List<Mob> targets = entity.getCommandSenderWorld().getEntitiesOfClass(Mob.class, new AABB(x - range, y - range, z - range, x + range, y + range, z + range));
            for (Mob target : targets) {
                target.addEffect(new MobEffectInstance((net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect>)(net.minecraft.core.Holder<?>)Utils.disequilibrium.getHolder(), 150 * level, level + 1));
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 150 * level, level * 2));
                if(!target.canBeAffected(new MobEffectInstance((net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect>)(net.minecraft.core.Holder<?>)Utils.disequilibrium.getHolder())))
                {
                    Vec3 motion = target.getDeltaMovement();
                    if (!target.onGround()) {
                        target.setDeltaMovement(motion.x,
                                motion.y - 0.8f * level,
                                motion.z);
                    }
                }
            }
            ToolDamageUtil.damageAnimated(tool, 3 * targets.size(), entity);
            entity.getCooldowns().addCooldown(tool.getItem(), 100 + 20 * level);
            return InteractionResult.sidedSuccess(entity.getCommandSenderWorld().isClientSide);
        }
        return InteractionResult.PASS;
    }

}
