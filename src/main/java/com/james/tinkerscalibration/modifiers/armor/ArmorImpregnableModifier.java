package com.james.tinkerscalibration.modifiers.armor;


import com.james.tinkerscalibration.Utils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.EventPriority;
import slimeknights.mantle.registration.RegistryObject;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;

public class ArmorImpregnableModifier extends Modifier implements ToolDamageModifierHook {
    private static final TinkerDataCapability.TinkerDataKey<Integer> IMPREGNABLE = TConstruct.createKey("impregnable");

    public ArmorImpregnableModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, ArmorImpregnableModifier::onHurt);
    }

    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOL_DAMAGE);
        hookBuilder.addModule(new ArmorLevelModule(IMPREGNABLE, false, null));
    }
    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
            int level = holder.get(IMPREGNABLE, 0);
            if (level > 0 && attacker != null) {
                if (event.getOriginalDamage() != 0) {
                    int effectLevel = Math.min(7, Utils.impregnableEffect.get().getLevel(living) + 1);
                    Utils.impregnableEffect.get().apply(living, 5 * 20, effectLevel, true);
                }
            }
        });
    }

    private static float getBonus(@Nullable LivingEntity living, RegistryObject<? extends TinkerEffect> effect, ModifierEntry modifier, float scale) {
        // without an entity there is no effect to read (tool damage without a holder)
        if (living == null) {
            return 0;
        }
        int effectLevel = effect.get().getLevel(living) + 1;
        return modifier.getLevel() * effectLevel / scale;
    }
    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        float bonus = getBonus(holder, Utils.impregnableEffect, modifier, 6);
        int off = 0;
        for (int i = 0; i < amount; i++) {
            if (RANDOM.nextFloat() >= 1 / bonus) {
                off++;
            }
        }
        return amount - off;
    }
}
