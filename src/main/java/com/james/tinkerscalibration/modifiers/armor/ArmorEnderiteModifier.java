package com.james.tinkerscalibration.modifiers.armor;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.modules.technical.ArmorLevelModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArmorEnderiteModifier extends Modifier {
    private static final TinkerDataCapability.TinkerDataKey<Integer> ENDERITE = TConstruct.createKey("enderite_armor");
}
/*
    public ArmorEnderiteModifier() {
        super();
        NeoForge.EVENT_BUS.addListener(ArmorEnderiteModifier::onUpdateApply);
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(new ArmorLevelModule(ENDERITE, false, null));
    }
    private static void onUpdateApply(EntityTickEvent.Post evt) {
        LivingEntity living = evt.getEntity();
        if (!living.isSpectator()) {
            EquipmentContext context = new EquipmentContext(living);
            if (context.hasModifiableArmor()) {
                if (!living.getCommandSenderWorld().isClientSide && living.isAlive()) {
                    living.getCapability(TinkerDataCapability.CAPABILITY).ifPresent((holder) -> {
                        int level = holder.get(ENDERITE, 0);
                        if (level > 0 && living instanceof Player player && !EntityDataUtil.hasEnderTeleportCooldown(player)){
                            Level world = player.getCommandSenderWorld();
                            BlockPos onPos = player.getOnPos();
                            BlockPos belowPos = player.getOnPos().below();
                            BlockState state = world.getBlockState(belowPos);
                            if(state.getMaterial().blocksMotion())
                                EntityDataUtil.setAbilityEnderPos(player, true);
                            if(player.getCommandSenderWorld().dimension() == Level.OVERWORLD && player.getY() < -128.0 || player.getY() < -64.0)
                            {
                                BlockPos pos = EntityDataUtil.getAbilityEnderPos(player);
                                if(pos == null)
                                {
                                    world.setBlockAndUpdate(onPos.above(66), Blocks.OBSIDIAN.defaultBlockState());
                                    world.setBlockAndUpdate(onPos.above(67), Blocks.AIR.defaultBlockState());
                                    world.setBlockAndUpdate(onPos.above(68), Blocks.AIR.defaultBlockState());
                                    player.stopRiding();
                                    player.fallDistance = 0.0F;
                                    player.teleportTo(onPos.getX(), onPos.getY() + 67, onPos.getZ());
                                    player.getPersistentData().putInt("upgraded_netherite_ender_teleport_cd", 20);
                                    SoundEvent soundevent = SoundEvents.ENDERMAN_TELEPORT;
                                    player.getCommandSenderWorld().playSound(null, onPos.getX(), onPos.getY() + 65, onPos.getZ(), soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                                    player.playSound(soundevent, 1.0F, 1.0F);
                                    return;
                                }
                                List<BlockPos> validTpList = new ArrayList<>();
                                if (world.getBlockState(pos.below()).getMaterial().blocksMotion() && (player.getCommandSenderWorld().getFluidState(pos).isEmpty() || world.getBlockState(pos).is(Blocks.BUBBLE_COLUMN)) && world.getBlockState(pos).isPathfindable(world, pos, PathComputationType.LAND) && (world.getFluidState(pos.above()).isEmpty() || world.getBlockState(pos.above()).is(Blocks.BUBBLE_COLUMN)) && world.getBlockState(pos.above()).isPathfindable(world, pos.above(), PathComputationType.LAND)) {
                                    validTpList.add(pos.immutable());
                                }

                                if (validTpList.isEmpty()) {
                                    Iterator<BlockPos> var4 = BlockPos.betweenClosed(pos.offset(-5, -5, -5), pos.offset(5, 5, 5)).iterator();

                                    label90:
                                    while(true) {
                                        BlockPos blockpos1;
                                        do {
                                            do {
                                                do {
                                                    do {
                                                        if (!var4.hasNext()) {
                                                            break label90;
                                                        }

                                                        blockpos1 = var4.next();
                                                    } while(!world.getBlockState(blockpos1.below()).getMaterial().blocksMotion());
                                                } while(!world.getFluidState(blockpos1).isEmpty() && !world.getBlockState(blockpos1).is(Blocks.BUBBLE_COLUMN));
                                            } while(!world.getBlockState(blockpos1).isPathfindable(world, blockpos1, PathComputationType.LAND));
                                        } while(!world.getFluidState(blockpos1.above()).isEmpty() && !world.getBlockState(blockpos1.above()).is(Blocks.BUBBLE_COLUMN));

                                        if (world.getBlockState(blockpos1.above()).isPathfindable(player.getCommandSenderWorld(), blockpos1.above(), PathComputationType.LAND)) {
                                            validTpList.add(blockpos1.immutable());
                                        }
                                    }
                                }

                                if (!validTpList.isEmpty()) {
                                    player.stopRiding();
                                    player.fallDistance = 0.0F;
                                    int IRNG = player.getRandom().nextInt(validTpList.size());
                                    player.teleportTo((double) validTpList.get(IRNG).getX() + 0.5, validTpList.get(IRNG).getY(), (double) validTpList.get(IRNG).getZ() + 0.5);
                                    player.getPersistentData().putInt("upgraded_netherite_ender_teleport_cd", 20);
                                    SoundEvent soundevent = SoundEvents.ENDERMAN_TELEPORT;
                                    world.playSound(null, (double) validTpList.get(IRNG).getX() + 0.5, validTpList.get(IRNG).getY(), (double) validTpList.get(IRNG).getZ() + 0.5, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                                    player.playSound(soundevent, 1.0F, 1.0F);

                                    for(int i = 0; i < 32; ++i) {
                                        player.getCommandSenderWorld().addParticle(ParticleTypes.PORTAL, (double) validTpList.get(IRNG).getX() + 0.5, (double) validTpList.get(IRNG).getY() + player.getRandom().nextDouble() * 2.0, (double) validTpList.get(IRNG).getZ() + 0.5, player.getRandom().nextGaussian(), 0.0, player.getRandom().nextGaussian());
                                    }

                                }

                            }
                        }

                });
            }

        }
    }
}
}
 */

