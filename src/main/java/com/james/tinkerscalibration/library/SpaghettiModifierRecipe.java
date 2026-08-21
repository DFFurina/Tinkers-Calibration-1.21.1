package com.james.tinkerscalibration.library;

import com.james.tinkerscalibration.TinkersCalibration;
import com.james.tinkerscalibration.Utils;
import com.james.tinkerscalibration.contents.TinkersCalibrationItems;
import com.james.tinkerscalibration.modifiers.SpaghettiModifier;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.common.IngredientLoadable;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.util.RegistryHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.recipe.RecipeResult;
import slimeknights.tconstruct.library.recipe.modifiers.ModifierRecipeLookup;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IncrementalModifierRecipe;
import slimeknights.tconstruct.library.recipe.tinkerstation.IMutableTinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationContainer;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationRecipe;
import slimeknights.tconstruct.library.tools.nbt.LazyToolStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe.withModifiers;

public class SpaghettiModifierRecipe implements ITinkerStationRecipe, IDisplayModifierRecipe {
  public static final RecordLoadable<SpaghettiModifierRecipe> LOADER = RecordLoadable.create(
    ContextKey.ID.requiredField(),
    IngredientLoadable.DISALLOW_EMPTY.requiredField("ingredient", r -> r.ingredient),
    IntLoadable.FROM_ONE.requiredField("restore_amount", r -> r.restoreAmount),
    SpaghettiModifierRecipe::new);


  private final ResourceLocation id;
  private final Ingredient ingredient;
  private final int restoreAmount;

  public SpaghettiModifierRecipe(ResourceLocation id, Ingredient ingredient, int restoreAmount) {
    this.id = id;
    this.ingredient = ingredient;
    this.restoreAmount = restoreAmount;
    ModifierRecipeLookup.addRecipeModifier(null, Utils.spaghetti);
  }
  private final ResourceLocation KEY = ResourceLocation.fromNamespaceAndPath(TinkersCalibration.MODID, "spaghetti");

  @Override
  public boolean matches(ITinkerStationContainer inv, Level world) {
    if (!inv.getTinkerableStack().is(TinkersCalibrationItems.SPAGHETTI.get())) {
      return false;
    }
    // must find at least one slime, but multiple is fine, as is empty slots
    return IncrementalModifierRecipe.containsOnlyIngredient(inv, ingredient);
  }


  @Override
  public @NotNull RecipeResult<LazyToolStack> getValidatedResult(ITinkerStationContainer inv, RegistryAccess access) {
    ToolStack tool = inv.getTinkerable();
    SpaghettiModifier spaghetti = (SpaghettiModifier) Utils.spaghetti.get();
    ModifierId spaghettiId = Utils.spaghetti.getId();
    ModifierEntry entry = tool.getModifier(spaghettiId);
    if (tool.getModifiers().getLevel(spaghettiId) > 0) {

      tool = tool.copy();
    } else {
      return RecipeResult.pass();
    }
    int available = IncrementalModifierRecipe.getAvailableAmount(inv, ingredient, restoreAmount);
    if(available > 640 && available < 1000 && entry.getLevel() == 1)
    {
      tool.addModifier(spaghettiId, 1);
      tool.addModifier(Utils.spaghetti2.getId(), 1);
      tool.getPersistentData().putInt(KEY, 100);
    }
    else if(available >= 1000 && entry.getLevel() == 2)
    {
      tool.addModifier(spaghettiId, 1);
      tool.addModifier(Utils.spaghetti3.getId(), 1);
      tool.getPersistentData().putInt(KEY, 100);
    }
    else if(available > 640 && available < 1000 && entry.getLevel() == 2)
    {
      return RecipeResult.pass();
    }
    else if(available >= 1000 && entry.getLevel() == 3)
    {
      return RecipeResult.pass();
    }
    else if(available > 640 && available < 1000 && entry.getLevel() == 3)
    {
      return RecipeResult.pass();
    }
    else {
      if (entry.getLevel() > 0 && spaghetti.getUses(tool) >= 100) {
        return RecipeResult.pass();
      }
      tool.getPersistentData().putInt(KEY, Math.min(available + spaghetti.getUses(tool), 100));
    }
    return ITinkerStationRecipe.success(tool, inv);
  }

  @Override
  public void updateInputs(LazyToolStack result, IMutableTinkerStationContainer inv, boolean isServer) {
    ToolStack tool = inv.getTinkerable();
    int current = 0;
    SpaghettiModifier spaghetti = (SpaghettiModifier) Utils.spaghetti.get();
    if (tool.getModifierLevel(spaghetti) != 0) {
      current = spaghetti.getUses(tool);
    }
    if(result.getTool().getModifierLevel(spaghetti.getId()) > tool.getModifierLevel(spaghetti)) {
      IncrementalModifierRecipe.updateInputs(inv, ingredient, 1, restoreAmount, ItemStack.EMPTY);
    }
    int maxNeeded = spaghetti.getUses(result.getTool()) - current;
    IncrementalModifierRecipe.updateInputs(inv, ingredient, maxNeeded, restoreAmount, ItemStack.EMPTY);
  }


  public ResourceLocation getId() {
    return this.id;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return Utils.spaghettiSerializer.get();
  }
  private static final ModifierEntry RESULT = new ModifierEntry(Utils.spaghetti, 1);
  /** Cache of input and output tools for display */
  private List<ItemStack> toolWithoutModifier, toolWithModifier = null;

  @Override
  public int getInputCount() {
    return 1;
  }

  @Override
  public List<ItemStack> getDisplayItems(int slot) {
    if (slot == 0) {
      return Arrays.asList(ingredient.getItems());
    }
    return Collections.emptyList();
  }
  @Override
  public List<ItemStack> getToolWithoutModifier() {
    if (toolWithoutModifier == null) {
      toolWithoutModifier = RegistryHelper.getTagValueStream(BuiltInRegistries.ITEM, TinkerTags.Items.DURABILITY).map(MAP_TOOL_FOR_RENDERING).toList();
    }
    return toolWithoutModifier;
  }

  @Override
  public List<ItemStack> getToolWithModifier() {
    if (toolWithModifier == null) {
      List<ModifierEntry> result = List.of(RESULT);
      toolWithModifier = RegistryHelper.getTagValueStream(BuiltInRegistries.ITEM, TinkerTags.Items.DURABILITY)
                                       .map(MAP_TOOL_FOR_RENDERING)
                                       .map(stack -> withModifiers(stack, result, data -> data.putInt(KEY, restoreAmount)))
                                       .toList();
    }
    return toolWithModifier;
  }

  @Override
  public ModifierEntry getDisplayResult() {
    return RESULT;
  }
}