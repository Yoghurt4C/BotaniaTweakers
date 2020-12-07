package mods.botaniatweakers.kubejs;

import dev.latvian.kubejs.KubeJSInitializer;
import dev.latvian.kubejs.recipe.RecipeJS;
import dev.latvian.kubejs.recipe.RecipeTypeJS;
import dev.latvian.kubejs.recipe.RegisterRecipeHandlersEvent;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import mods.botaniatweakers.kubejs.handlers.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Util;
import vazkii.botania.common.crafting.ModRecipeTypes;

import java.util.Map;
import java.util.function.Supplier;

public class BotaniaRecipeEventHandler implements KubeJSInitializer {
    @Override
    public void onKubeJSInitialization() {
        Map<RecipeSerializer<?>, Supplier<RecipeJS>> map = Util.make(new Object2ObjectArrayMap<>(), m -> {
            m.put(ModRecipeTypes.BREW_SERIALIZER, BrewRecipeJS::new);
            m.put(ModRecipeTypes.ELVEN_TRADE_SERIALIZER, ElvenTradeRecipeJS::new);
            m.put(ModRecipeTypes.MANA_INFUSION_SERIALIZER, ManaInfusionRecipeJS::new);
            m.put(ModRecipeTypes.PETAL_SERIALIZER, PetalRecipeJS::new);
            m.put(ModRecipeTypes.PURE_DAISY_SERIALIZER, PureDaisyRecipeJS::new);
            m.put(ModRecipeTypes.RUNE_SERIALIZER, RuneAltarRecipeJS::new);
            m.put(ModRecipeTypes.TERRA_PLATE_SERIALIZER, TerraPlateRecipeJS::new);
        });

        RegisterRecipeHandlersEvent.EVENT.register(event -> map.forEach((k, v) -> event.register(new RecipeTypeJS(k, v))));
    }
}
