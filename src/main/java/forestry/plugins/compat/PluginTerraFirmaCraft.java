package forestry.plugins.compat;

import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.FlowerManager;
import forestry.api.core.ForestryAPI;
import forestry.api.farming.Farmables;
import forestry.api.recipes.RecipeManagers;
import forestry.api.storage.BackpackManager;
import forestry.core.config.Constants;
import forestry.core.fluids.Fluids;
import forestry.core.recipes.RecipeUtil;
import forestry.core.utils.Log;
import forestry.core.utils.ModUtil;
import forestry.farming.logic.FarmableBasicFruit;
import forestry.farming.logic.FarmableGenericCrop;
import forestry.farming.logic.FarmableGenericSapling;
import forestry.plugins.ForestryPlugin;
import forestry.plugins.Plugin;
import forestry.plugins.PluginCore;
import forestry.plugins.PluginManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

@Plugin(pluginID = "TerraFirmaCraft", name = "TerraFirmaCraft", author = "Bunsan", url = Constants.URL, unlocalizedDescription = "for.plugin.terrafirmacraft.description")
public class PluginTerraFirmaCraft extends ForestryPlugin {

    private static final String TFC = "terrafirmacraft";

    private static Block sapling;
    private static Block fruitSapling;
    private static int amount;
    private static ItemStack redApple;
    private static ItemStack banana;
    private static ItemStack orange;
    private static ItemStack greenApple;
    private static ItemStack lemon;
    private static ItemStack olive;
    private static ItemStack cherry;
    private static ItemStack peach;
    private static ItemStack plum;




    @Override
    public boolean isAvailable() {
        return ModUtil.isModLoaded(TFC);
    }

    @Override
    public String getFailMessage() {
        return "TerraFirmaCraft not found";
    }

    @Override
    public void doInit() {
        super.doInit();
        sapling = GameRegistry.findBlock(TFC, "sapling");

        if (PluginManager.Module.APICULTURE.isEnabled()) {
            addFlowers();
        }
        if (PluginManager.Module.FARMING.isEnabled()) {
            addFarmCrops();
        }
    }

    @Override
    protected void registerRecipes() {

        super.registerRecipes();

        if (PluginManager.Module.FACTORY.isEnabled()) {
            addFermenterRecipes();
            addSqueezerRecipes();
        }
    }
    private static void addFarmCrops() {
        List<String> saplingItemKeys = new ArrayList<>();

        if (sapling != null) {
            saplingItemKeys.add("sapling");
        }

        for (String key : saplingItemKeys) {
            Item saplingItem = GameRegistry.findItem(TFC, key);
            String saplingName = GameData.getItemRegistry().getNameForObject(saplingItem);
            FMLInterModComms.sendMessage(Constants.MOD, "add-farmable-sapling", String.format("farmArboreal@%s.-1", saplingName));
        }
    }
    private static void addFermenterRecipes() {

        ImmutableList<String> seeds = ImmutableList.of(
                "Wheat",
                "Maize",
                "Tomato",
                "Barley",
                "Rye",
                "Oat",
                "Rice",
                "Potato",
                "Onion",
                "Cabbage",
                "Carrot",
                "Sugarcane",
                "Yellow Bell Pepper",
                "Red Bell Pepper",
                "Soybean",
                "Green Bean",
                "Squash",
                "Jute"
        );

        double saplingYield = (ForestryAPI.activeMode.getIntegerSetting("fermenter.yield.sapling") * 1.6);
        double fruitSaplingYield = (ForestryAPI.activeMode.getIntegerSetting("fermenter.yield.sapling") * 4);
        double seedYield = (ForestryAPI.activeMode.getIntegerSetting("fermenter.yield.sapling") * 4);

        Item sapling = GameRegistry.findItem(TFC, "sapling");
            if (sapling != null) {
                RecipeUtil.addFermenterRecipes(new ItemStack(sapling, 1, OreDictionary.WILDCARD_VALUE), (int)saplingYield, Fluids.BIOMASS);
            }
        Item fruitSapling = GameRegistry.findItem(TFC, "item.FruitSapling");
        if (fruitSapling != null) {
            RecipeUtil.addFermenterRecipes(new ItemStack(fruitSapling, 1, OreDictionary.WILDCARD_VALUE), (int)fruitSaplingYield, Fluids.BIOMASS);
        }
        for (String seedName : seeds) {
            ItemStack seed = GameRegistry.findItemStack(TFC, "item.Seeds" + " " + seedName, 1);
            if (seed != null) {
                RecipeUtil.addFermenterRecipes(seed, (int)seedYield, Fluids.BIOMASS);
            }
        }
    }

