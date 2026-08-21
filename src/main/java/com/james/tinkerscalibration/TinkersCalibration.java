package com.james.tinkerscalibration;

import com.james.tinkerscalibration.contents.*;
import com.james.tinkerscalibration.event.SpaghettiTooltipEvent;
import com.james.tinkerscalibration.hud.RangedDrawHud;
import com.james.tinkerscalibration.library.TinkersCalibrationLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(TinkersCalibration.MODID)
public class TinkersCalibration {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "tinkerscalibration";

    // Store modEventBus for deferred registration (e.g. Twilight Forest integration)
    private static IEventBus modEventBus;

    public TinkersCalibration(IEventBus modEventBus) {
        TinkersCalibration.modEventBus = modEventBus;

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);

        // Register blocks, items, fluids
        TinkersCalibrationFluids.FLUIDS.register(modEventBus);
        TinkersCalibrationBlocks.BLOCKS.register(modEventBus);
        TinkersCalibrationItems.ITEMS.register(modEventBus);
        TinkersCalibrationItems.CREATIVE_TABS.register(modEventBus);

        // Register modifiers
        Utils.MODIFIERS.register(modEventBus);

        // Register recipe serializers
        Utils.RECIPE_SERIALIZERS.register(modEventBus);

        // Register mob effects
        Utils.MOB_EFFECTS.register(modEventBus);

        // Register armor modifiers
        TinkersCalibrationArmorModifiers.Init(modEventBus);

        // Initialize world gen registers
        TinkersCalibrationModule.initRegisters(modEventBus);

        // Check for Tinkers' Thinking
        if (ModList.get().isLoaded("tinkers_thinking")) {
            TinkersCalibrationLootModifiers.init(modEventBus);
            LOGGER.info("Found Tinkers' Thinking, spaghetti initializing...");
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        boolean pe = ModList.get().isLoaded("projecte");
        if (pe) {
            LOGGER.info("Found ProjectE, integration initializing...");
        }

        boolean tw = ModList.get().isLoaded("twilightforest");
        if (tw) {
            TinkersCalibrationArmorModifiers.InitT(modEventBus);
            LOGGER.info("Found Twilight Forest, armor integration initializing...");
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.register(new RangedDrawHud());
        NeoForge.EVENT_BUS.register(SpaghettiTooltipEvent.class);
    }
}
