package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import javax.annotation.Nullable;

public class InfiltratingModifier extends Modifier implements ProjectileHitModifierHook {
    /*@Override
    public float getEntityDamage(IToolStackView tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        if (damage >= 2) return damage - 2;
        else return 0;
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT);
    }
    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();
        LivingEntity holder = context.getAttacker();
        if (target != null) {
            for (ItemStack stackEquipment : target.getArmorSlots()) {
                Item item = stackEquipment.getItem();
                if (item instanceof ModifiableItem) {
                    IToolStackView infoStack = ToolStack.from(stackEquipment);
                    ToolDamageUtil.damage(infoStack, 10, holder, stackEquipment);
                }
                if (item.isDamageable(stackEquipment)) {
                    item.damageItem(stackEquipment, 10, holder, null);
                }
            }
        }
        return 0;
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target){
        if (target != null) {
            for (ItemStack stackEquipment : target.getArmorSlots()) {
                Item item = stackEquipment.getItem();
                if (item instanceof ModifiableItem) {
                    IToolStackView infoStack = ToolStack.from(stackEquipment);
                    ToolDamageUtil.damage(infoStack, 10, attacker, stackEquipment);
                }
                if (item.isDamageable(stackEquipment)) {
                    item.damageItem(stackEquipment, 10, attacker, null);
                }
            }
        }
        return false;
    }

     */
}
