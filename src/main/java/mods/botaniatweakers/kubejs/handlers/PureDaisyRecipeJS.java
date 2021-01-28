package mods.botaniatweakers.kubejs.handlers;

import dev.latvian.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.kubejs.recipe.RecipeJS;
import dev.latvian.kubejs.util.ListJS;
import mods.botaniatweakers.BotaniaTweakers;
import mods.botaniatweakers.util.TweakerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.crafting.StateIngredientHelper;

public class PureDaisyRecipeJS extends RecipeJS {
    int index = 0;
    int time = 150;
    StateIngredient ing;
    BlockState out;

    @Override
    public void create(ListJS args) {
        int i = 1;

        this.out = TweakerUtil.toState(TweakerUtil.toString(args.get(i)));
        i++;

        this.ing = TweakerUtil.toStateIngredient(TweakerUtil.toString(args.get(i)));
        i++;

        this.time = TweakerUtil.toInt(args.get(i));

        if (args.size() > 3) {
            this.id = TweakerUtil.toId(args.get(0));
        } else {
            Identifier block = Registry.BLOCK.getId(out.getBlock());
            this.id = TweakerUtil.toId("pure_daisy/" + block.getPath() + "_" + index);
        }
    }

    @Override
    public void deserialize() {
        if (json.get("output").isJsonObject()) {
            this.out = TweakerUtil.toState(json, "output");
        } else {
            throw new RecipeExceptionJS("Invalid output format! Expected JsonObject! Offending recipe: " + json.toString());
        }

        if (json.get("input").isJsonObject()) {
            this.ing = StateIngredientHelper.deserialize(JsonHelper.getObject(json, "input"));
        } else {
            throw new RecipeExceptionJS("Invalid input format! Expected JsonObject! Offending recipe: " + json.toString());
        }

        this.time = JsonHelper.getInt(json, "time", this.time);

        if (this.json.get("id") != null) {
            this.id = BotaniaTweakers.getId(this.json.get("id").getAsString());
        } else {
            Identifier block = Registry.BLOCK.getId(out.getBlock());
            this.id = TweakerUtil.toId("pure_daisy/" + block.getPath() + "_" + index);
        }

    }

    @Override
    public void serialize() {
        this.json.addProperty("type", this.type.toString());

        this.json.add("output", StateIngredientHelper.serializeBlockState(out));

        this.json.add("input", ing.serialize());

        if (this.time >= 0 && this.time != 150) {
            this.json.addProperty("time", this.time);
        }

        index++;
    }
}
