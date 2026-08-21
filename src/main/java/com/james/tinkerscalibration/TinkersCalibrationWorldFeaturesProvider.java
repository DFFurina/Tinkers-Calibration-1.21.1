package com.james.tinkerscalibration;

import com.james.tinkerscalibration.contents.TinkersCalibrationBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.common.world.BiomeModifier;
// TODO: Fix ForgeBiomeModifiers API for NeoForge 1.21.1
// import net.neoforged.neoforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import slimeknights.tconstruct.common.registration.GeodeItemObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.james.tinkerscalibration.TinkersCalibrationWorldFeatures.*;
import static net.minecraft.core.HolderSet.direct;
import static slimeknights.tconstruct.world.TinkerWorld.placedLargeCobaltOre;
import static slimeknights.tconstruct.world.TinkerWorld.spawnCobaltOre;

public class TinkersCalibrationWorldFeaturesProvider{
    private TinkersCalibrationWorldFeaturesProvider() {
    }

    /** Registers this provider with the data generator */
    public static void register(RegistrySetBuilder builder) {
        builder.add(Registries.PLACED_FEATURE, TinkersCalibrationWorldFeaturesProvider::registerPlacedFeatures);
        builder.add(Registries.CONFIGURED_FEATURE, TinkersCalibrationWorldFeaturesProvider::registerConfiguredFeatures);
        // TODO: Fix BiomeModifiers registration for NeoForge 1.21.1
        // builder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, TinkersCalibrationWorldFeaturesProvider::registerBiomeModifiers);
    }
    private static void registerPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        // ores
        register(context, placedTitaniumOre, configuredTitaniumOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(6)), BiomeFilter.biome());
        register(context, placedCorundumOre, configuredCorundumOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(10)), BiomeFilter.biome());
        register(context, placedToniumOre, configuredToniumOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(6)), BiomeFilter.biome());
        register(context, placedMagigaOre, configuredMagigaOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(6)), BiomeFilter.biome());
        register(context, placedVibratingOre, configuredVibratingOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-30), VerticalAnchor.absolute(0)), BiomeFilter.biome());
        register(context, placedInertwitheriumOre, configuredInertwitheriumOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(5), VerticalAnchor.absolute(20)), BiomeFilter.biome());
        register(context, placedTourmalineOre, configuredTourmalineOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(20)), BiomeFilter.biome());
        register(context, placedSpinelOre, configuredSpinelOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(10)), BiomeFilter.biome());
        register(context, placedStellariumOre, configuredStellariumOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(6)), BiomeFilter.biome());
        register(context, placedImmersedSilverOre, configuredImmersedSilverOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(20), VerticalAnchor.absolute(40)), BiomeFilter.biome());
        register(context, placedHothiumOre, configuredHothiumOre, CountPlacement.of(5), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(60)), BiomeFilter.biome());

        // geodes
        placeGeode(context, placedTopazGeode, configuredTopazGeode, RarityFilter.onAverageOnceEvery(128), HeightRangePlacement.uniform(VerticalAnchor.absolute(0),  VerticalAnchor.absolute(54)));
        placeGeode(context, placedIcelandGeode,   configuredIcelandGeode,   RarityFilter.onAverageOnceEvery(128),  HeightRangePlacement.uniform(VerticalAnchor.absolute(0),    VerticalAnchor.absolute(54)));
    }
    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?,?>> configured, PlacementModifier... placement) {
        context.register(key, new PlacedFeature(context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configured), List.of(placement)));
    }

    private static void registerConfiguredFeatures(BootstrapContext<ConfiguredFeature<?,?>> context) {
        configureGeode(context, configuredIcelandGeode, icelandGeode, BlockStateProvider.simple(Blocks.BLUE_ICE), BlockStateProvider.simple(Blocks.SNOW_BLOCK),
                new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 5.2D), new GeodeCrackSettings(0.95D, 2.0D, 2), UniformInt.of(6, 9), UniformInt.of(3, 4), UniformInt.of(1, 2), 16, 1);
        configureGeode(context, configuredTopazGeode, topazGeode, BlockStateProvider.simple(Blocks.LIGHT_BLUE_TERRACOTTA), BlockStateProvider.simple(Blocks.GRANITE),
                new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 5.2D), new GeodeCrackSettings(0.95D, 2.0D, 2), UniformInt.of(6, 9), UniformInt.of(3, 4), UniformInt.of(1, 2), 16, 1);
        RuleTest netherrack = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest stone = new BlockMatchTest(Blocks.STONE);
        RuleTest deepslate = new BlockMatchTest(Blocks.DEEPSLATE);
        BlockState titaniumOre = TinkersCalibrationBlocks.titanium_ore.get().defaultBlockState();
        BlockState deeptitaniumOre = TinkersCalibrationBlocks.deepslate_titanium_ore.get().defaultBlockState();
        BlockState toniumOre = TinkersCalibrationBlocks.tonium_ore.get().defaultBlockState();
        BlockState deeptoniumOre = TinkersCalibrationBlocks.deepslate_tonium_ore.get().defaultBlockState();
        BlockState hothiumOre = TinkersCalibrationBlocks.hothium_ore.get().defaultBlockState();
        BlockState deephothiumOre = TinkersCalibrationBlocks.deepslate_hothium_ore.get().defaultBlockState();
        BlockState corundumOre = TinkersCalibrationBlocks.corundum_ore.get().defaultBlockState();
        BlockState deepcorundumOre = TinkersCalibrationBlocks.deepslate_corundum_ore.get().defaultBlockState();
        BlockState magigaOre = TinkersCalibrationBlocks.magiga_ore.get().defaultBlockState();
        BlockState deepmagigaOre = TinkersCalibrationBlocks.deepslate_magiga_ore.get().defaultBlockState();
        BlockState spinelOre = TinkersCalibrationBlocks.spinel_ore.get().defaultBlockState();
        BlockState deepspinelOre = TinkersCalibrationBlocks.deepslate_spinel_ore.get().defaultBlockState();
        BlockState tourmalineOre = TinkersCalibrationBlocks.tourmaline_ore.get().defaultBlockState();
        BlockState deeptourmalineOre = TinkersCalibrationBlocks.deepslate_tourmaline_ore.get().defaultBlockState();
        BlockState silverOre = TinkersCalibrationBlocks.immersed_silver_ore.get().defaultBlockState();
        BlockState deepsilverOre = TinkersCalibrationBlocks.deepslate_immersed_silver_ore.get().defaultBlockState();
        BlockState stellariumOre = TinkersCalibrationBlocks.stellarium_ore.get().defaultBlockState();
        BlockState deepstellariumOre = TinkersCalibrationBlocks.deepslate_stellarium_ore.get().defaultBlockState();
        BlockState vibratingOre = TinkersCalibrationBlocks.vibrating_crystal_ore.get().defaultBlockState();
        BlockState deepvibratingOre = TinkersCalibrationBlocks.deepslate_vibrating_crystal_ore.get().defaultBlockState();
        BlockState witheriumOre = TinkersCalibrationBlocks.inert_witherium_ore.get().defaultBlockState();
        register(context, configuredTitaniumOre, Feature.ORE, new OreConfiguration(stone, titaniumOre, 4));
        register(context, configuredTitaniumOre, Feature.ORE, new OreConfiguration(deepslate, deeptitaniumOre, 4));
        register(context, configuredToniumOre, Feature.ORE, new OreConfiguration(stone, toniumOre, 4));
        register(context, configuredToniumOre, Feature.ORE, new OreConfiguration(deepslate, deeptoniumOre, 4));
        register(context, configuredHothiumOre, Feature.ORE, new OreConfiguration(stone, hothiumOre, 4));
        register(context, configuredHothiumOre, Feature.ORE, new OreConfiguration(deepslate, deephothiumOre, 4));
        register(context, configuredCorundumOre, Feature.ORE, new OreConfiguration(stone, corundumOre, 4));
        register(context, configuredCorundumOre, Feature.ORE, new OreConfiguration(deepslate, deepcorundumOre, 4));
        register(context, configuredMagigaOre, Feature.ORE, new OreConfiguration(stone, magigaOre, 4));
        register(context, configuredMagigaOre, Feature.ORE, new OreConfiguration(deepslate, deepmagigaOre, 4));
        register(context, configuredSpinelOre, Feature.ORE, new OreConfiguration(stone, spinelOre, 4));
        register(context, configuredSpinelOre, Feature.ORE, new OreConfiguration(deepslate, deepspinelOre, 4));
        register(context, configuredTourmalineOre, Feature.ORE, new OreConfiguration(stone, tourmalineOre, 4));
        register(context, configuredTourmalineOre, Feature.ORE, new OreConfiguration(deepslate, deeptourmalineOre, 4));
        register(context, configuredImmersedSilverOre, Feature.ORE, new OreConfiguration(stone, silverOre, 4));
        register(context, configuredImmersedSilverOre, Feature.ORE, new OreConfiguration(deepslate, deepsilverOre, 4));
        register(context, configuredStellariumOre, Feature.ORE, new OreConfiguration(stone, stellariumOre, 4));
        register(context, configuredStellariumOre, Feature.ORE, new OreConfiguration(deepslate, deepstellariumOre, 4));
        register(context, configuredVibratingOre, Feature.ORE, new OreConfiguration(stone, vibratingOre, 4));
        register(context, configuredVibratingOre, Feature.ORE, new OreConfiguration(deepslate, deepvibratingOre, 4));
        register(context, configuredInertwitheriumOre, Feature.ORE, new OreConfiguration(stone, witheriumOre, 4));
    }

    // TODO: Fix BiomeModifiers for NeoForge 1.21.1
    /*
    private static void registerBiomeModifiers(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
        HolderSet<Biome> overworld = biomes.getOrThrow(BiomeTags.IS_OVERWORLD);
        HolderSet<Biome> nether = biomes.getOrThrow(BiomeTags.IS_NETHER);
        context.register(spawnTitaniumOre, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedTitaniumOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnHothiumOre, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedHothiumOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnToniumOre, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedToniumOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnMagigaOre, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedMagigaOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnImmersedSilverOre, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedImmersedSilverOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnStellariumOre, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedStellariumOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnSpinelOre, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedSpinelOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnTourmalineOre, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedTourmalineOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnCorundumOre, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedCorundumOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnVibratingOre, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedVibratingOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnInertwitheriumOre, new AddFeaturesBiomeModifier(nether, direct(placed.getOrThrow(placedInertwitheriumOre)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
        context.register(spawnIcelandGeode, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedIcelandGeode)), GenerationStep.Decoration.LOCAL_MODIFICATIONS));
        context.register(spawnTopazGeode, new AddFeaturesBiomeModifier(overworld, direct(placed.getOrThrow(placedTopazGeode)), GenerationStep.Decoration.LOCAL_MODIFICATIONS));

    }
    */

        private static void placeGeode(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?,?>> configured, RarityFilter rarity, HeightRangePlacement height) {
        register(context, key, configured, rarity, InSquarePlacement.spread(), height, BiomeFilter.biome());
    }
    private static void configureGeode(BootstrapContext<ConfiguredFeature<?,?>> context, ResourceKey<ConfiguredFeature<?,?>> key, GeodeItemObject geode,
                                        BlockStateProvider middleLayer, BlockStateProvider outerLayer, GeodeLayerSettings layerSettings, GeodeCrackSettings crackSettings,
                                        IntProvider outerWall, IntProvider distributionPoints, IntProvider pointOffset, int genOffset, int invalidBlocks) {
        register(context, key, Feature.GEODE, new GeodeConfiguration(
                new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(geode.getBlock()),
                        BlockStateProvider.simple(geode.getBudding()),
                        middleLayer, outerLayer,
                        Arrays.stream(GeodeItemObject.BudSize.values()).map(type -> geode.getBud(type).defaultBlockState()).toList(),
                        BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                layerSettings, crackSettings, 0.335, 0.083, true, outerWall, distributionPoints, pointOffset, -genOffset, genOffset, 0.05D, invalidBlocks)
        );
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?,?>> context, ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }

    /** Registers a configured feature */
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?,?>> context, ResourceKey<ConfiguredFeature<?,?>> key, Supplier<F> feature, FC config) {
        register(context, key, feature.get(), config);
    }
}
