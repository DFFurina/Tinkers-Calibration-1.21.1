package com.james.tinkerscalibration.contents;

import com.james.tinkerscalibration.TinkersCalibration;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.TConstruct;

public class TinkersCalibrationFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(TinkersCalibration.MODID);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenfiberglass = register("moltenfiberglass", 700);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenmangobberslime = register("moltenmangobberslime", 1500);
    public static final FlowingFluidObject<BaseFlowingFluid> moltengobber = register("moltengobber", 800);
    public static final FlowingFluidObject<BaseFlowingFluid> moltennethergobber = register("moltennethergobber", 950);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenfazelle = register("moltenfazelle", 1200);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenemperorslime = register("moltenemperorslime", 1150);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenlindsteel = register("moltenlindsteel", 1200);
    public static final FlowingFluidObject<BaseFlowingFluid> moltentitanium = register("moltentitanium", 1350);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenhymon = register("moltenhymon", 550);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenmandite = register("moltenmandite", 1450);
    public static final FlowingFluidObject<BaseFlowingFluid> moltencarminite = register("moltencarminite", 900);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenredmatter = register("moltenredmatter", 1500);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenendgobber = register("moltenendgobber", 1100);
    public static final FlowingFluidObject<BaseFlowingFluid> moltensoulgold = register("moltensoulgold", 1000);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenjazz = register("moltenjazz", 1300);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenrefinedquartz = register("moltenrefinedquartz", 1200);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenblazerite = register("moltenblazerite", 1250);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenenderite = register("moltenenderite", 1250);
    public static final FlowingFluidObject<BaseFlowingFluid> moltengolderite = register("moltengolderite", 1250);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenwitherite = register("moltenwitherite", 1250);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenphanterite = register("moltenphanterite", 1250);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenspiderite = register("moltenspiderite", 1250);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenprismarite = register("moltenprismarite", 1250);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenfeatherite = register("moltenfeatherite", 1250);
    //public static final FlowingFluidObject<BaseFlowingFluid> moltenaltairium = register("moltenaltairium", 1050);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenhalleium = register("moltenhalleium", 1100);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenhothium = register("moltenhothium", 1070);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenmagiga = register("moltenmagiga", 1240);
    public static final FlowingFluidObject<BaseFlowingFluid> moltentonium = register("moltentonium", 1220);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenimmersedsilver = register("moltenimmersedsilver", 1090);
    public static final FlowingFluidObject<BaseFlowingFluid> molteninertwitherium = register("molteninertwitherium", 1130);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenoraclium = register("moltenoraclium", 1440);
    public static final FlowingFluidObject<BaseFlowingFluid> moltensteamium = register("moltensteamium", 1100);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenstellarium = register("moltenstellarium", 1260);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenwitherium = register("moltenwitherium", 1490);
    public static final FlowingFluidObject<BaseFlowingFluid> moltengravity = register("moltengravity", 1450);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenhorizonite = register("moltenhorizonite", 790);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenmoonsteel = register("moltenmoonsteel", 870);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenmoonstone = register("moltenmoonstone", 800);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenichor = register("moltenichor", 1000);
    public static final FlowingFluidObject<BaseFlowingFluid> moltentopaz = register("moltentopaz", 700);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenicelandspar = register("moltenicelandspar", 800);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenlizanite = register("moltenlizanite", 780);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenprehnite = register("moltenprehnite", 690);
    public static final FlowingFluidObject<BaseFlowingFluid> moltendarkmatter = register("moltendarkmatter", 1400);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenalumite = register("moltenalumite", 925);
    public static final FlowingFluidObject<BaseFlowingFluid> moltencorundum = register("moltencorundum", 925);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenspinel = register("moltenspinel", 905);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenvibratingcrystal = register("moltenvibratingcrystal", 785);
    public static final FlowingFluidObject<BaseFlowingFluid> moltenventium = register("moltenventium", 800);
    public static final FlowingFluidObject<BaseFlowingFluid> moltencharoite = register("moltencharoite", 1400);
    public static final FlowingFluidObject<BaseFlowingFluid> moltendiopside = register("moltendiopside", 1400);
    //public static final FlowingFluidObject<BaseFlowingFluid> moltenlavacrystal = register("moltenlavacrystal", 900);
    public static final FlowingFluidObject<BaseFlowingFluid> dragonbreath = register("dragonbreath", 2000);
    private static FluidType.Properties hot(String name) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000)
                .descriptionId(TConstruct.makeDescriptionId("fluid", name))
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA);
    }
    private static FluidType.Properties cool(String name) {
        return cool().descriptionId(TConstruct.makeDescriptionId("fluid", name))
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY);
    }
    private static FluidType.Properties cool() {
        return FluidType.Properties.create()
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY);
    }
    private static FlowingFluidObject<BaseFlowingFluid> register(String name, int temp) {
        return FLUIDS.register(name).type(hot(name).temperature(temp).lightLevel(12)).block(MapColor.COLOR_RED, 12).bucket().flowing();
    }
}
