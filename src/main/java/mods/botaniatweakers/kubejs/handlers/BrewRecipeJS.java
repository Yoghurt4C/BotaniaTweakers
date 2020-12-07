package mods.botaniatweakers.kubejs.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.latvian.kubejs.item.ingredient.IngredientJS;
import dev.latvian.kubejs.recipe.RecipeJS;
import dev.latvian.kubejs.util.ListJS;
import mods.botaniatweakers.BotaniaTweakers;
import mods.botaniatweakers.util.TweakerUtil;
import net.minecraft.util.Identifier;
import vazkii.botania.common.lib.ResourceLocationHelper;

import java.util.List;

public class BrewRecipeJS extends RecipeJS {
    private String brewName;
    private Identifier type = ResourceLocationHelper.prefix("brew");

    @Override
    public void create(ListJS args) {
        int i = 0;
        if (args.size() > 2) {
            this.id = TweakerUtil.toId(args.get(i));
            i++;
        }
        this.brewName = TweakerUtil.toString(args.get(i));
        i++;

        List<IngredientJS> inputs = TweakerUtil.toIngredientJSList(args.get(i));
        this.inputItems.addAll(inputs);
    }

    @Override
    public void deserialize() {
        if (this.json.get("id") != null) {
            this.id = BotaniaTweakers.getId(this.json.get("id").getAsString());
        }
        this.brewName = this.json.get("brew").getAsString();
        JsonArray e = this.json.get("ingredients").getAsJsonArray();
        for (JsonElement in : e) {
            this.inputItems.add(IngredientJS.of(in));
        }
    }

    @Override
    public void serialize() {
        this.json.addProperty("type", this.type.toString());
        this.json.addProperty("brew", brewName);
        JsonArray ingredientsJson = new JsonArray();

        for (IngredientJS in : this.inputItems) {
            ingredientsJson.add(in.toJson());
        }
        this.json.add("ingredients", ingredientsJson);
    }
}
