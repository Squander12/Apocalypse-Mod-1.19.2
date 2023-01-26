package me.squander.apocalypse.item;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.entity.EntityTypeInit;
import me.squander.apocalypse.misc.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ApocalypseMod.MODID);

    public static RegistryObject<Item> OLD_SHOTGUN = register("old_shotgun", () -> new WeaponItem(prop().durability(250)));
    public static RegistryObject<Item> CROWBAR = register("crowbar", () -> new SwordItem(TierInit.CROWBAR, 2, -2.0f, prop()));
    public static RegistryObject<Item> KATANA = register("katana", () -> new SwordItem(TierInit.KATANA, 4, -1.7f, prop()));
    public static RegistryObject<Item> FIRE_AXE = register("fire_axe", () -> new AxeItem(Tiers.NETHERITE, 5, -3.0f, prop()));

    public static RegistryObject<Item> TABLE_LEG = register("table_leg", () -> new SwordItem(TierInit.TABLE_LEG, 4, -2.4f, prop()));
    public static RegistryObject<Item> GAS_TUBE = register("gas_tube", () -> new SwordItem(TierInit.GAS_TUBE, 3, -2.4f, prop()));
    public static RegistryObject<Item> SLEDGEHAMMER = register("sledgehammer", () -> new AxeItem(TierInit.SLEDGEHAMMER, 4, -3.1f, prop()));
    public static RegistryObject<Item> POLICE_TASER = register("police_taser", () -> new SwordItem(TierInit.POLICE_TASER, 5, -2.4f, prop()));
    public static RegistryObject<Item> ELECTRIC_BATON = register("electric_baton", () -> new SwordItem(TierInit.ELECTRIC_BATON, 5, -2.4f, prop()));
    public static RegistryObject<Item> HYDRAULIC_WRENCH = register("hydraulic_wrench", () -> new SwordItem(TierInit.HYDRAULIC_WRENCH, 4, -2.4f, prop()));
    public static RegistryObject<Item> WRENCH = register("wrench", () -> new SwordItem(TierInit.WRENCH, 4, -2.4f, prop()));

    public static RegistryObject<Item> MILITARY_SWORD = register("military_sword", () -> new SwordItem(TierInit.MILITARY, 4, -2.4f, prop()));
    public static RegistryObject<Item> MILITARY_PICKAXE = register("military_pickaxe", () -> new PickaxeItem(TierInit.MILITARY, 2, -2.0f, prop()));
    public static RegistryObject<Item> MILITARY_AXE = register("military_axe", () -> new AxeItem(TierInit.MILITARY, 6, -2.7f, prop()));
    public static RegistryObject<Item> MILITARY_SHOVEL = register("military_shovel", () -> new ShovelItem(TierInit.MILITARY, 1, -3.0f, prop()));
    public static RegistryObject<Item> MILITARY_HOE = register("military_hoe", () -> new HoeItem(TierInit.MILITARY, 0, -3.0f, prop()));

    public static RegistryObject<Item> RADIATION_SUIT_MASK = register("radiation_suit_mask", () -> new ArmorItemInit(ArmorMaterialInit.RADIATION_SUIT, EquipmentSlot.HEAD, prop()));
    public static RegistryObject<Item> RADIATION_SUIT_CHEST = register("radiation_suit_chest", () -> new ArmorItemInit(ArmorMaterialInit.RADIATION_SUIT, EquipmentSlot.CHEST, prop()));
    public static RegistryObject<Item> RADIATION_SUIT_LEGS = register("radiation_suit_legs", () -> new ArmorItemInit(ArmorMaterialInit.RADIATION_SUIT, EquipmentSlot.LEGS, prop()));
    public static RegistryObject<Item> RADIATION_SUIT_BOOTS = register("radiation_suit_boots", () -> new ArmorItemInit(ArmorMaterialInit.RADIATION_SUIT, EquipmentSlot.FEET, prop()));

    public static RegistryObject<Item> MILITARY_HELMET = register("military_helmet", () -> new ArmorItemInit(ArmorMaterialInit.MILITARY_ARMOR, EquipmentSlot.HEAD, prop()));
    public static RegistryObject<Item> MILITARY_CHESTPLATE = register("military_chestplate", () -> new ArmorItemInit(ArmorMaterialInit.MILITARY_ARMOR, EquipmentSlot.CHEST, prop()));
    public static RegistryObject<Item> MILITARY_LEGGINGS = register("military_leggings", () -> new ArmorItemInit(ArmorMaterialInit.MILITARY_ARMOR, EquipmentSlot.LEGS, prop()));
    public static RegistryObject<Item> MILITARY_BOOTS = register("military_boots", () -> new ArmorItemInit(ArmorMaterialInit.MILITARY_ARMOR, EquipmentSlot.FEET, prop()));

    public static RegistryObject<Item> SHOTGUN_AMMO = registerItem("shotgun_ammo", prop());
    public static RegistryObject<Item> FRESH_APPLE = registerItem("fresh_apple", prop().food(FoodInit.FRESH_APPLE));
    public static RegistryObject<Item> RAW_RAT = registerItem("raw_rat", prop().food(FoodInit.RAW_RAT));
    public static RegistryObject<Item> COOKED_RAT = registerItem("cooked_rat", prop().food(FoodInit.COOKED_RAT));
    public static RegistryObject<Item> CHOCOLATE = registerItem("chocolate", prop().food(FoodInit.CHOCOLATE));

    public static RegistryObject<Item> CANNED_BEANS = registerItem("canned_beans", prop().food(FoodInit.CANNED_BEANS));
    public static RegistryObject<Item> CANNED_CARROTS = registerItem("canned_carrots", prop().food(FoodInit.CANNED_CARROTS));
    public static RegistryObject<Item> CANNED_CORN = registerItem("canned_corn", prop().food(FoodInit.CANNED_CORN));
    public static RegistryObject<Item> CAN_OF_DOG_FOOD = registerItem("can_of_dog_food", prop().food(FoodInit.CAN_OF_DOG_FOOD));

    public static RegistryObject<Item> RAW_LIZARD = registerItem("raw_lizard", prop().food(FoodInit.RAW_LIZARD));
    public static RegistryObject<Item> COOKED_LIZARD = registerItem("cooked_lizard", prop().food(FoodInit.COOKED_LIZARD));
    public static RegistryObject<Item> CAN_OF_LAMB = registerItem("can_of_lamb", prop().food(FoodInit.CAN_OF_LAMB));
    public static RegistryObject<Item> CAN_OF_SALTED_LAMB = registerItem("can_of_salted_lamb", prop().food(FoodInit.CAN_OF_SALTED_LAMB));

    public static RegistryObject<Item> OLD_WORLD_MONEY = registerItem("old_world_money", prop().tab(TabInit.TRADE_TAB));
    public static RegistryObject<Item> BAG_OF_GOLD = registerItem("bag_of_gold", prop().tab(TabInit.TRADE_TAB));
    public static RegistryObject<Item> OLD_COIN = registerItem("old_coin", prop().tab(TabInit.TRADE_TAB));

    public static RegistryObject<Item> POUCH = register("pouch", () -> new BackpackItem(BackpackType.POUCH, prop().stacksTo(1)));
    public static RegistryObject<Item> SMALL_BACKPACK = register("small_backpack", () -> new BackpackItem(BackpackType.SMALL_BACKPACK, prop().stacksTo(1)));
    public static RegistryObject<Item> LARGE_BACKPACK = register("large_backpack", () -> new BackpackItem(BackpackType.LARGE_BACKPACK, prop().stacksTo(1)));
    public static RegistryObject<Item> BITER_SPAWN_EGG = register("biter_spawn_egg", () -> new ForgeSpawnEggItem(EntityTypeInit.BITER, 845776, 5526612, prop()));

    public static RegistryObject<Item> LAMPS = registerTradeItem("lamps", false);
    public static RegistryObject<Item> BLUE_LAMPS = registerTradeItem("blue_lamps", false);
    public static RegistryObject<Item> BROKEN_LAMPS = registerTradeItem("broken_lamps", false);
    public static RegistryObject<Item> AIR_FILTER = registerTradeItem("air_filter", false);
    public static RegistryObject<Item> GREEN_INTEGRATED_CIRCUIT = registerTradeItem("green_integrated_circuit", false);
    public static RegistryObject<Item> BLUE_INTEGRATED_CIRCUIT = registerTradeItem("blue_integrated_circuit", false);
    public static RegistryObject<Item> BAIT = registerTradeItem("bait", false);

    public static Item.Properties prop(){
        return new Item.Properties().tab(TabInit.APOCALYPSE_TAB);
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> supplier){
        return ITEMS.register(name, supplier);
    }

    private static RegistryObject<Item> registerItem(String name, Item.Properties prop){
        return register(name, () -> new Item(prop));
    }

    private static RegistryObject<Item> registerTradeItem(String name, boolean isCraftingItem){
        return register(name, () -> new TradeItem(isCraftingItem, prop().tab(TabInit.TRADE_TAB)));
    }
}
