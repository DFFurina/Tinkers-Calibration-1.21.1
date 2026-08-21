package com.james.tinkerscalibration;

import com.james.tinkerscalibration.effect.*;
import com.james.tinkerscalibration.library.SpaghettiModifierRecipe;
import com.james.tinkerscalibration.modifiers.*;
import com.james.tinkerscalibration.modifiers.rewrite.NecroticModifierRewrite;
import com.james.tinkerscalibration.modifiers.rewrite.TastyModifierRewrite;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.mantle.registration.RegistryObject;
import slimeknights.mantle.registration.deferred.EnumDeferredRegister;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;
import slimeknights.tconstruct.tools.modifiers.traits.melee.LaceratingModifier;
import slimeknights.tconstruct.tools.modifiers.upgrades.general.MagneticModifier;

import java.util.function.IntFunction;
import java.util.function.Supplier;

public class Utils {
    public static final String MOD_ID = "tinkerscalibration";

    public static final SynchronizedDeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            SynchronizedDeferredRegister.create(Registries.RECIPE_SERIALIZER, TinkersCalibration.MODID);

    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TinkersCalibration.MODID);

    protected static final SynchronizedDeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            SynchronizedDeferredRegister.create(Registries.PARTICLE_TYPE, TinkersCalibration.MODID);

    protected static final EnumDeferredRegister<MobEffect> MOB_EFFECTS =
            new EnumDeferredRegister<>(Registries.MOB_EFFECT, TinkersCalibration.MODID);

    // Material IDs
    public static final MaterialId fiberglass = createMaterial("fiberglass");
    public static final MaterialId obsidian = createMaterial("obsidian");
    public static final MaterialId netherbrick = createMaterial("nether_brick");
    public static final MaterialId bamboosteel = createMaterial("bamboo_steel");
    public static final MaterialId purpur = createMaterial("purpur");
    public static final MaterialId refinedquartz = createMaterial("refinedquartz");
    public static final MaterialId lindsteel = createMaterial("lindsteel");
    public static final MaterialId gobber = createMaterial("gobber");
    public static final MaterialId gobbernether = createMaterial("gobbernether");
    public static final MaterialId mangobberslime = createMaterial("mangobberslime");
    public static final MaterialId emperorslime = createMaterial("emperorslime");
    public static final MaterialId hymon = createMaterial("hymon");
    public static final MaterialId fazelle = createMaterial("fazelle");
    public static final MaterialId blazewood = createMaterial("blazewood");
    public static final MaterialId carminite = createMaterial("carminite");
    public static final MaterialId soulgold = createMaterial("soulgold");
    public static final MaterialId andesitalloy = createMaterial("andesitalloy");
    public static final MaterialId cupronickel = createMaterial("cupronickel");
    public static final MaterialId redmatter = createMaterial("redmatter");
    public static final MaterialId titanium = createMaterial("titanium");
    public static final MaterialId breashell = createMaterial("breashell");
    public static final MaterialId signalum = createMaterial("signalum");
    public static final MaterialId mandite = createMaterial("mandite");
    public static final MaterialId emerald = createMaterial("emerald");
    public static final MaterialId jazz = createMaterial("jazz");
    public static final MaterialId netherite = createMaterial("netherite");
    public static final MaterialId gnetherite = createMaterial("gnetherite");
    public static final MaterialId bnetherite = createMaterial("bnetherite");
    public static final MaterialId enetherite = createMaterial("enetherite");
    public static final MaterialId wnetherite = createMaterial("wnetherite");
    public static final MaterialId pnetherite = createMaterial("pnetherite");
    public static final MaterialId snetherite = createMaterial("snetherite");
    public static final MaterialId prnetherite = createMaterial("prnetherite");
    public static final MaterialId fnetherite = createMaterial("fnetherite");
    public static final MaterialId diamond = createMaterial("diamond");
    public static final MaterialId ironwood = createMaterial("ironwood");
    public static final MaterialId silicon = createMaterial("silicon");
    public static final MaterialId vinteum = createMaterial("vinteum");
    public static final MaterialId stellarium = createMaterial("stellarium");
    public static final MaterialId immersedsilver = createMaterial("immersedsilver");
    public static final MaterialId inertwitherium = createMaterial("inertwitherium");
    public static final MaterialId wither = createMaterial("wither");
    public static final MaterialId halleium = createMaterial("halleium");
    public static final MaterialId hothium = createMaterial("hothium");
    public static final MaterialId oraclium = createMaterial("oraclium");
    public static final MaterialId steamium = createMaterial("steamium");
    public static final MaterialId grain = createMaterial("grain");
    public static final MaterialId scale = createMaterial("scale");
    public static final MaterialId brokenice = createMaterial("brokenice");
    public static final MaterialId corundum = createMaterial("corundum");
    public static final MaterialId nitre = createMaterial("nitre");
    public static final MaterialId icelandspar = createMaterial("icelandspar");
    public static final MaterialId spinel = createMaterial("spinel");
    public static final MaterialId tourmaline = createMaterial("tourmaline");
    public static final MaterialId moonstone = createMaterial("moonstone");
    public static final MaterialId topaz = createMaterial("topaz");
    public static final MaterialId lizanite = createMaterial("lizanite");
    public static final MaterialId prehnite = createMaterial("prehnite");
    public static final MaterialId vibrating_crystal = createMaterial("vibrating_crystal");
    public static final MaterialId aventurine = createMaterial("aventurine");
    public static final MaterialId magiga = createMaterial("magiga");
    public static final MaterialId treeroot = createMaterial("treeroot");
    public static final MaterialId tonium = createMaterial("tonium");
    public static final MaterialId gravity = createMaterial("gravity");
    public static final MaterialId memoryslime = createMaterial("memoryslime");
    public static final MaterialId darkmatter = createMaterial("darkmatter");
    public static final MaterialId alumite = createMaterial("alumite");

    // Mob Effects
    private static final IntFunction<Supplier<TinkerEffect>> MARKER_EFFECT = color -> () -> new NoMilkEffect(MobEffectCategory.BENEFICIAL, color, true);
    public static RegistryObject<TinkerEffect> impregnableEffect = MOB_EFFECTS.register("impregnable", MARKER_EFFECT.apply(0xDADBD2));
    public static RegistryObject<TinkerEffect> bloodthirstyEffect = MOB_EFFECTS.register("bloodthirsty", BloodThirstyEffect::new);
    public static RegistryObject<TinkerEffect> envyEffect = MOB_EFFECTS.register("envy", MARKER_EFFECT.apply(0x5A603E));
    public static RegistryObject<TinkerEffect> vibratingEffect = MOB_EFFECTS.register("vibrating", MARKER_EFFECT.apply(0xD7CB77));
    public static RegistryObject<TinkerEffect> vibratingArmorEffect = MOB_EFFECTS.register("vibrating_armor", MARKER_EFFECT.apply(0xD7CB77));
    public static RegistryObject<TinkerEffect> criticalspeedEffect = MOB_EFFECTS.register("criticalspeed", MARKER_EFFECT.apply(0xD7CB77));
    public static RegistryObject<TinkerEffect> bloodblastEffect = MOB_EFFECTS.register("bloodblast", MARKER_EFFECT.apply(0x8b0000));
    public static RegistryObject<TinkerEffect> resistEffect = MOB_EFFECTS.register("resist", MARKER_EFFECT.apply(0x8b0000));
    public static RegistryObject<TinkerEffect> witherSprintCooldownEffect = MOB_EFFECTS.register("wither_cooldown", MARKER_EFFECT.apply(0x8b0000));
    public static RegistryObject<DisequilibriumEffect> disequilibrium = MOB_EFFECTS.register("disequilibrium", DisequilibriumEffect::new);
    public static RegistryObject<EnviedEffect> envied = MOB_EFFECTS.register("envied", EnviedEffect::new);
    public static RegistryObject<EddyingEffect> eddyingEffect = MOB_EFFECTS.register("eddying", EddyingEffect::new);
    public static RegistryObject<LionHeartEffect> lionheartEffect = MOB_EFFECTS.register("lionheart", LionHeartEffect::new);
    public static RegistryObject<EnvyArmorEffect> envyArmorEffect = MOB_EFFECTS.register("envy_armor", EnvyArmorEffect::new);
    public static RegistryObject<DashArmorEffect> dashArmorEffect = MOB_EFFECTS.register("dash_armor", DashArmorEffect::new);
    public static RegistryObject<BloodThirstyArmorEffect> bloodArmorEffect = MOB_EFFECTS.register("bloodthirsty_armor", BloodThirstyArmorEffect::new);
    public static RegistryObject<HeavyEffect> heavyEffect = MOB_EFFECTS.register("heavy", HeavyEffect::new);

    // Modifiers
    public static StaticModifier<Modifier> sharplikeglass = MODIFIERS.register("sharp_like_glass", SharpLikeGlassModifier::new);
    public static StaticModifier<Modifier> lightlikeglass = MODIFIERS.register("light_like_glass", LightLikeGlassModifier::new);
    public static StaticModifier<Modifier> fragilelikeglass = MODIFIERS.register("fragile_like_glass", FragileLikeGlassModifier::new);
    public static StaticModifier<Modifier> purgatory = MODIFIERS.register("purgatory", PurgatoryModifier::new);
    public static StaticModifier<Modifier> levitate = MODIFIERS.register("levitate", LevitateModifier::new);
    public static StaticModifier<Modifier> bamboogrowing = MODIFIERS.register("bamboo_growing", BambooGrowingModifier::new);
    public static StaticModifier<Modifier> refined = MODIFIERS.register("refined", RefinedModifier::new);
    public static StaticModifier<Modifier> impregnable = MODIFIERS.register("impregnable", ImpregnableModifier::new);
    public static StaticModifier<Modifier> gobberbless = MODIFIERS.register("gobber_bless", GobberBlessModifier::new);
    public static StaticModifier<Modifier> gobbercurse = MODIFIERS.register("gobber_curse", GobberCurseModifier::new);
    public static StaticModifier<Modifier> gobberblessnether = MODIFIERS.register("gobber_bless_nether", GobberBlessNetherModifier::new);
    public static StaticModifier<Modifier> gobbercursenether = MODIFIERS.register("gobber_curse_nether", GobberCurseNetherModifier::new);
    public static StaticModifier<Modifier> united = MODIFIERS.register("united", UnitedModifier::new);
    public static StaticModifier<Modifier> overslimeempire = MODIFIERS.register("overslime_empire", OverslimeEmpireModifier::new);
    public static StaticModifier<Modifier> overslimearmy = MODIFIERS.register("overslime_army", OverslimeArmyModifier::new);
    public static StaticModifier<Modifier> hydrophilous = MODIFIERS.register("hydrophilous", HydrophilousModifier::new);
    public static StaticModifier<Modifier> dash = MODIFIERS.register("dash", DashModifier::new);
    public static StaticModifier<Modifier> darkness = MODIFIERS.register("darkness", DarknessModifier::new);
    public static StaticModifier<Modifier> stonecold = MODIFIERS.register("stone_cold", StoneColdModifier::new);
    public static StaticModifier<Modifier> vengeance = MODIFIERS.register("vengeance", VengeanceModifier::new);
    public static StaticModifier<Modifier> igneous = MODIFIERS.register("igneous", IgneousModifier::new);
    public static StaticModifier<Modifier> beekeeper = MODIFIERS.register("bee_keeper", BeeKeeperModifier::new);
    public static StaticModifier<Modifier> familiar = MODIFIERS.register("familiar", FamiliarModifier::new);
    public static StaticModifier<Modifier> subdue = MODIFIERS.register("subdue", SubdueModifier::new);
    public static StaticModifier<Modifier> dominate = MODIFIERS.register("dominate", DominateModifier::new);
    public static StaticModifier<Modifier> airblade = MODIFIERS.register("airblade", AirBladeModifier::new);
    public static StaticModifier<Modifier> laceratingtitanium = MODIFIERS.register("laceratingtitanium", LaceratingModifier::new);
    public static StaticModifier<Modifier> criticalattack = MODIFIERS.register("criticalattack", CriticalAttackModifier::new);
    public static StaticModifier<Modifier> energystorage = MODIFIERS.register("energystorage", EnergyStorageModifier::new);
    public static StaticModifier<Modifier> energyaccelerate = MODIFIERS.register("energyaccelerate", EnergyAccelerateModifier::new);
    public static StaticModifier<Modifier> energyshield = MODIFIERS.register("energyshield", EnergyShieldModifier::new);
    public static StaticModifier<Modifier> bloodthirsty = MODIFIERS.register("bloodthirsty", BloodThirstyModifier::new);
    public static StaticModifier<Modifier> mossy = MODIFIERS.register("mossy", MossyModifier::new);
    public static StaticModifier<Modifier> tastyrewrite = MODIFIERS.register("tastyrewrite", TastyModifierRewrite::new);
    public static StaticModifier<Modifier> necroticrewrite = MODIFIERS.register("necroticrewrite", NecroticModifierRewrite::new);
    public static StaticModifier<Modifier> shulker = MODIFIERS.register("shulker", ShulkerModifier::new);
    public static StaticModifier<Modifier> circulation = MODIFIERS.register("circulation", CirculationModifier::new);
    public static StaticModifier<Modifier> benthamism = MODIFIERS.register("benthamism", BenthamismModifier::new);
    public static StaticModifier<Modifier> welltrained = MODIFIERS.register("welltrained", WellTrainedModifier::new);
    public static StaticModifier<Modifier> blacksmith = MODIFIERS.register("blacksmith", BlacksmithModifier::new);
    public static StaticModifier<Modifier> brutekiller = MODIFIERS.register("brutekiller", BruteKillerModifier::new);
    public static StaticModifier<Modifier> blazerite = MODIFIERS.register("blazerite", BlazeriteModifier::new);
    public static StaticModifier<Modifier> witherite = MODIFIERS.register("witherite", WitheriteModifier::new);
    public static StaticModifier<Modifier> phanterite = MODIFIERS.register("phanterite", PhanteriteModifier::new);
    public static StaticModifier<Modifier> prismarite = MODIFIERS.register("prismarite", PrismariteModifier::new);
    public static StaticModifier<Modifier> spiderite = MODIFIERS.register("spiderite", SpideriteModifier::new);
    public static StaticModifier<Modifier> featherite = MODIFIERS.register("featherite", MagneticModifier::new);
    public static StaticModifier<Modifier> envy = MODIFIERS.register("envy", EnvyModifier::new);
    public static StaticModifier<Modifier> photovoltaic = MODIFIERS.register("photovoltaic", PhotovoltaicModifier::new);
    public static StaticModifier<Modifier> enderite = MODIFIERS.register("enderite", EnderiteModifier::new);
    public static StaticModifier<Modifier> fire = MODIFIERS.register("fire", FireModifier::new);
    public static StaticModifier<Modifier> oxidelayer = MODIFIERS.register("oxidelayer", OxideLayerModifier::new);
    public static StaticModifier<Modifier> incandescent = MODIFIERS.register("incandescent", IncandescentModifier::new);
    public static StaticModifier<Modifier> respect = MODIFIERS.register("respect", RespectModifier::new);
    public static StaticModifier<Modifier> gravitydisequilibrium = MODIFIERS.register("gravitydisequilibrium", GravityDisequilibriumModifier::new);
    public static StaticModifier<Modifier> carbonizing = MODIFIERS.register("carbonizing", CarbonizingModifier::new);
    public static StaticModifier<Modifier> clustering = MODIFIERS.register("clustering", ClusteringModifier::new);
    public static StaticModifier<Modifier> creaky = MODIFIERS.register("creaky", CreakyModifier::new);
    public static StaticModifier<Modifier> decoying = MODIFIERS.register("decoying", DecoyingModifier::new);
    public static StaticModifier<Modifier> degenerating = MODIFIERS.register("degenerating", DegeneratingModifier::new);
    public static StaticModifier<Modifier> hyper = MODIFIERS.register("hyper", HyperModifier::new);
    public static StaticModifier<Modifier> icy = MODIFIERS.register("icy", IcyModifier::new);
    public static StaticModifier<Modifier> infernalblazing = MODIFIERS.register("infernalblazing", InfernalBlazingModifier::new);
    public static StaticModifier<Modifier> inky = MODIFIERS.register("inky", InkyModifier::new);
    public static StaticModifier<Modifier> peaceenergetic = MODIFIERS.register("peaceenergetic", PeaceEnergeticModifier::new);
    public static StaticModifier<Modifier> soluble = MODIFIERS.register("soluble", SolubleModifier::new);
    public static StaticModifier<Modifier> staminafocusing = MODIFIERS.register("staminafocusing", StaminaFocusingModifier::new);
    public static StaticModifier<Modifier> steamy = MODIFIERS.register("steamy", SteamyModifier::new);
    public static StaticModifier<Modifier> unsettled = MODIFIERS.register("unsettled", UnsettledModifier::new);
    public static StaticModifier<Modifier> birefringent = MODIFIERS.register("birefringent", BirefringentModifier::new);
    public static StaticModifier<Modifier> blowing = MODIFIERS.register("blowing", BlowingModifier::new);
    public static StaticModifier<Modifier> champing = MODIFIERS.register("champing", ChampingModifier::new);
    public static StaticModifier<Modifier> cherishing = MODIFIERS.register("cherishing", CherishingModifier::new);
    public static StaticModifier<Modifier> vibrating = MODIFIERS.register("vibrating", VibratingModifier::new);
    public static StaticModifier<Modifier> combustionsupporting = MODIFIERS.register("combustionsupporting", CombustionSupportingModifier::new);
    public static StaticModifier<Modifier> dragonkiller = MODIFIERS.register("dragonkiller", DragonKillerModifier::new);
    public static StaticModifier<Modifier> eddying = MODIFIERS.register("eddying", EddyingModifier::new);
    public static StaticModifier<Modifier> extremefreezing = MODIFIERS.register("extremefreezing", ExtremeFreezingModifier::new);
    public static StaticModifier<Modifier> gluttonic = MODIFIERS.register("gluttonic", GluttonicModifier::new);
    public static StaticModifier<Modifier> gorgeous = MODIFIERS.register("gorgeous", GorgeousModifier::new);
    public static StaticModifier<Modifier> hemolytic = MODIFIERS.register("hemolytic", HemolyticModifier::new);
    public static StaticModifier<Modifier> inductance = MODIFIERS.register("inductance", InductanceModifier::new);
    public static StaticModifier<Modifier> infiltrating = MODIFIERS.register("infiltrating", InfiltratingModifier::new);
    public static StaticModifier<Modifier> lionheart = MODIFIERS.register("lionheart", LionheartModifier::new);
    public static StaticModifier<Modifier> maiming = MODIFIERS.register("maiming", MaimingModifier::new);
    public static StaticModifier<Modifier> oracular = MODIFIERS.register("oracular", OracularModifier::new);
    public static StaticModifier<Modifier> piercing = MODIFIERS.register("piercing", PiercingModifier::new);
    public static StaticModifier<Modifier> pyroelectric = MODIFIERS.register("pyroelectric", PyroelectricModifier::new);
    public static StaticModifier<Modifier> shaking = MODIFIERS.register("shaking", ShakingModifier::new);
    public static StaticModifier<Modifier> switching = MODIFIERS.register("switching", SwitchingModifier::new);
    public static StaticModifier<Modifier> terrifying = MODIFIERS.register("terrifying", TerrifyingModifier::new);
    public static StaticModifier<Modifier> witherflow = MODIFIERS.register("witherflow", WitherFlowModifier::new);
    public static StaticModifier<Modifier> overnatural = MODIFIERS.register("overnatural", OverNaturalModifier::new);
    public static StaticModifier<Modifier> tenacity = MODIFIERS.register("tenacity", TenacityModifier::new);
    public static StaticModifier<Modifier> vegetate = MODIFIERS.register("vegetate", VegetateModifier::new);
    public static StaticModifier<Modifier> heavy = MODIFIERS.register("heavy", HeavyModifier::new);
    public static StaticModifier<Modifier> moonpower = MODIFIERS.register("moonpower", MoonPowerModifier::new);
    public static StaticModifier<Modifier> criticalspeed = MODIFIERS.register("criticalspeed", CriticalSpeedModifier::new);
    public static StaticModifier<Modifier> mossmending = MODIFIERS.register("mossmending", MossyMendingModifier::new);
    public static StaticModifier<Modifier> arrogance = MODIFIERS.register("arrogance", ArroganceModifier::new);
    public static StaticModifier<Modifier> scorn = MODIFIERS.register("scorn", ScornModifier::new);
    public static StaticModifier<Modifier> lifeinspire = MODIFIERS.register("lifeinspire", LifeInspireModifier::new);
    public static StaticModifier<Modifier> overshield = MODIFIERS.register("overshield", OvershieldModifier::new);
    public static StaticModifier<Modifier> overmemory = MODIFIERS.register("overmemory", OverslimeMemoryModifier::new);
    public static StaticModifier<Modifier> globaltraveller = MODIFIERS.register("global_traveller", GlobalTravellerModifier::new);
    public static StaticModifier<Modifier> spaghetti = MODIFIERS.register("spaghetti", SpaghettiModifier::new);
    public static StaticModifier<Modifier> spaghetti2 = MODIFIERS.register("spaghetti2", Spaghetti2Modifier::new);
    public static StaticModifier<Modifier> spaghetti3 = MODIFIERS.register("spaghetti3", Spaghetti3Modifier::new);
    public static StaticModifier<Modifier> glisten = MODIFIERS.register("glisten", GlistenModifier::new);
    public static StaticModifier<Modifier> arbalist = MODIFIERS.register("arbalist", ArbalistModifier::new);

    public static final RegistryObject<RecipeSerializer<SpaghettiModifierRecipe>> spaghettiSerializer;

    public static final ModifierId ecological = id("ecological");
    public static final ModifierId scute = id("scute");
    public static final ModifierId defense_respawn = id("defense_respawn");
    public static final ModifierId gobber_unbreakable = id("gobber_unbreakable");
    public static final ModifierId golderite = id("golderite_armor");

    static {
        spaghettiSerializer = RECIPE_SERIALIZERS.register("spaghetti_modifier",
                () -> LoadableRecipeSerializer.of(SpaghettiModifierRecipe.LOADER));
    }

    private static ModifierId id(String name) {
        return new ModifierId(TinkersCalibration.MODID, name);
    }

    public static MaterialId createMaterial(String name) {
        return new MaterialId(ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, name));
    }
}
