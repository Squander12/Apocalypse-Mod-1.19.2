package me.squander.apocalypse.helper;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.menu.BaseContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Helper {

    public static ResourceLocation getRc(String loc){
        return new ResourceLocation(ApocalypseMod.MODID, loc);
    }

    public static Component makeGuiTranslationName(String loc){
        return Component.translatable("container.apocalypse." + loc);
    }

    public static String getArmorTextureLocation(ArmorMaterial armorMaterial, boolean legs){
        String s = getRc("textures/armor/" + armorMaterial.getName() + ".png").toString();
        String s1 = getRc("textures/armor/" + armorMaterial.getName() + "_legs.png").toString();
        return legs ? s : s1;
    }

    public static ItemStack findItemInPlayerInventory(Item item, Player player){
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack f = inventory.getItem(i);
            if(item == f.getItem()){
                return f;
            }
        }
        return ItemStack.EMPTY;
    }

    public static void addInventory(BaseContainer baseContainer, Inventory inventory){
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                baseContainer.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    public static void addHotbar(BaseContainer baseContainer, Inventory inventory){
        for(int k = 0; k < 9; ++k) {
            baseContainer.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }
}
