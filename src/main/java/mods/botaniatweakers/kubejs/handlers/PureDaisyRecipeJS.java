package mods.botaniatweakers.kubejs.handlers;

import com.google.common.collect.ImmutableSet;
import dev.latvian.kubejs.item.ingredient.IngredientJS;
import dev.latvian.kubejs.recipe.RecipeJS;
import dev.latvian.kubejs.util.ListJS;
import mods.botaniatweakers.BotaniaTweakers;
import mods.botaniatweakers.util.TweakerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.JsonHelper;
import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.crafting.StateIngredientHelper;

public class PureDaisyRecipeJS extends RecipeJS {
    int time = 150;

    @Override
    public void create(ListJS args) {
        int i = 0;
        if (args.size() > 3) {
            this.id = TweakerUtil.toId(args.get(i));
            i++;
        }

        this.outputItems.add(TweakerUtil.toItemStackJS(args.get(i)));
        i++;

        this.inputItems.add(TweakerUtil.toItemStackJS(args.get(i)));
        i++;

        this.time = TweakerUtil.toInt(args.get(i));
    }

    @Override
    public void deserialize() {
        if (this.json.get("id") != null) {
            this.id = BotaniaTweakers.getId(this.json.get("id").getAsString());
        }

        if (json.get("output").isJsonObject()) {
            this.outputItems.add(IngredientJS.of(TweakerUtil.toState(json, "output").getBlock()).getFirst());
        } else {
            this.outputItems.add(IngredientJS.of(json.get("output")).getFirst());
        }

        if (json.get("input").isJsonObject()) {
            StateIngredient in = StateIngredientHelper.deserialize(JsonHelper.getObject(json, "input"));
            for (BlockState state : in.getDisplayed()) {
                this.inputItems.add(IngredientJS.of(state.getBlock()));
            }
        } else {
            this.inputItems.add(IngredientJS.of(json.get("input")));
        }
        this.time = JsonHelper.getInt(json, "time", this.time);
    }

    @Override
    public void serialize() {
        this.json.addProperty("type", this.type.toString());

        BlockState out = Block.getBlockFromItem(this.outputItems.get(0).getItem()).getDefaultState();
        this.json.add("output", StateIngredientHelper.serializeBlockState(out));

        ImmutableSet.Builder<Block> blocks = ImmutableSet.builder();
        for (IngredientJS in : this.inputItems) {
            in.getStacks().forEach(stack -> blocks.add(Block.getBlockFromItem(stack.getItem())));
        }
        StateIngredient in = StateIngredientHelper.of(blocks.build());
        this.json.add("input", in.serialize());

        if (this.time >= 0 && this.time != 150) {
            this.json.addProperty("time", this.time);
        }
    }
}
