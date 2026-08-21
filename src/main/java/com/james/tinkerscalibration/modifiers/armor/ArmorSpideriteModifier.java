package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import net.minecraft.client.player.LocalPlayer;

public class ArmorSpideriteModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> SPIDERITE = TConstruct.createKey("spiderite_armor");

    public ArmorSpideriteModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorSpideriteModifier::onUpdateApply);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(SPIDERITE, false, null));
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        if (!(evt.getEntity() instanceof LivingEntity living)) return;
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (living.isAlive()) {
                    TinkerDataCapability.getOptional(living).ifPresent((holder) -> {
                        int level = holder.get(SPIDERITE, 0);
                        if (level > 0 && living instanceof Player player)// && UpgradedNetheriteConfig.EnableClimbWall)
                        {
                            if (!player.isCrouching() && getAbilityClimbwall(player)) {
                                if (player.getDeltaMovement().y() < 0.0) {
                                    player.setOnGround(true);
                                    player.setDeltaMovement(player.getDeltaMovement().add(-player.getDeltaMovement().x() / 5.0, -player.getDeltaMovement().y(), -player.getDeltaMovement().z() / 5.0));
                                    player.fallDistance = 0.0F;
                                }

                                if (player.getDeltaMovement().x() != 0.0 && player.getDeltaMovement().z() != 0.0) {
                                    setAbilityClimbwall(player, false);
                                }
                            }

                            if ((player.isCrouching() || player.onClimbable()) && getAbilityClimbwall(player)) {
                                setAbilityClimbwall(player, false);
                            }
                            if (!player.isCrouching() && player.horizontalCollision && !player.isInLava() && !player.isInWater() && !player.getAbilities().flying && !player.onClimbable() && player.getDeltaMovement().y() < 0.1) {
                                double LookAt = player.getLookAngle().y;
                                if (LookAt > 0.1) {
                                    LookAt = 0.1;
                                }

                                if (LookAt < -0.1) {
                                    LookAt = -0.1;
                                }

                                if (player.getCommandSenderWorld().isClientSide && player instanceof LocalPlayer && ((LocalPlayer)player).input.forwardImpulse < 0.0F) {
                                    LookAt = LookAt * -1.0;
                                }

                                player.setDeltaMovement(player.getDeltaMovement().add(-player.getDeltaMovement().x() / 5.0, LookAt - player.getDeltaMovement().y, -player.getDeltaMovement().z() / 5.0));
                                setAbilityClimbwall(player, true);
                                player.fallDistance = 0.0F;
                                if (player.getCommandSenderWorld().isClientSide) {
                                    //PlayerFallDistanceUpdateHandler.PlayerFallDistanceUpdate(player.getUUID(), player.fallDistance);
                                }
                            }
                        }
                    });
                }

            }
        }
    }
    public static void setAbilityClimbwall(Entity entity, Boolean bool) {
        entity.getPersistentData().putBoolean("tinkerscalibration_upgradednetherite_climbwall", bool);
    }
    public static boolean getAbilityClimbwall(Entity entity) {
        return entity.getPersistentData().contains("tinkerscalibration_upgradednetherite_climbwall") && entity.getPersistentData().getBoolean("tinkerscalibration_upgradednetherite_climbwall");
    }
}
