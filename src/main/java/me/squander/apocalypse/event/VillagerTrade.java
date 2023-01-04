package me.squander.apocalypse.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.item.ItemInit;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ApocalypseMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VillagerTrade {

    @SubscribeEvent
    public static void onRegisterTrades(VillagerTradesEvent event){
        VillagerProfession profession = event.getType();
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        removeVanillaTrades(trades);

        if(profession == VillagerProfession.BUTCHER){
            addTrade(1, new BasicItemListing(money(20, MoneyType.COIN), result(Items.APPLE, 10), 16, 3, 0.3f), trades);
        }
    }

    public static void addTrade(int level, ItemListing items, Int2ObjectMap<List<VillagerTrades.ItemListing>> trades){
        int i = level > 0 && level < 6 ? level : 1;
        trades.get(i).add(items);
    }

    public static ItemStack money(int count, MoneyType type){
        return switch (type){
            case COIN -> new ItemStack(ItemInit.OLD_COIN.get(), count);
            case MONEY -> new ItemStack(ItemInit.OLD_WORLD_MONEY.get(), count);
            case GOLD -> new ItemStack(ItemInit.BAG_OF_GOLD.get(), count);
        };
    }

    public static ItemStack result(Item item, int count){
        return new ItemStack(item, count);
    }

    public static void removeVanillaTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades){
        for (int i = 1; i < 6; i++) {
            trades.get(i).clear();
        }
    }

    public enum MoneyType{
        COIN,
        MONEY,
        GOLD
    }
}
