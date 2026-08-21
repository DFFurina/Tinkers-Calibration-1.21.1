//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.james.tinkerscalibration.library;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class LootTableModifiers extends LootModifier {
    public static final Supplier<MapCodec<LootTableModifiers>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec((inst) -> codecStart(inst).and(ResourceLocation.CODEC.fieldOf("lootTable").forGetter((m) -> m.lootTable)).apply(inst, LootTableModifiers::new)));
    private final ResourceLocation lootTable;

    protected LootTableModifiers(LootItemCondition[] conditionsIn, ResourceLocation lootTable) {
        super(conditionsIn);
        this.lootTable = lootTable;
    }

    public LootTableModifiers(ResourceLocation lootTable, LootItemCondition... conditionsIn) {
        super(conditionsIn);
        this.lootTable = lootTable;
    }

    @Nonnull
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootTable extraTable = context.getLevel().getServer().reloadableRegistries().getLootTable(ResourceKey.create(net.minecraft.core.registries.Registries.LOOT_TABLE, this.lootTable));
        Objects.requireNonNull(generatedLoot);
        extraTable.getRandomItemsRaw(context, generatedLoot::add);
        return generatedLoot;
    }

    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