    private static void addSqueezerRecipes() {

        Item sand = GameRegistry.findItem(TFC, "Sand");
        if (sand != null) {
            ItemStack Sand = new ItemStack(sand, 1, OreDictionary.WILDCARD_VALUE);
                RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{PluginCore.items.phosphor.getItemStack(2),Sand}, Fluids.LAVA.getFluid(1600));
        }
        Item sand2 = GameRegistry.findItem(TFC, "Sand2");
        if (sand2 != null) {
            ItemStack Sand2 = new ItemStack(sand2, 1, OreDictionary.WILDCARD_VALUE);
            RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{PluginCore.items.phosphor.getItemStack(2),Sand2}, Fluids.LAVA.getFluid(1600));
        }
        Item dirt = GameRegistry.findItem(TFC, "Dirt");
        if (dirt != null) {
            ItemStack Dirt = new ItemStack(dirt, 1, OreDictionary.WILDCARD_VALUE);
            RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{PluginCore.items.phosphor.getItemStack(2),Dirt}, Fluids.LAVA.getFluid(1600));
        }
        Item dirt2 = GameRegistry.findItem(TFC, "Dirt2");
        if (dirt2 != null) {
            ItemStack Dirt2 = new ItemStack(dirt2, 1, OreDictionary.WILDCARD_VALUE);
            RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{PluginCore.items.phosphor.getItemStack(2),Dirt2}, Fluids.LAVA.getFluid(1600));
        }
        Item cobbleInIg = GameRegistry.findItem(TFC, "StoneIgInCobble");
        if (cobbleInIg != null) {
            ItemStack CobbleInIg = new ItemStack(cobbleInIg, 1, OreDictionary.WILDCARD_VALUE);
            RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{PluginCore.items.phosphor.getItemStack(2),CobbleInIg}, Fluids.LAVA.getFluid(1600));
        }
        Item cobbleInEx = GameRegistry.findItem(TFC, "StoneIgExCobble");
        if (cobbleInEx != null) {
            ItemStack CobbleInEx = new ItemStack(cobbleInEx, 1, OreDictionary.WILDCARD_VALUE);
            RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{PluginCore.items.phosphor.getItemStack(2),CobbleInEx}, Fluids.LAVA.getFluid(1600));
        }
        Item cobbleMM = GameRegistry.findItem(TFC, "StoneMMCobble");
        if (cobbleMM != null) {
            ItemStack CobbleMM = new ItemStack(cobbleMM, 1, OreDictionary.WILDCARD_VALUE);
            RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{PluginCore.items.phosphor.getItemStack(2),CobbleMM}, Fluids.LAVA.getFluid(1600));
        }
        Item cobbleSed = GameRegistry.findItem(TFC, "StoneSedCobble");
        if (cobbleSed != null) {
            ItemStack CobbleSed = new ItemStack(cobbleSed, 1, OreDictionary.WILDCARD_VALUE);
            RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{PluginCore.items.phosphor.getItemStack(2),CobbleSed}, Fluids.LAVA.getFluid(1600));
        }
    }

    private static void addFlowers() {
        Block flowers = GameRegistry.findBlock(TFC, "Flowers");
        if (flowers != null) {
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers, 0, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);		//Dandelion
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers, 1, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);		//Nasturtium
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers, 2, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow); 	    //Meeds Milkweed
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers, 3, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);		//TropicalMilkweed
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers, 4, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);		//Butterfly Milkweed
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers, 5, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);		//Calendula

        }

        Block flowers2 = GameRegistry.findBlock(TFC, "Flowers2");
        if (flowers2 != null) {
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers2, 0, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);		//Poppy
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers2, 1, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);	    //Blue Orchid
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers2, 2, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);		//Allium
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers2, 3, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);	    //Houstonia
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers2, 4, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);	    //Red Tulip
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers2, 5, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);	    //Orange Tulip
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers2, 6, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);	    //White Tulip
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers2, 7, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);  //Pink Tulip
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers2, 8, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);	    //Daisy
        }

        Block flowers3 = GameRegistry.findBlock(TFC, "Flora");
        if (flowers3 != null) {
            FlowerManager.flowerRegistry.registerPlantableFlower(flowers3, 0, 1.0, FlowerManager.FlowerTypeVanilla, FlowerManager.FlowerTypeSnow);      //GoldenRod
        }

        Block mushrooms = GameRegistry.findBlock(TFC, "Fungi");
        if (mushrooms != null) {
            FlowerManager.flowerRegistry.registerPlantableFlower(mushrooms, 0, 1.0, FlowerManager.FlowerTypeMushrooms);
            FlowerManager.flowerRegistry.registerPlantableFlower(mushrooms, 1, 1.0, FlowerManager.FlowerTypeMushrooms);
        }

        Block cactus = GameRegistry.findBlock(TFC, "Cactus");
        if (cactus != null) {
            FlowerManager.flowerRegistry.registerPlantableFlower(cactus, 0, 1.0, FlowerManager.FlowerTypeCacti);
        }

        Block grass = GameRegistry.findBlock(TFC, "TallGrass");
        if (grass != null) {
            FlowerManager.flowerRegistry.registerPlantableFlower(grass, 1, 1.0, FlowerManager.FlowerTypeJungle);
        }

        Block vine = GameRegistry.findBlock(TFC, "Vine");
        if (vine != null) {
            FlowerManager.flowerRegistry.registerPlantableFlower(vine, 0, 1.0, FlowerManager.FlowerTypeJungle);
        }

        Block gourd = GameRegistry.findBlock(TFC, "Pumpkin");
        if (gourd != null) {
            FlowerManager.flowerRegistry.registerPlantableFlower(vine, 0, 1.0, FlowerManager.FlowerTypeGourd);
        }

        Block crop = GameRegistry.findBlock(TFC, "Crops");
        if (crop != null) {
            FlowerManager.flowerRegistry.registerPlantableFlower(crop, 0, 1.0, FlowerManager.FlowerTypeWheat);
        }
    }
}
