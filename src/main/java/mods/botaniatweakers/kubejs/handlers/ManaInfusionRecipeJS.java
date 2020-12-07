package mods.botaniatweakers.kubejs.handlers;

import com.google.gson.JsonPrimitive;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.item.ingredient.IngredientJS;
import dev.latvian.kubejs.recipe.RecipeJS;
import dev.latvian.kubejs.util.ListJS;
import mods.botaniatweakers.BotaniaTweakers;
import mods.botaniatweakers.util.TweakerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import vazkii.botania.common.crafting.StateIngredientHelper;

import javax.annotation.Nullable;
import java.util.Optional;

public class ManaInfusionRecipeJS extends RecipeJS {
    private int mana;
    private String group;
    @Nullable
    private BlockState catalyst = null;

    @Override
    public void create(ListJS args) {
        int i = 0;
        if (args.size() > 4) {
            this.id = TweakerUtil.toId(args.get(i));
            i++;
        }
        ItemStackJS out = TweakerUtil.toItemStackJS(args.get(i));
        this.outputItems.add(out);
        i++;

        IngredientJS in = TweakerUtil.toIngredientJS(args.get(i));
        this.inputItems.add(in);
        i++;

        int mana = TweakerUtil.toInt(args.get(i));
        this.mana = MathHelper.clamp(mana, 0, 99999);
        i++;

        Identifier cat = new Identifier(TweakerUtil.toString(args.get(i)));
        Optional<Block> block = Registry.BLOCK.getOrEmpty(cat);
        block.ifPresent(v -> this.catalyst = v.getDefaultState());

        if (args.size() > 5) {
            i++;
            this.group = TweakerUtil.toString(args.get(i));
        }
    }

    @Override
    public void deserialize() {
        if (JsonHelper.hasString(json, "id"))
            this.id = BotaniaTweakers.getId(JsonHelper.getString(json, "id"));
        this.outputItems.add(ItemStackJS.of(this.json.get("output")));
        this.inputItems.add(IngredientJS.ingredientFromRecipeJson(this.json.get("input")));
        this.mana = JsonHelper.getInt(json, "mana");
        if (json.has("catalyst"))
            this.catalyst = TweakerUtil.toState(json, "catalyst");
        if (JsonHelper.hasString(json, "group"))
            this.group = JsonHelper.getString(json, "group");
    }

    @Override
    public void serialize() {
        this.json.addProperty("type", this.type.toString());
        this.json.add("output", this.outputItems.get(0).toResultJson());
        this.json.add("input", this.inputItems.get(0).toJson());
        this.json.addProperty("mana", MathHelper.clamp(this.mana, 0, 99999));
        if (this.catalyst != null)
            this.json.add("catalyst", StateIngredientHelper.serializeBlockState(this.catalyst));
        if (this.group != null)
            this.json.addProperty("group", this.group);
    }
}
