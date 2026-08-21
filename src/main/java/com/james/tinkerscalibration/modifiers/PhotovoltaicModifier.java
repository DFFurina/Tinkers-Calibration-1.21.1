package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class PhotovoltaicModifier extends Modifier implements BlockBreakModifierHook, MeleeHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_BREAK, ModifierHooks.MELEE_HIT);
    }
    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        Player player = context.getPlayer();
        if(player != null) {
            Level world = player.getCommandSenderWorld();
            int level = modifier.getLevel();
            int skylight = world.getBrightness(LightLayer.SKY, player.blockPosition()) - world.getSkyDarken();
            if (skylight > 10 && RANDOM.nextFloat() <= 0.3f * level) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 60 * level, level - 1));
            }
        }
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        Player player = context.getPlayerAttacker();
        if(player != null) {
            Level world = player.getCommandSenderWorld();
            int level = modifier.getLevel();
            int skylight = world.getBrightness(LightLayer.SKY, player.blockPosition()) - world.getSkyDarken();
            if (skylight > 10 && RANDOM.nextFloat() <= 0.3f * level) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60 * level, level - 1));
            }
        }
    }
}
