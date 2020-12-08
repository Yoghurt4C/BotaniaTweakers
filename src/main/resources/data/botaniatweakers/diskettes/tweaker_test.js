log.warn("WARNING! The BotaniaTweaker tweaker/driver sample script is running!");
log.warn("If you're seeing this and aren't in dev mode, please report it!");

var BT = diskette.require("botaniatweaker.BotaniaDriver");

// potato + tiny potato -> brew of haste
BT.addBrew("scronch", "haste", ["minecraft:potato", "botania:tiny_potato"])
// basically just a no-id test
BT.addBrew("overload", ["minecraft:carrot", "minecraft:golden_carrot", "minecraft:golden_apple"])

// 2 pieces of charcoal -> 4 pieces of tagged coal using custom syntax
BT.addElvenTrade("gronch", ["minecraft:coal * 4 {tagged:1b}"], ["minecraft:charcoal", "minecraft:charcoal"])
// 2 pieces of coal -> 2 pieces of tagged charcoal using libdp syntax
BT.addElvenTrade(["minecraft:charcoal{tagged:1b}@2"], ["minecraft:coal", "minecraft:coal"])

// iron ore -> tagged iron ingot using a blast furnace as state catalyst
BT.addManaInfusion("croog", "minecraft:iron_ingot{epic:1b}", "minecraft:iron_ore", 200, "minecraft:blast_furnace")
// gold ore pentuplication. balanced!
BT.addManaInfusion("minecraft:gold_ingot*5", "minecraft:gold_ore", 99999)

// melone + slime -> two cacti
BT.addPetalApothecary("freb", "minecraft:cactus@2", ["minecraft:melon_slice", "minecraft:slime_ball"])
// stone + flint -> cobblestone
BT.addPetalApothecary("minecraft:cobblestone", ["minecraft:stone", "minecraft:flint"])

// tagged lava -> obsidian
BT.addPureDaisy("ploomb", "minecraft:obsidian", "#minecraft:lava", 10)
// tagged planks -> oak logs
BT.addPureDaisy("minecraft:oak_log", "#minecraft:planks")

// string + diamond = 25 of the first item from the "wool" tag
BT.addRuneAltar("qbote", "#minecraft:wool*25", ["minecraft:string", "minecraft:diamond"], 100)
// 4 coal -> diamond
BT.addRuneAltar("minecraft:diamond", ["minecraft:coal", "minecraft:coal", "minecraft:coal", "minecraft:coal"], 2000)

// it him
BT.addTerraPlate("jfmsu", "minecraft:dirt", ["minecraft:diamond", "minecraft:diamond"], 1)
// he has no style
BT.addTerraPlate("#minecraft:coals", ["#minecraft:leaves", "#minecraft:beds"], 10)

// adds tag 'c:copper_ores` into the Orechid ore weight registry. the '#' is unnecessary here
// BT.addOrechidOre("#c:copper_ores", 9316)
// adds tag 'c:nether_copper_ores' into the Orechid Ignem ore weight registry. lack of an identifier automatically prepends 'c:' to the id
// BT.addOrechidIgnemOre("nether_copper_ores", 3861)