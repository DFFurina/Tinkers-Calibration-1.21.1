package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.bus.api.Event;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

public class ArmorFeatheriteModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> FEATHER = TConstruct.createKey("featherite_armor");
}
/*
    public ArmorFeatheriteModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorFeatheriteModifier::onApplyEffect);
        NeoForge.EVENT_BUS.addListener(ArmorFeatheriteModifier::onUpdateApply);
        NeoForge.EVENT_BUS.addListener(ArmorFeatheriteModifier::onHurt);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(FEATHER, false, null));
    }
    private static void onApplyEffect(MobEffectEvent event) {
        LivingEntity living = event.getEntity();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(FEATHER, 0);
            if (level > 0 && living instanceof ServerPlayer && event.getEffectInstance() != null) {
                if (event.getEffectInstance().getEffect() == MobEffects.LEVITATION && UpgradedNetheriteConfig.EnableLevitationImmune) {
                    event.setResult(Event.Result.DENY);
                    if (event.isCancelable()) {
                        event.setCanceled(true);
                    }
                }
            }
        });
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        LivingEntity living = evt.getEntity();
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (living.isAlive()) {
                    living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                        int level = holder.get(FEATHER, 0);
                        if (level > 0 && living instanceof Player player && UpgradedNetheriteConfig.EnableWaterLavaWalking)
                        {
                            Level world = player.getCommandSenderWorld();
                            if(world.getFluidState(player.getOnPos()).is(FluidTags.LAVA) && !player.isCrouching()|| world.getFluidState(player.getOnPos()).is(FluidTags.WATER) && !player.isCrouching() && !player.isSwimming())
                            {
                                if(player.getDeltaMovement().y() < 0 && !player.isInLava() && !player.isInWater())
                                {
                                    player.fallDistance = 0.0F;
                                    player.setOnGround(true);
                                    player.setDeltaMovement(player.getDeltaMovement().add(0.0, -player.getDeltaMovement().y(), 0.0));
                                }
                                else if (player.getDeltaMovement().y < 0.15 && !player.isEyeInFluid(FluidTags.LAVA) && !player.isEyeInFluid(FluidTags.WATER)) {
                                    player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.15 - player.getDeltaMovement().y, 0.0));
                                }
                            }
                        }

                    });
                }
            }
        }
    }

    private static void onHurt(LivingDamageEvent.Pre event) {
        LivingEntity living = event.getEntity();
        living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
            int level = holder.get(FEATHER, 0);
            if(level > 0) {
                if (event.getSource().isFall())
                {
                    event.setAmount(event.getAmount() * 0.5f);
                }
            }
        });
    }

}*/