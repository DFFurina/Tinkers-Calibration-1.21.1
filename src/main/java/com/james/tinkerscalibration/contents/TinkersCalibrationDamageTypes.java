package com.james.tinkerscalibration.contents;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.fluid.entity.DamageFluidEffect;

public class TinkersCalibrationDamageTypes {

    public static final ResourceKey<DamageType> FIBERGLASS_PRICK = create("fiberglass_prick");

    private TinkersCalibrationDamageTypes() {
    }

    private static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("tinkerscalibration", name));
    }

    public static DamageSource source(RegistryAccess access, ResourceKey<DamageType> type, @Nullable Entity direct, @Nullable Entity causing) {
        return new DamageSource(access.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), direct, causing);
    }

    public static DamageSource source(RegistryAccess access, ResourceKey<DamageType> type, @Nullable Entity entity) {
        return source(access, type, entity, entity);
    }

    public static DamageSource source(RegistryAccess access, ResourceKey<DamageType> type) {
        return source(access, type, (Entity)null, (Entity)null);
    }
}
