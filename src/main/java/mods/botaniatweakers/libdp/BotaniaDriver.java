package mods.botaniatweakers.libdp;

import com.google.gson.JsonObject;
import io.github.cottonmc.libdp.api.driver.Driver;
import io.github.cottonmc.libdp.api.driver.recipe.RecipeDriver;
import mods.botaniatweakers.util.TweakerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.IBrewContainer;
import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.crafting.*;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.ResourceLocationHelper;

import javax.annotation.Nullable;
import java.util.concurrent.Executor;

public class BotaniaDriver implements Driver {
    public static final BotaniaDriver INSTANCE = new BotaniaDriver();
    private final RecipeDriver tweaker = RecipeDriver.INSTANCE;

    @Override
    public void prepareReload(ResourceManager resourceManager) {
    }

    @Override
    public void applyReload(ResourceManager resourceManager, Executor executor) {

    }

    @Override
    public String getApplyMessage() {
        return "I LIVE";
    }

    @Override
    public JsonObject getDebugInfo() {
        return null;
    }

    private void throwError(String type, Exception e) {
        tweaker.getLogger().error("Error parsing BotaniaTweaker recipe '{}': {}", type, e);
    }

    public void addBrew(String id, String brew, Object[] inputs) {
        try {
            Identifier name = TweakerUtil.toId(id);
            Registry<Brew> reg = BotaniaAPI.instance().getBrewRegistry();
            Identifier brewId = brew.contains(":") ? new Identifier(brew) : ResourceLocationHelper.prefix(brew);
            Brew out = reg.getOrThrow(RegistryKey.of(reg.getKey(), brewId));
            Ingredient[] ingredients = new Ingredient[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                ingredients[i] = TweakerUtil.toIngredient(inputs[i]);
            }
            tweaker.addRecipe(new RecipeBrew(name, out, ingredients));
        } catch (Exception e) {
            throwError(id, e);
        }
    }

    public void addBrew(String brew, Object[] inputs) {
        try {
            ItemStack stack = new ItemStack(ModItems.flask);
            IBrewContainer vial = (IBrewContainer) ModItems.flask;
            Registry<Brew> reg = BotaniaAPI.instance().getBrewRegistry();
            Identifier brewId = brew.contains(":") ? new Identifier(brew) : ResourceLocationHelper.prefix(brew);
            Brew out = reg.getOrThrow(RegistryKey.of(reg.getKey(), brewId));
            ItemStack brewStack = vial.getItemForBrew(out, stack);
            String id = tweaker.getRecipeId(brewStack).toString();
            addBrew(id, brew, inputs);
        } catch (Exception e) {
            throwError(brew, e);
        }
    }

    public void addElvenTrade(String id, Object[] outputs, Object[] inputs) {
        try {
            Identifier name = TweakerUtil.toId(id);
            ItemStack[] out = new ItemStack[outputs.length];
            for (int i = 0; i < outputs.length; i++) {
                out[i] = TweakerUtil.toItemStack(outputs[i]);
            }
            Ingredient[] in = new Ingredient[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                in[i] = TweakerUtil.toIngredient(inputs[i]);
            }
            tweaker.addRecipe(new RecipeElvenTrade(name, out, in));
        } catch (Exception e) {
            throwError(id, e);
        }
    }

    public void addElvenTrade(Object[] outputs, Object[] inputs) {
        ItemStack out = TweakerUtil.toItemStack(outputs[0]);
        Identifier id = tweaker.getRecipeId(out);
        addElvenTrade(id.toString(), outputs, inputs);
    }

    public void addManaInfusion(String id, Object output, Object input, int mana, @Nullable String catalyst) {
        try {
            Identifier name = TweakerUtil.toId(id);
            ItemStack out = TweakerUtil.toItemStack(output);
            Ingredient in = TweakerUtil.toIngredient(input);
            BlockState cat = null;
            if (catalyst != null) {
                cat = TweakerUtil.toState(catalyst);
            }
            tweaker.addRecipe(new RecipeManaInfusion(name, out, in, MathHelper.clamp(mana, 0, 99999), null, cat));
        } catch (Exception e) {
            throwError(id, e);
        }
    }

    public void addManaInfusion(String id, Object output, Object input, int mana) {
        addManaInfusion(id, output, input, mana, null);
    }

    public void addManaInfusion(Object output, Object input, int mana, @Nullable String catalyst) {
        ItemStack out = TweakerUtil.toItemStack(output);
        Identifier id = tweaker.getRecipeId(out);
        addManaInfusion(id.toString(), output, input, mana, catalyst);
    }

