package mods.botaniatweakers.kubejs.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.item.ingredient.IngredientJS;
import dev.latvian.kubejs.recipe.RecipeJS;
import dev.latvian.kubejs.util.ListJS;
import mods.botaniatweakers.BotaniaTweakers;
import mods.botaniatweakers.util.TweakerUtil;

import java.util.List;

public class PetalRecipeJS extends RecipeJS {
    @Override
    public void create(ListJS args) {
        int i = 0;
        if (args.size() > 2) {
            this.id = TweakerUtil.toId(args.get(i));
            i++;
        }

        this.outputItems.add(TweakerUtil.toItemStackJS(args.get(i)));
        i++;

        List<IngredientJS> in = TweakerUtil.toIngredientJSList(args.get(i));
        this.inputItems.addAll(in);
    }

    @Override
    public void deserialize() {
        if (this.json.get("id") != null) {
            this.id = BotaniaTweakers.getId(this.json.get("id").getAsString());
        }
        this.outputItems.add(ItemStackJS.of(json.get("output")));
        JsonArray e = this.json.get("ingredients").getAsJsonArray();
        for (JsonElement in : e) {
            this.inputItems.add(IngredientJS.of(in));
        }
    }

    @Override
    public void serialize() {
        this.json.addProperty("type", this.type.toString());

        this.json.add("output", this.outputItems.get(0).toResultJson());

        JsonArray ingredientsJson = new JsonArray();
        for (IngredientJS in : this.inputItems) {
            ingredientsJson.add(in.toJson());
        }
        this.json.add("ingredients", ingredientsJson);
    }
}
