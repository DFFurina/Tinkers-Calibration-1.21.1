package com.james.tinkerscalibration.modifiers;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;

public class ChimeriteModifier extends Modifier implements ProjectileHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT);
    }
    /*@Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        Iterator<ItemStack> iterator = generatedLoot.iterator();
        float chance = level * 0.20f;
        while (iterator.hasNext()) {
            ItemStack stack = iterator.next();
            if (stack.is(Tags.Items.ORES)) {
                if (chance >= 1.0f) {
                    generatedLoot.add(new ItemStack(ItemInit.CHIMERITE_GEM.get()));
                } else {
                    for (int i = 0; i < stack.getCount(); i++) {
                        if (RANDOM.nextFloat() < chance) {
                            generatedLoot.add(new ItemStack(ItemInit.CHIMERITE_GEM.get()));
                        }
                    }
                }
            }
        }
        
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        Entity targetEntity = hit.getEntity();
        if (!targetEntity.isAlive() && attacker != null && target.getKillCredit() == attacker) {
            float distance = targetEntity.distanceTo(attacker);
            float chance = distance / 10 * modifier.getLevel();
            if (RANDOM.nextFloat() <= chance) {
                Item gem = ItemInit.CHIMERITE_GEM.get();
                targetEntity.spawnAtLocation(gem);
            }
        }
        return false;
    }

     */
}