    public void addManaInfusion(Object output, Object input, int mana) {
        addManaInfusion(output, input, mana, null);
    }

    public void addPetalApothecary(String id, Object output, Object[] inputs) {
        try {
            Identifier name = TweakerUtil.toId(id);
            ItemStack out = TweakerUtil.toItemStack(output);
            Ingredient[] in = new Ingredient[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                in[i] = TweakerUtil.toIngredient(inputs[i]);
            }
            tweaker.addRecipe(new RecipePetals(name, out, in));
        } catch (Exception e) {
            throwError(id, e);
        }
    }

    public void addPetalApothecary(Object output, Object[] inputs) {
        ItemStack out = TweakerUtil.toItemStack(output);
        Identifier id = tweaker.getRecipeId(out);
        addPetalApothecary(id.toString(), output, inputs);
    }

    public void addPureDaisy(String id, String output, String input, int time) {
        try {
            Identifier name = TweakerUtil.toId(id);
            BlockState out = TweakerUtil.toState(output);
            StateIngredient in = TweakerUtil.toStateIngredient(input);
            tweaker.addRecipe(new RecipePureDaisy(name, in, out, Math.max(0, time)));
        } catch (Exception e) {
            throwError(id, e);
        }
    }

    public void addPureDaisy(String id, String output, String input) {
        addPureDaisy(id, output, input, 150);
    }

    public void addPureDaisy(String output, String input, int time) {
        ItemStack out = TweakerUtil.toItemStack(output);
        Identifier id = tweaker.getRecipeId(out);
        addPureDaisy(id.toString(), output, input, time);
    }

    public void addPureDaisy(String output, String input) {
        addPureDaisy(output, input, 150);
    }

    public void addRuneAltar(String id, Object output, Object[] inputs, int mana) {
        try {
            Identifier name = TweakerUtil.toId(id);
            ItemStack out = TweakerUtil.toItemStack(output);
            Ingredient[] in = new Ingredient[inputs.length];
            for (int i = 0; i < inputs.length && i <= 16; i++) {
                in[i] = TweakerUtil.toIngredient(inputs[i]);
            }
            tweaker.addRecipe(new RecipeRuneAltar(name, out, MathHelper.clamp(mana, 0, 100000), in));
        } catch (Exception e) {
            throwError(id, e);
        }
    }

    public void addRuneAltar(Object output, Object[] inputs, int mana) {
        ItemStack out = TweakerUtil.toItemStack(output);
        Identifier id = tweaker.getRecipeId(out);
        addRuneAltar(id.toString(), output, inputs, mana);
    }

    public void addTerraPlate(String id, Object output, Object[] inputs, int mana) {
        try {
            Identifier name = TweakerUtil.toId(id);
            ItemStack out = TweakerUtil.toItemStack(output);
            Ingredient[] in = new Ingredient[inputs.length];
            for (int i = 0; i < inputs.length && i <= 16; i++) {
                in[i] = TweakerUtil.toIngredient(inputs[i]);
            }
            tweaker.addRecipe(new RecipeTerraPlate(name, Math.max(mana, 0), DefaultedList.copyOf(Ingredient.EMPTY, in), out));
        } catch (Exception e) {
            throwError(id, e);
        }
    }

    public void addTerraPlate(Object output, Object[] inputs, int mana) {
        ItemStack out = TweakerUtil.toItemStack(output);
        Identifier id = tweaker.getRecipeId(out);
        addTerraPlate(id.toString(), output, inputs, mana);
    }

    public void registerOreWeight(String ore, int weight, boolean isIgnem) {
        if (!ore.contains(":")) ore = "c:" + ore;
        else if (ore.charAt(0) == '#') ore = ore.substring(1);
        Identifier id = TweakerUtil.toId(ore);
        Tag<Block> tag = ServerTagManagerHolder.getTagManager().getBlocks().getTag(id);
        if (tag == null) {
            tweaker.getLogger().error("Error adding Orechid Ore Weight for tag '{}': It doesn't exist!", ore);
        } else {
            if (isIgnem) {
                BotaniaAPI.instance().registerNetherOreWeight(id, weight);
            } else {
                BotaniaAPI.instance().registerOreWeight(id, weight);
            }
        }
    }

    public void addOrechidOre(String ore, int weight) {
        registerOreWeight(ore, weight, false);
    }

    public void addOrechidIgnemOre(String ore, int weight) {
        registerOreWeight(ore, weight, true);
    }
}
