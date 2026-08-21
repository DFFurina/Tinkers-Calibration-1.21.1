package com.james.tinkerscalibration.modifiers;


import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import com.james.tinkerscalibration.TinkersCalibration;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@EventBusSubscriber(modid = TinkersCalibration.MODID)
public class ChampingModifier extends Modifier {
    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        ToolStack tool = ToolStack.from(player.getMainHandItem());
        tool.getModifierList().forEach(modifierEntry -> {
            if (modifierEntry.getModifier() instanceof ChampingModifier) {
                if (RANDOM.nextFloat() <= 0.2f * modifierEntry.getLevel()) {
                    event.setCriticalHit(true);
                    event.setDamageMultiplier(1.5f);
                }
            }
        });
    }
}
