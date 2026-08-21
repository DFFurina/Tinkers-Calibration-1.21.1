package com.james.tinkerscalibration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;

public abstract class TinkersCalibrationModule {
    protected static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String name) {
        return ResourceKey.create(registry, ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, name));
    }

    protected static final BlockDeferredRegisterExtension BLOCKS = new BlockDeferredRegisterExtension(TinkersCalibration.MODID);

    public static void initRegisters(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
