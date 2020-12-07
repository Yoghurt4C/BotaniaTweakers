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

public class ElvenTradeRecipeJS extends RecipeJS {
    @Override
    public void create(ListJS args) {
        int i = 0;
        if (args.size() > 2) {
            this.id = TweakerUtil.toId(args.get(i));
            i++;
        }
        List<ItemStackJS> out = TweakerUtil.toStackJSList(args.get(i));
        this.outputItems.addAll(out);
        i++;
        List<IngredientJS> in = TweakerUtil.toIngredientJSList(args.get(i));
        this.inputItems.addAll(in);
    }

    @Override
    public void deserialize() {
        if (this.json.get("id") != null) {
            this.id = BotaniaTweakers.getId(this.json.get("id").getAsString());
        }
        JsonArray a = this.json.get("output").getAsJsonArray();
        for (JsonElement out : a) {
            this.outputItems.add(ItemStackJS.of(out));
        }
        JsonArray e = this.json.get("ingredients").getAsJsonArray();
        for (JsonElement in : e) {
            this.inputItems.add(IngredientJS.of(in));
        }
    }

    @Override
    public void serialize() {
        this.json.addProperty("type", this.type.toString());
        JsonArray outJson = new JsonArray();
        for (ItemStackJS out : this.outputItems) {
            outJson.add(out.toResultJson());
        }
        this.json.add("output", outJson);
        JsonArray ingredientsJson = new JsonArray();
        for (IngredientJS in : this.inputItems) {
            ingredientsJson.add(in.toJson());
        }
        this.json.add("ingredients", ingredientsJson);
    }
}
