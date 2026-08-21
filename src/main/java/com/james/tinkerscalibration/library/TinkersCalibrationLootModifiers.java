
package com.james.tinkerscalibration.library;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import slimeknights.mantle.registration.RegistryObject;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class TinkersCalibrationLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> REGISTRY;
    public static final RegistryObject<MapCodec<? extends IGlobalLootModifier>> ADD_LOOT_TABLE;

    public TinkersCalibrationLootModifiers() {
    }

    public static void init(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }

    static {
        REGISTRY = DeferredRegister.create(Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "tinkerscalibration");
        ADD_LOOT_TABLE = RegistryObject.of(REGISTRY.register("add_loot_table", LootTableModifiers.CODEC));
    }
}
