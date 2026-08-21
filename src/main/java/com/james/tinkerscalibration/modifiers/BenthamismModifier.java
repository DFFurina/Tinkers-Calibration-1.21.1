package com.james.tinkerscalibration.modifiers;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.helper.ModifierLootingHandler;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Iterator;
import java.util.List;

public class BenthamismModifier extends Modifier implements ProcessLootModifierHook {
    private static final TinkerDataCapability.TinkerDataKey<Integer> BENTHAMISM = TConstruct.createKey("benthamism");

    public BenthamismModifier() {
        NeoForge.EVENT_BUS.addListener(this::onExperienceDrop);
        NeoForge.EVENT_BUS.addListener(this::onEntityKilled);
        NeoForge.EVENT_BUS.addListener(this::beforeBlockBreak);
    }
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT);
    }
    private static int boost(int original, int level) {
        float modifier = 1 + RANDOM.nextFloat() * level;
        return (int) (original * modifier);
    }

    private void beforeBlockBreak(BlockEvent.BreakEvent event) {
        // BlockEvent.BreakEvent no longer has experience methods in NeoForge 1.21.1
        // Experience drop modification is no longer supported through this event
    }

    private void onEntityKilled(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        if (source != null && source.getDirectEntity() instanceof Projectile projectile) {
            ModifierNBT modifiers = EntityModifierCapability.getOrEmpty(projectile);
            // it is very unlikely that we fire an arrow on a bow with no modifiers, if that ever happens though we will not be able to identify its our arrow
            if (!modifiers.isEmpty()) {
                TinkerDataCapability.getOptional(event.getEntity()).ifPresent(data -> data.put(BENTHAMISM, modifiers.getLevel(this.getId())));
            }
        }
    }

    private void onExperienceDrop(LivingExperienceDropEvent event) {
        int benthamism = TinkerDataCapability.getOptional(event.getEntity()).map(data -> data.get(BENTHAMISM)).orElse(-1);
        if (benthamism > 0) {
            event.setDroppedExperience(boost(event.getDroppedExperience(), benthamism));
        } else if (benthamism != 0) {
            Player player = event.getAttackingPlayer();
            if (player != null) {
                int level = 0;
                ToolStack tool = getHeldTool(player, ModifierLootingHandler.getLootingSlot(player));
                if (tool != null) level = tool.getModifierLevel(this);
                if (level > 0) {
                    event.setDroppedExperience(boost(event.getDroppedExperience(), level));
                }
            }
        }
    }

    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        Iterator<ItemStack> iterator = generatedLoot.iterator();
        int level = modifier.getLevel();
        while (iterator.hasNext()) {
            if (RANDOM.nextFloat() <= 0.2f * level) {
                ItemStack stack = iterator.next();
                stack.getItem();
                iterator.remove();
            }
        }
    }
}
