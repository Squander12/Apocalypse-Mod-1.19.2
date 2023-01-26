package me.squander.apocalypse.helper;

import com.google.common.collect.Lists;
import me.squander.apocalypse.ApocalypseMod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class Helper {

    public static ResourceLocation getRc(String loc){
        return new ResourceLocation(ApocalypseMod.MODID, loc);
    }

    public static MutableComponent makeGuiTranslationName(String loc){
        return Component.translatable("container.apocalypse." + loc);
    }

    public static String getArmorTextureLocation(ArmorMaterial armorMaterial, boolean legs){
        String s = getRc("textures/armor/" + armorMaterial.getName() + ".png").toString();
        String s1 = getRc("textures/armor/" + armorMaterial.getName() + "_legs.png").toString();
        return legs ? s1 : s;
    }

    public static double roundToDecPlace(double value, int precision){
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static ItemStack findItemInContainer(Item item, IItemHandler container){
        for (int i = 0; i < container.getSlots(); i++) {
            ItemStack f = container.getStackInSlot(i);
            if(item == f.getItem()){
                return f;
            }
        }
        return ItemStack.EMPTY;
    }

    public static void createFirework(Level level, BlockPos pos, int power, FireworkRocketItem.Shape shape, DyeColor color){
        ItemStack stack = new ItemStack(Items.FIREWORK_ROCKET);
        CompoundTag mainTag = stack.getOrCreateTagElement("Fireworks");
        CompoundTag subTag = new CompoundTag();
        ListTag listTag = new ListTag();
        List<Integer> colors = Lists.newArrayList();

        colors.add(color.getFireworkColor());
        mainTag.putByte("Flight", (byte) power <= 0 ? 1 : (byte) power);
        subTag.putIntArray("Colors", colors);
        subTag.putByte("Type", (byte) shape.getId());
        listTag.add(subTag);

        mainTag.put("Explosions", listTag);

        FireworkRocketEntity fireworkRocket = new FireworkRocketEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        level.addFreshEntity(fireworkRocket);
    }
}
