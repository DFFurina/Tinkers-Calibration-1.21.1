package com.james.tinkerscalibration;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.common.registration.GeodeItemObject;


public class TinkersCalibrationWorldFeatures extends TinkersCalibrationModule{

    protected static final Item.Properties ITEM_PROPS = new Item.Properties();
    public static ResourceKey<BiomeModifier> spawnTitaniumOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "titanium_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredTitaniumOre = key(Registries.CONFIGURED_FEATURE, "titanium_ore");
    public static ResourceKey<PlacedFeature> placedTitaniumOre = key(Registries.PLACED_FEATURE, "titanium_ore");

    public static ResourceKey<BiomeModifier> spawnHothiumOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "hothium_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredHothiumOre = key(Registries.CONFIGURED_FEATURE, "hothium_ore");
    public static ResourceKey<PlacedFeature> placedHothiumOre = key(Registries.PLACED_FEATURE, "hothium_ore");

    public static ResourceKey<BiomeModifier> spawnImmersedSilverOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "immersed_silver_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredImmersedSilverOre = key(Registries.CONFIGURED_FEATURE, "immersed_silver_ore");
    public static ResourceKey<PlacedFeature> placedImmersedSilverOre = key(Registries.PLACED_FEATURE, "immersed_silver_ore");

    public static ResourceKey<BiomeModifier> spawnMagigaOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "magiga_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredMagigaOre = key(Registries.CONFIGURED_FEATURE, "magiga_ore");
    public static ResourceKey<PlacedFeature> placedMagigaOre = key(Registries.PLACED_FEATURE, "magiga_ore");

    public static ResourceKey<BiomeModifier> spawnStellariumOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "stellarium_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredStellariumOre = key(Registries.CONFIGURED_FEATURE, "stellarium_ore");
    public static ResourceKey<PlacedFeature> placedStellariumOre = key(Registries.PLACED_FEATURE, "stellarium_ore");

    public static ResourceKey<BiomeModifier> spawnInertwitheriumOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "inert_witherium_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredInertwitheriumOre = key(Registries.CONFIGURED_FEATURE, "inert_witherium_ore");
    public static ResourceKey<PlacedFeature> placedInertwitheriumOre = key(Registries.PLACED_FEATURE, "inert_witherium_ore");

    public static ResourceKey<BiomeModifier> spawnToniumOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "tonium_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredToniumOre = key(Registries.CONFIGURED_FEATURE, "tonium_ore");
    public static ResourceKey<PlacedFeature> placedToniumOre = key(Registries.PLACED_FEATURE, "tonium_ore");

    public static ResourceKey<BiomeModifier> spawnCorundumOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "corundum_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredCorundumOre = key(Registries.CONFIGURED_FEATURE, "corundum_ore");
    public static ResourceKey<PlacedFeature> placedCorundumOre = key(Registries.PLACED_FEATURE, "corundum_ore");

    public static ResourceKey<BiomeModifier> spawnVibratingOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "vibrating_crystal_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredVibratingOre = key(Registries.CONFIGURED_FEATURE, "vibrating_crystal_ore");
    public static ResourceKey<PlacedFeature> placedVibratingOre = key(Registries.PLACED_FEATURE, "vibrating_crystal_ore");

    public static ResourceKey<BiomeModifier> spawnSpinelOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "spinel_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredSpinelOre = key(Registries.CONFIGURED_FEATURE, "spinel_ore");
    public static ResourceKey<PlacedFeature> placedSpinelOre = key(Registries.PLACED_FEATURE, "spinel_ore");

    public static ResourceKey<BiomeModifier> spawnTourmalineOre = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "tourmaline_ore");
    public static ResourceKey<ConfiguredFeature<?,?>> configuredTourmalineOre = key(Registries.CONFIGURED_FEATURE, "tourmaline_ore");
    public static ResourceKey<PlacedFeature> placedTourmalineOre = key(Registries.PLACED_FEATURE, "tourmaline_ore");

    public static final GeodeItemObject icelandGeode = BLOCKS.registerGeode("iceland_spar_crystal", MapColor.COLOR_LIGHT_BLUE, Sounds.EARTH_CRYSTAL, Sounds.EARTH_CRYSTAL_CHIME.getSound(), Sounds.EARTH_CRYSTAL_CLUSTER,  3, ITEM_PROPS);
    public static final ResourceKey<ConfiguredFeature<?,?>> configuredIcelandGeode = key(Registries.CONFIGURED_FEATURE, "iceland_spar_geode");
    public static final ResourceKey<PlacedFeature> placedIcelandGeode = key(Registries.PLACED_FEATURE, "iceland_spar_geode");
    public static ResourceKey<BiomeModifier> spawnIcelandGeode = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "iceland_spar_geode");

    public static final GeodeItemObject topazGeode = BLOCKS.registerGeode("topaz_crystal", MapColor.COLOR_BLUE, Sounds.EARTH_CRYSTAL, Sounds.EARTH_CRYSTAL_CHIME.getSound(), Sounds.EARTH_CRYSTAL_CLUSTER,  3, ITEM_PROPS);
    public static final ResourceKey<ConfiguredFeature<?,?>> configuredTopazGeode = key(Registries.CONFIGURED_FEATURE, "topaz_geode");
    public static final ResourceKey<PlacedFeature> placedTopazGeode = key(Registries.PLACED_FEATURE, "topaz_geode");
    public static ResourceKey<BiomeModifier> spawnTopazGeode = key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, "topaz_geode");
    public TinkersCalibrationWorldFeatures()
    {

    }

    /** Calling this forces the static initializer to run, registering the geode blocks and items */
    public static void preload() {
    }
}
