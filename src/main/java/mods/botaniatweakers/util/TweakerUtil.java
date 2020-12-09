package mods.botaniatweakers.util;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.latvian.kubejs.item.EmptyItemStackJS;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.item.ingredient.IngredientJS;
import dev.latvian.kubejs.item.ingredient.TagIngredientJS;
import dev.latvian.kubejs.util.ListJS;
import io.github.cottonmc.libdp.api.driver.recipe.RecipeDriver;
import io.github.cottonmc.libdp.api.driver.recipe.RecipeParser;
import mods.botaniatweakers.BotaniaTweakers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.crafting.StateIngredientBlocks;
import vazkii.botania.common.crafting.StateIngredientHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public interface TweakerUtil {
    Pattern pattern = Pattern.compile("^([*]?[a-z0-9:_.#-]+) *(\\* *[0-9]+)? *(\\{.*})?$");

    static String toString(Object o) {
        return String.valueOf(o);
    }

    static int toInt(Object o) {
        return Integer.parseInt(toString(o));
    }

    static Identifier toId(String s) {
        return s.contains(":") ? new Identifier(s) : BotaniaTweakers.getId(s);
    }

    static Identifier toId(Object o) {
        return toId(toString(o));
    }

    static Ingredient toIngredient(Object o) {
        String s = toString(o);
        boolean isTag = false;
        String itemId = "";
        String num;
        int stacksize = 1;
        CompoundTag nbt = null;
        Matcher matcher = pattern.matcher(s);
        if (matcher.find() && matcher.groupCount() > 1) {
            if (matcher.group(1) != null) {
                itemId = matcher.group(1).trim();
                if (itemId.startsWith("#")) {
                    itemId = itemId.substring(1);
                    isTag = true;
                }
            }
            if (matcher.group(2) != null) {
                num = matcher.group(2).substring(1).trim();
                stacksize = Integer.parseInt(num);
            }
            if (matcher.group(3) != null) {
                try {
                    nbt = StringNbtReader.parse(matcher.group(3));
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
            }
            if (!itemId.isEmpty()) {
                if (isTag) {
                    Tag<Item> tag = ServerTagManagerHolder.getTagManager().getItems().getTag(new Identifier(itemId));
                    if (tag != null) {
                        List<ItemStack> stacks = new ArrayList<>();
                        for (Item in : tag.values()) {
                            ItemStack stack = new ItemStack(in, stacksize);
                            if (nbt != null) {
                                stack.setTag(nbt);
                            }
                            stacks.add(stack);
                        }
                        return Ingredient.ofStacks(stacks.stream());
                    }
                } else {
                    Item item = Registry.ITEM.get(new Identifier(itemId));
                    ItemStack stack = new ItemStack(item, stacksize);
                    if (nbt != null) {
                        stack.setTag(nbt);
                    }
                    return Ingredient.ofStacks(Stream.of(stack));
                }
            }
        } else {
            try {
                return RecipeParser.processIngredient(o);
            } catch (Exception e) {
                RecipeDriver.INSTANCE.getLogger().error("Caught error processing ingredient '{}': {}", o, e.getMessage());
            }
        }
        return Ingredient.EMPTY;
    }

    static IngredientJS toIngredientJS(Object o) {
        String s = toString(o);
        boolean isTag = false;
        String itemId = "";
        String num;
        int stacksize = 1;
        CompoundTag nbt = null;
        Matcher matcher = pattern.matcher(s);
        if (matcher.find() && matcher.groupCount() > 1) {
            if (matcher.group(1) != null) {
                itemId = matcher.group(1).trim();
                if (itemId.startsWith("#")) {
                    itemId = itemId.substring(1);
                    isTag = true;
                }
            }
            if (matcher.group(2) != null) {
                num = matcher.group(2).substring(1).trim();
                stacksize = Integer.parseInt(num);
            }
            if (matcher.group(3) != null) {
                try {
                    nbt = StringNbtReader.parse(matcher.group(3));
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
            }
            if (!itemId.isEmpty()) {
                if (isTag) {
                    return TagIngredientJS.createTag(itemId).count(stacksize);
                } else {
                    Item item = Registry.ITEM.get(new Identifier(itemId));
                    ItemStack stack = new ItemStack(item, stacksize);
                    ItemStackJS sjs = ItemStackJS.of(stack);
                    if (nbt != null) {
                        sjs.nbt(nbt);
                    }
                    return sjs;
                }
            }
        } else {
            return IngredientJS.of(o);
        }
        return EmptyItemStackJS.INSTANCE;
    }

    static ItemStack toItemStack(Object o) {
        String s = toString(o);
        boolean isTag = false;
        String itemId = "";
        String num;
        int stacksize = 1;
        CompoundTag nbt = null;
        Matcher matcher = pattern.matcher(s);
        if (matcher.find() && matcher.groupCount() > 1) {
            if (matcher.group(1) != null) {
                itemId = matcher.group(1).trim();
                if (itemId.startsWith("#")) {
                    itemId = itemId.substring(1);
                    isTag = true;
                }
            }
            if (matcher.group(2) != null) {
                num = matcher.group(2).substring(1).trim();
                stacksize = Integer.parseInt(num);
            }
            if (matcher.group(3) != null) {
                try {
                    nbt = StringNbtReader.parse(matcher.group(3));
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
            }
            if (!itemId.isEmpty()) {
                if (isTag) {
                    Tag<Item> tag = ServerTagManagerHolder.getTagManager().getItems().getTag(new Identifier(itemId));
                    if (tag != null) {
                        ItemStack stack = new ItemStack(tag.values().get(0), stacksize);
                        if (nbt != null) {
                            stack.setTag(nbt);
                        }
                        return stack;
                    }
                } else {
                    Item item = Registry.ITEM.get(new Identifier(itemId));
                    ItemStack stack = new ItemStack(item, stacksize);
                    if (nbt != null) {
                        stack.setTag(nbt);
                    }
                    return stack;
                }
            }
        } else {
            try {
                return RecipeParser.processItemStack(o);
            } catch (Exception e) {
                RecipeDriver.INSTANCE.getLogger().error("Caught error processing ItemStack '{}': {}", o, e.getMessage());
            }
        }
        return ItemStack.EMPTY;
    }

    static ItemStackJS toItemStackJS(Object o) {
        return toIngredientJS(o).getFirst();
    }

    static BlockState toState(JsonObject o, String key) {
        if (o.get(key).isJsonPrimitive()) {
            String s = JsonHelper.getString(o, key);
            return toState(s);
        } else {
            return StateIngredientHelper.readBlockState(JsonHelper.getObject(o, key));
        }
    }

    static BlockState toState(String s) {
        boolean isTag = false;
        if (s.charAt(0) == '#') {
            isTag = true;
            s = s.substring(1);
        }
        Identifier catalystId = Identifier.tryParse(s);
        if (catalystId == null) {
            String error = isTag ? "Invalid Block Tag ID: " + s : "Invalid Block ID:" + s;
            throw new IllegalArgumentException(error);
        }
        if (isTag) {
            Tag<Block> tag = ServerTagManagerHolder.getTagManager().getBlocks().getTag(catalystId);
            if (tag == null) {
                throw new IllegalArgumentException("Tag \"" + s + "\" is empty!");
            }
            return tag.values().get(0).getDefaultState();
        } else {
            Optional<Block> cat = Registry.BLOCK.getOrEmpty(catalystId);
            if (!cat.isPresent()) {
                throw new IllegalArgumentException("Unknown BlockState: " + s);
            }
            return cat.get().getDefaultState();
        }
    }

    static StateIngredient toStateIngredient(String s) {
        boolean isTag = false;
        if (s.charAt(0) == '#') {
            isTag = true;
            s = s.substring(1);
        }
        Identifier catalystId = Identifier.tryParse(s);
        if (catalystId == null) {
            String error = isTag ? "Invalid Block Tag ID: " + s : "Invalid Block ID:" + s;
            throw new IllegalArgumentException(error);
        }
        if (isTag) {
            Tag<Fluid> fluidTag = ServerTagManagerHolder.getTagManager().getFluids().getTag(catalystId);
            if (fluidTag != null) {
                ImmutableSet.Builder<Block> fluids = ImmutableSet.builder();
                for (Fluid f : fluidTag.values()) {
                    fluids.add(f.getDefaultState().getBlockState().getBlock());
                }
                return StateIngredientHelper.of(fluids.build());
            }
            Tag<Block> tag = ServerTagManagerHolder.getTagManager().getBlocks().getTag(catalystId);
            if (tag == null) {
                throw new IllegalArgumentException("Tag \"" + s + "\" is empty!");
            }
            return new StateIngredientBlocks(tag.values());
        } else {
            Optional<Block> cat = Registry.BLOCK.getOrEmpty(catalystId);
            if (!cat.isPresent()) {
                throw new IllegalArgumentException("Unknown BlockState: " + s);
            }
            return StateIngredientHelper.of(cat.get());
        }
    }

    static List<IngredientJS> toIngredientJSList(Object o) {
        List<IngredientJS> list = new ArrayList<>();
        if (o instanceof JsonElement) {
            JsonArray array;
            if (o instanceof JsonArray) {
                array = ((JsonArray) o).getAsJsonArray();
            } else {
                array = new JsonArray();
                array.add((JsonElement) o);
            }

            for (JsonElement e : array) {
                list.add(toIngredientJS(e));
            }
        } else {
            for (Object obj : ListJS.orSelf(o)) {
                list.add(toIngredientJS(obj));
            }
        }

        return list;
    }

    static List<ItemStackJS> toStackJSList(Object o) {
        List<ItemStackJS> list = new ArrayList<>();
        for (IngredientJS ing : toIngredientJSList(o)) {
            list.add(ing.getFirst());
        }
        return list;
    }
}
