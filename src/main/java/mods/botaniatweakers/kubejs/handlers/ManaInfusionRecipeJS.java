package mods.botaniatweakers.kubejs.handlers;

import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.item.ingredient.IngredientJS;
import dev.latvian.kubejs.recipe.RecipeJS;
import dev.latvian.kubejs.util.ListJS;
import mods.botaniatweakers.BotaniaTweakers;
import mods.botaniatweakers.util.TweakerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import vazkii.botania.common.crafting.StateIngredientHelper;

import javax.annotation.Nullable;

public class ManaInfusionRecipeJS extends RecipeJS {
    private int mana;
    private String group;
    @Nullable
    private BlockState catalyst = null;

    @Override
    public void create(ListJS args) {
        int i = 0;

        if (args.size() > 3) {
            String hack = TweakerUtil.toString(args.get(3));
            try {
                Integer.parseInt(hack);
                this.id = TweakerUtil.toId(args.get(i));
                i++;
            } catch (NumberFormatException e) {
                // todo do a somersault
            }
        }

        ItemStackJS out = TweakerUtil.toItemStackJS(args.get(i));
        this.outputItems.add(out);
        i++;

        IngredientJS in = TweakerUtil.toIngredientJS(args.get(i));
        this.inputItems.add(in);
        i++;

        int mana = TweakerUtil.toInt(args.get(i));
        this.mana = MathHelper.clamp(mana, 0, 99999);

        if (i < args.size() - 1) {
            i++;
            String str = TweakerUtil.toString(args.get(i));
            if (!str.equals("null")) {
                this.catalyst = TweakerUtil.toState(str);
            }
        }

        if (i < args.size() - 1) {
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
