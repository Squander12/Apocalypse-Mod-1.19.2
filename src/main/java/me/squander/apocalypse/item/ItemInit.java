package me.squander.apocalypse.item;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.misc.ArmorMaterialInit;
import me.squander.apocalypse.misc.TabInit;
import me.squander.apocalypse.misc.TierInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ApocalypseMod.MODID);

    public static RegistryObject<Item> OLD_SHOTGUN = register("old_shotgun", () -> new WeaponItem(prop().durability(250)));
    public static RegistryObject<Item> CROWBAR = register("crowbar", () -> new SwordItem(TierInit.CROWBAR, 2, -2.0f, prop()));
    public static RegistryObject<Item> KATANA = register("katana", () -> new SwordItem(TierInit.KATANA, 4, -1.7f, prop()));

    public static RegistryObject<Item> RADIATION_SUIT_MASK = register("radiation_suit_mask", () -> new ArmorItemInit(ArmorMaterialInit.RADIATION_SUIT, EquipmentSlot.HEAD, prop()));
    public static RegistryObject<Item> RADIATION_SUIT_CHEST = register("radiation_suit_chest", () -> new ArmorItemInit(ArmorMaterialInit.RADIATION_SUIT, EquipmentSlot.CHEST, prop()));
    public static RegistryObject<Item> RADIATION_SUIT_LEGS = register("radiation_suit_legs", () -> new ArmorItemInit(ArmorMaterialInit.RADIATION_SUIT, EquipmentSlot.LEGS, prop()));
    public static RegistryObject<Item> RADIATION_SUIT_BOOTS = register("radiation_suit_boots", () -> new ArmorItemInit(ArmorMaterialInit.RADIATION_SUIT, EquipmentSlot.FEET, prop()));

    public static RegistryObject<Item> SHOTGUN_AMMO = registerItem("shotgun_ammo", prop());
    public static RegistryObject<Item> OLD_WORLD_MONEY = registerItem("old_world_money", prop().tab(TabInit.TRADE_TAB));
    public static RegistryObject<Item> BAG_OF_GOLD = registerItem("bag_of_gold", prop().tab(TabInit.TRADE_TAB));
    public static RegistryObject<Item> OLD_COIN = registerItem("old_coin", prop().tab(TabInit.TRADE_TAB));

    public static RegistryObject<Item> LAMPS = registerTradeItem("lamps", false);
    public static RegistryObject<Item> BLUE_LAMPS = registerTradeItem("blue_lamps", false);
    public static RegistryObject<Item> BROKEN_LAMPS = registerTradeItem("broken_lamps", false);
    public static RegistryObject<Item> AIR_FILTER = registerTradeItem("air_filter", false);
    public static RegistryObject<Item> GREEN_INTEGRATED_CIRCUIT = registerTradeItem("green_integrated_circuit", false);
    public static RegistryObject<Item> BLUE_INTEGRATED_CIRCUIT = registerTradeItem("blue_integrated_circuit", false);

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
