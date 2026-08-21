package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Iterator;
import java.util.List;

public class GluttonicModifier extends Modifier {
    public float getEntityDamage(IToolStackView tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        return damage + 1.5f;
    }
/*
    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        Player player = context.getPlayerAttacker();
        LivingEntity target = context.getLivingTarget();
        if (player != null && !player.getCommandSenderWorld().isClientSide && target != null) {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 125, 0));

            if (target.getHealth() < 0) {
                FoodData fs = player.getFoodData();
                fs.setFoodLevel(fs.getFoodLevel() + 2);
            }
        }

        return 0;
    }

    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        if (!context.hasParam(LootContextParams.DAMAGE_SOURCE)) {
            
        }
        Iterator<ItemStack> iterator = generatedLoot.iterator();
        while (iterator.hasNext()) {
            ItemStack stack = iterator.next();
            // if the item is a stone, num time
            if (stack.getItem().isEdible()) {
                iterator.remove();
            }
        }
        
    }

 */
}
