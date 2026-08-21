package com.james.tinkerscalibration.modifiers;

import com.james.tinkerscalibration.TinkersCalibration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.RawDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.utils.RestrictedCompoundTag;
import slimeknights.tconstruct.library.utils.Util;

import javax.annotation.Nullable;
import java.util.List;

public class SpaghettiModifier extends NoLevelsModifier implements GeneralInteractionModifierHook, RawDataModifierHook, TooltipModifierHook, DurabilityDisplayModifierHook {
    private static final Lazy<ItemStack> BREAD = Lazy.of(() -> new ItemStack(Items.BREAD));
    private static final Lazy<ItemStack> STEAK = Lazy.of(() -> new ItemStack(Items.COOKED_BEEF));
    private static final Lazy<ItemStack> BEETROOT = Lazy.of(() -> new ItemStack(Items.BEETROOT));
    private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "spaghetti");
    private static final Component SPAGHETTI = TConstruct.makeTranslation("modifier", "spaghetti");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.GENERAL_INTERACT, ModifierHooks.RAW_DATA, ModifierHooks.TOOLTIP);
        hookBuilder.addHook(this, ModifierHooks.DURABILITY_DISPLAY);
    }

    @Override
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
        if (source == InteractionSource.RIGHT_CLICK && tool.getPersistentData().getInt(KEY) > 0 && player.canEat(false)) {
            GeneralInteractionModifierHook.startUsing(tool, modifier.getId(), player, hand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    private void eat(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        int level = modifier.getLevel();
        if (level > 0 && entity instanceof Player player) {
            Level world = entity.getCommandSenderWorld();
            ModifierUtil.foodConsumer.onConsume(player, BREAD.get(), level, 0.6f);
            switch (level) {
                case 1:
                    player.getFoodData().eat(3, 0.4F);
                    break;
                case 2:
                    player.getFoodData().eat(3, 0.6F);
                    ModifierUtil.foodConsumer.onConsume(player, BEETROOT.get(), level, 0.6f);
                    break;
                case 3:
                    player.getFoodData().eat(5, 0.6F);
                    ModifierUtil.foodConsumer.onConsume(player, BEETROOT.get(), level, 0.6f);
                    ModifierUtil.foodConsumer.onConsume(player, STEAK.get(), level, 0.f);
                    break;
            }
            if(!player.isCreative())
                tool.getPersistentData().putInt(KEY, tool.getPersistentData().getInt(KEY) - 1);
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_BURP, SoundSource.NEUTRAL, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);

        }
    }

    @Override
    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        eat(tool, modifier, entity);
    }

    public int getUses(IToolStackView tool)
    {
        return tool.getPersistentData().getInt(KEY);
    }

    @Override
    public @NotNull UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 16;
    }

    @Override
    public void addRawData(IToolStackView iToolStackView, ModifierEntry modifierEntry, RestrictedCompoundTag restrictedCompoundTag) {
        if(!iToolStackView.getPersistentData().contains(KEY, 3))
            iToolStackView.getPersistentData().putInt(KEY, 100);
    }

    @Override
    public void removeRawData(IToolStackView iToolStackView, Modifier modifier, RestrictedCompoundTag restrictedCompoundTag) {

    }

    @Override
    public boolean shouldDisplay(boolean advanced) {
        return false;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, slimeknights.mantle.client.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        int level = modifier.getLevel();
        if (player == null) {
            tooltip.add(Component.literal("").append(SPAGHETTI).append(TooltipBuilder.formatPartialAmount(tool.getPersistentData().getInt(KEY), 100)));
            tooltip.add(Component.translatable("modifier.tinkerscalibration.spaghetti.food_level").append(Component.literal(Util.COMMA_FORMAT.format((long)level < 3 ? 3 : 5)).withStyle((style) -> style.withColor(-2661276))));
            tooltip.add(Component.translatable("modifier.tinkerscalibration.spaghetti.saturation_level").append(Component.literal(Util.COMMA_FORMAT.format((long)level < 3 ? level * 1.2f + 1.2f: 6)).withStyle(style -> style.withColor(-8871731))));
            if(level > 1) {
                tooltip.add(Component.translatable("modifier.tinkerscalibration.spaghetti.sauce").withStyle(style -> style.withColor(-8042548)));
                if(level > 2)
                    tooltip.add(Component.translatable("modifier.tinkerscalibration.spaghetti.meat").withStyle(style -> style.withColor(-8042548)));
            }
        }
    }

    @Nullable
    @Override
    public Boolean showDurabilityBar(IToolStackView iToolStackView, ModifierEntry modifierEntry) {
        return getUses(iToolStackView) < 100 ? true : null;
    }

    @Override
    public int getDurabilityRGB(IToolStackView iToolStackView, ModifierEntry modifierEntry) {
        float f = Math.max(0.0F, (float) getUses(iToolStackView) / 100);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        int uses = getUses(tool);
        return uses > 0 ? DurabilityDisplayModifierHook.getWidthFor(uses, 100) : 0;
    }

}
