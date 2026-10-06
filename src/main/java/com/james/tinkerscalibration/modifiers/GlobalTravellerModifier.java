package com.james.tinkerscalibration.modifiers;

import com.google.common.base.MoreObjects;
import com.james.tinkerscalibration.TinkersCalibration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.Mantle;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.TinkerTools;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GlobalTravellerModifier extends Modifier implements TooltipModifierHook, BlockInteractionModifierHook, ProcessLootModifierHook {
    private final ResourceLocation X = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "global_traveller_x");
    private final ResourceLocation Y = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "global_traveller_y");
    private final ResourceLocation Z = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "global_traveller_z");
    private final ResourceLocation WORLD = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "global_traveller_dimension");
    private static final Component GLOBAL_POS = TConstruct.makeTranslation("modifier", "global_traveller.pos");
    private static final String UNLINK_SUCCEED = Mantle.makeDescriptionId("modifier", "global_traveller.unlink");
    private static final String LINK_SUCCEED = Mantle.makeDescriptionId("modifier", "global_traveller.link");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP, ModifierHooks.BLOCK_INTERACT, ModifierHooks.PROCESS_LOOT);
    }

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public @NotNull InteractionResult afterBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        if (source == InteractionSource.RIGHT_CLICK && tool.getCurrentDurability() >= 10 && context.getPlayer() != null && context.getPlayer().isCrouching() || tool.hasTag(TinkerTags.Items.RANGED) && source == InteractionSource.LEFT_CLICK && tool.getCurrentDurability() >= 10 && context.getPlayer() != null && context.getPlayer().isCrouching() || tool.getItem().toString().equals(TinkerTools.warPick.get().toString()) && source == InteractionSource.LEFT_CLICK && tool.getCurrentDurability() >= 10 && context.getPlayer() != null && context.getPlayer().isCrouching()) {
            Player player = context.getPlayer();
            if (!player.getCommandSenderWorld().isClientSide) {
                Level world = context.getLevel();
                BlockPos pos = context.getClickedPos();
                BlockEntity block = world.getBlockEntity(pos);
                if (block != null && world.getCapability(Capabilities.ItemHandler.BLOCK, pos, null) != null) {
                    ModDataNBT persistentData = tool.getPersistentData();
                    if (persistentData.contains(X, 3) && persistentData.contains(Y, 3) && persistentData.contains(Z, 3) && persistentData.contains(WORLD, 8)) {
                        if (persistentData.getInt(X) == pos.getX() && persistentData.getInt(Y) == pos.getY() && persistentData.getInt(Z) == pos.getZ() && persistentData.getString(WORLD).equals(world.dimension().location().getPath())) {
                            persistentData.remove(X);
                            persistentData.remove(Y);
                            persistentData.remove(Z);
                            persistentData.remove(WORLD);
                            player.displayClientMessage(Component.translatable(UNLINK_SUCCEED, pos.toShortString(), world.dimension().location().getPath()), true);
                        } else {
                            persistentData.putInt(X, pos.getX());
                            persistentData.putInt(Y, pos.getY());
                            persistentData.putInt(Z, pos.getZ());
                            persistentData.putString(WORLD, world.dimension().location().getPath());
                            player.displayClientMessage(Component.translatable(LINK_SUCCEED, pos.toShortString(), world.dimension().location().getPath()), true);
                        }
                    } else {
                        persistentData.putInt(X, pos.getX());
                        persistentData.putInt(Y, pos.getY());
                        persistentData.putInt(Z, pos.getZ());
                        persistentData.putString(WORLD, world.dimension().location().getPath());
                        player.displayClientMessage(Component.translatable(LINK_SUCCEED, pos.toShortString(), world.dimension().location().getPath()), true);
                    }
                    player.getCooldowns().addCooldown(tool.getItem(), 40);
                    ToolDamageUtil.damageAnimated(tool, 5, player);
                    return InteractionResult.sidedSuccess(player.getCommandSenderWorld().isClientSide);
                }
            }

        }
        return InteractionResult.PASS;
    }

    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        ModDataNBT persistentData = tool.getPersistentData();
        if (persistentData.contains(X, 3) && persistentData.contains(Y, 3) && persistentData.contains(Z, 3) && persistentData.contains(WORLD, 8)) {
            BlockPos pos = new BlockPos(persistentData.getInt(X), persistentData.getInt(Y), persistentData.getInt(Z));
            ServerLevel level = context.getLevel().getServer().getLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(persistentData.getString(WORLD))));
            if (level != null) {
                BlockEntity block = level.getBlockEntity(pos);
                if (block != null) {
                    IItemHandler inventory = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
                    Iterator<ItemStack> iterator = generatedLoot.iterator();
                    List<ItemStack> leftover = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ItemStack stack = iterator.next();
                        leftover.add(ItemHandlerHelper.insertItemStacked(inventory, stack, false));
                    }
                    generatedLoot.clear();
                    generatedLoot.addAll(leftover);
                }
            }
        }

    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        ModDataNBT persistentData = tool.getPersistentData();
        if (player != null) {
            if (persistentData.contains(X, 3) && persistentData.contains(Y, 3) && persistentData.contains(Z, 3)) {
                BlockPos pos = new BlockPos(persistentData.getInt(X), persistentData.getInt(Y), persistentData.getInt(Z));
                Level world = player.getCommandSenderWorld();
                tooltip.add(Component.translatable(MoreObjects.toStringHelper("").add("X", pos.getX()).add(" Y", pos.getY()).add(" Z", pos.getZ()).toString()).append(" ").append(persistentData.getString(WORLD)).append(" ").append(GLOBAL_POS).withStyle(style -> style.withColor(TextColor.fromRgb(0xE29AEC))));
                if(world.dimension().location().getPath().equals(persistentData.getString(WORLD))) {
                    BlockEntity block = world.getBlockEntity(pos);
                    if (block != null) {
                        if (world.getCapability(Capabilities.ItemHandler.BLOCK, pos, null) != null) {
                            tooltip.add(Component.translatable("modifier.tinkerscalibration.global.valid").withStyle(style -> style.withColor(TextColor.fromRgb(0xE29AEC))));
                        }
                        else {
                            tooltip.add(Component.translatable("modifier.tinkerscalibration.global.invalid").withStyle(style -> style.withColor(TextColor.fromRgb(0xE29AEC))));
                        }
                    }
                    else {
                        tooltip.add(Component.translatable("modifier.tinkerscalibration.global.invalid").withStyle(style -> style.withColor(TextColor.fromRgb(0xE29AEC))));
                    }
                }
                else {
                    tooltip.add(Component.translatable("modifier.tinkerscalibration.global.different_dimension").withStyle(style -> style.withColor(TextColor.fromRgb(0xE29AEC))));

                }
            }
            else {
                tooltip.add(Component.translatable("modifier.tinkerscalibration.global.none").withStyle(style -> style.withColor(TextColor.fromRgb(0xE29AEC))));
            }
        }
    }
}
