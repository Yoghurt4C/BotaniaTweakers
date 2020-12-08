// this is an example of what a valid KubeJS tweaker file could look like.
// change shouldLoad to true to witness it in action.
var shouldLoad = true

if (shouldLoad) {
settings.logAddedRecipes = true
settings.logRemovedRecipes = true
settings.logSkippedRecipes = false
settings.logErroringRecipes = true

events.listen('recipes', event => {

    event.recipes.botania.brew({
        id: 'rot_test',
        brew: 'botania:absorption',
        ingredients: [
            'minecraft:apple', 'minecraft:iron_ingot', 'minecraft:rotten_flesh'
        ]
    })
    event.recipes.botania.brew('grot_test', 'botania:absorption', ['minecraft:golden_apple * 5', 'minecraft:iron_ingot * 2 {tag:1b}', 'minecraft:rotten_flesh'])

    event.recipes.botania.elven_trade({
        id: 'scrot_test',
        output: [
            'minecraft:apple'
        ],
        ingredients: [
            'minecraft:carrot', 'minecraft:carrot'
        ]
    })
    event.recipes.botania.elven_trade('scrunt_test', ['botania:tiny_potato * 4 {display: {Name: \'{"text":"pahimar"}\'}}'], ['minecraft:potato', 'minecraft:potato'])

    event.recipes.botania.mana_infusion({
        id: 'coom',
        output: 'minecraft:gold_block',
        input: 'minecraft:brick',
        mana: 500,
        catalyst: 'minecraft:bricks'
    })
    event.recipes.botania.mana_infusion('coom2', 'minecraft:diamond_block', 'minecraft:brick', 1000, 'minecraft:gold_block', 'botania:flower_cycle')
    event.recipes.botania.mana_infusion('coom3', 'minecraft:diamond_block', 'minecraft:brick', 1000, 'minecraft:gold_block')
    event.recipes.botania.mana_infusion('minecraft:diamond_block', 'minecraft:brick', 1000, 'minecraft:gold_block')
    event.recipes.botania.mana_infusion('minecraft:diamond_block', 'minecraft:brick', 1000, null)

    event.recipes.botania.petal_apothecary({
        id: 'vroom',
        output: 'minecraft:clay_ball',
        ingredients: ['minecraft:iron_ingot', 'minecraft:gold_ingot']
    })
    event.recipes.botania.petal_apothecary('gloom', 'minecraft:diamond', ['minecraft:clay_ball', 'minecraft:clay_ball'])

    event.recipes.botania.pure_daisy({
        id: 'woomy',
        output: 'minecraft:coarse_dirt',
        input: 'minecraft:coal_ore',
        time: 20
    })
    event.recipes.botania.pure_daisy('yoom', 'minecraft:gold_ore', 'minecraft:bricks', 80)

    event.recipes.botania.runic_altar({
        id: 'roony',
        output: 'minecraft:clay_ball',
        ingredients: ['minecraft:feather', 'minecraft:feather'],
        mana: 3000
    })
    event.recipes.botania.runic_altar('loomy', 'minecraft:feather', ['minecraft:clay_ball','minecraft:clay_ball'], 500)

    event.recipes.botania.terra_plate({
        id: 'all_the_mana_is_gone',
        result: 'minecraft:clay_ball',
        ingredients: ['minecraft:diamond','botania:tiny_potato'],
        mana: 1500000
    })
    event.recipes.botania.terra_plate('awful', 'minecraft:golden_apple*10', ['minecraft:potato','minecraft:golden_carrot'], 1500)
})
}