package com.james.tinkerscalibration.hud;

import com.james.tinkerscalibration.TinkersCalibration;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook.KEY_DRAWTIME;

public class RangedDrawHud {
    private static final ResourceLocation NO_CHARGE = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/no.png");
    private static final ResourceLocation CHARGE_08 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/-8.png");
    private static final ResourceLocation CHARGE_07 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/-7.png");
    private static final ResourceLocation CHARGE_06 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/-6.png");
    private static final ResourceLocation CHARGE_05 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/-5.png");
    private static final ResourceLocation CHARGE_04 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/-4.png");
    private static final ResourceLocation CHARGE_03 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/-3.png");
    private static final ResourceLocation CHARGE_02 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/-2.png");
    private static final ResourceLocation CHARGE_01 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/-1.png");
    private static final ResourceLocation CHARGE_0 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/0.png");
    private static final ResourceLocation CHARGE_1 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/1.png");
    private static final ResourceLocation CHARGE_2 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/2.png");
    private static final ResourceLocation CHARGE_3 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/3.png");
    private static final ResourceLocation CHARGE_4 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/4.png");
    private static final ResourceLocation CHARGE_5 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/5.png");
    private static final ResourceLocation CHARGE_6 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/6.png");
    private static final ResourceLocation CHARGE_7 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/7.png");
    private static final ResourceLocation CHARGE_8 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/8.png");
    private static final ResourceLocation CHARGE_9 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/9.png");
    private static final ResourceLocation CHARGE_10 = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "textures/charge/10.png");

    @SubscribeEvent(priority = EventPriority.LOW)
    public void renderDraw(RenderGuiLayerEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (!event.isCanceled() && event.getName().equals(VanillaGuiLayers.CROSSHAIR)) {
            if (!mc.options.hideGui) {
                Entity renderViewEnity = mc.getCameraEntity();
                if (!(renderViewEnity instanceof Player player)) {
                    return;
                }
                int width = mc.getWindow().getGuiScaledWidth();
                int height = mc.getWindow().getGuiScaledHeight();
                int x = width / 2;
                int y = height / 2;
                GuiGraphics guiGraphics = event.getGuiGraphics();
                RenderSystem.setShaderTexture(0, NO_CHARGE);
                ItemStack stack = null;
                if (player.getMainHandItem().getItem() instanceof IModifiable && ToolStack.from(player.getMainHandItem()).hasTag(TinkerTags.Items.RANGED) && !ToolStack.from(player.getMainHandItem()).isBroken()) {
                    stack = player.getMainHandItem();
                } else if (player.getOffhandItem().getItem() instanceof IModifiable && ToolStack.from(player.getOffhandItem()).hasTag(TinkerTags.Items.RANGED) && !ToolStack.from(player.getOffhandItem()).isBroken()) {
                    stack = player.getOffhandItem();
                }
                if (Minecraft.getInstance().gameMode != null && stack != null && Minecraft.getInstance().gameMode.getPlayerMode() != GameType.SPECTATOR) {
                    ToolStack tool = ToolStack.from(stack);
                    if (tool.hasTag(TinkerTags.Items.RANGED) && !tool.isBroken()) {
                        ModDataNBT persistentData = tool.getPersistentData();
                        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                        RenderSystem.enableBlend();
                        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        if (!persistentData.contains(KEY_DRAWTIME, 3)) {
                            guiGraphics.blit(NO_CHARGE, x - 4, height / 2 - 4, 0, 0, 8, 8, 8, 8);
                        } else {
                            // draw time is stored by the bow when drawing starts (ceil(20 / draw speed)), use it instead of recomputing
                            double total = persistentData.getInt(KEY_DRAWTIME);
                            int eta = (int) Math.max(0, Math.ceil(total - player.getTicksUsingItem()));
                            float charge = 1 - (float) (eta / total);
                            charge = (charge * charge + charge * 2.0F) / 3.0F;

                            if (charge >= 1) {
                                RenderSystem.setShaderTexture(0, NO_CHARGE);
                                guiGraphics.blit(NO_CHARGE, x - 4, height / 2 - 4, 0, 0, 8, 8, 8, 8);
                            } else if (charge >= 0.9) {
                                RenderSystem.setShaderTexture(0, CHARGE_10);
                                guiGraphics.blit(CHARGE_10, x - 5, height / 2 - 5, 0, 0, 10, 10, 10, 10);
                            } else if (charge >= 0.85) {
                                RenderSystem.setShaderTexture(0, CHARGE_9);
                                guiGraphics.blit(CHARGE_9, x - 6, height / 2 - 6, 0, 0, 12, 12, 12, 12);
                            } else if (charge >= 0.8) {
                                RenderSystem.setShaderTexture(0, CHARGE_8);
                                guiGraphics.blit(CHARGE_8, x - 7, height / 2 - 7, 0, 0, 14, 14, 14, 14);
                            } else if (charge >= 0.75) {
                                RenderSystem.setShaderTexture(0, CHARGE_7);
                                guiGraphics.blit(CHARGE_7, x - 8, height / 2 - 8, 0, 0, 16, 16, 16, 16);
                            } else if (charge >= 0.7) {
                                RenderSystem.setShaderTexture(0, CHARGE_6);
                                guiGraphics.blit(CHARGE_6, x - 9, height / 2 - 9, 0, 0, 18, 18, 18, 18);
                            } else if (charge >= 0.65) {
                                RenderSystem.setShaderTexture(0, CHARGE_5);
                                guiGraphics.blit(CHARGE_5, x - 10, height / 2 - 10, 0, 0, 20, 20, 20, 20);
                            } else if (charge >= 0.6) {
                                RenderSystem.setShaderTexture(0, CHARGE_4);
                                guiGraphics.blit(CHARGE_4, x - 11, height / 2 - 11, 0, 0, 22, 22, 22, 22);
                            } else if (charge >= 0.55) {
                                RenderSystem.setShaderTexture(0, CHARGE_3);
                                guiGraphics.blit(CHARGE_3, x - 12, height / 2 - 12, 0, 0, 24, 24, 24, 24);
                            } else if (charge >= 0.5) {
                                RenderSystem.setShaderTexture(0, CHARGE_2);
                                guiGraphics.blit(CHARGE_2, x - 13, height / 2 - 13, 0, 0, 26, 26, 26, 26);
                            } else if (charge >= 0.45) {
                                RenderSystem.setShaderTexture(0, CHARGE_1);
                                guiGraphics.blit(CHARGE_1, x - 14, height / 2 - 14, 0, 0, 28, 28, 28, 28);
                            } else if (charge >= 0.4) {
                                RenderSystem.setShaderTexture(0, CHARGE_0);
                                guiGraphics.blit(CHARGE_0, x - 15, height / 2 - 15, 0, 0, 30, 30, 30, 30);
                            } else if (charge >= 0.35) {
                                RenderSystem.setShaderTexture(0, CHARGE_01);
                                guiGraphics.blit(CHARGE_01, x - 16, height / 2 - 16, 0, 0, 32, 32, 32, 32);
                            } else if (charge >= 0.3) {
                                RenderSystem.setShaderTexture(0, CHARGE_02);
                                guiGraphics.blit(CHARGE_02, x - 17, height / 2 - 17, 0, 0, 34, 34, 34, 34);
                            } else if (charge >= 0.25) {
                                RenderSystem.setShaderTexture(0, CHARGE_03);
                                guiGraphics.blit(CHARGE_03, x - 18, height / 2 - 18, 0, 0, 36, 36, 36, 36);
                            } else if (charge >= 0.2) {
                                RenderSystem.setShaderTexture(0, CHARGE_04);
                                guiGraphics.blit(CHARGE_04, x - 19, height / 2 - 19, 0, 0, 38, 38, 38, 38);
                            } else if (charge >= 0.15) {
                                RenderSystem.setShaderTexture(0, CHARGE_05);
                                guiGraphics.blit(CHARGE_05, x - 20, height / 2 - 20, 0, 0, 40, 40, 40, 40);
                            } else if (charge >= 0.1) {
                                RenderSystem.setShaderTexture(0, CHARGE_06);
                                guiGraphics.blit(CHARGE_06, x - 21, height / 2 - 21, 0, 0, 42, 42, 42, 42);
                            } else if (charge >= 0.05) {
                                RenderSystem.setShaderTexture(0, CHARGE_07);
                                guiGraphics.blit(CHARGE_07, x - 22, height / 2 - 22, 0, 0, 44, 44, 44, 44);
                            } else {
                                RenderSystem.setShaderTexture(0, CHARGE_08);
                                guiGraphics.blit(CHARGE_08, x - 23, height / 2 - 23, 0, 0, 46, 46, 46, 46);
                            }
                        }
                        event.setCanceled(true);
                        GlStateManager._enableCull();
                        GlStateManager._depthMask(true);
                        // restore the default gui render state, otherwise later gui layers (e.g. the hotbar) render washed out
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();
                        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                }
            }
        }
    }
}
