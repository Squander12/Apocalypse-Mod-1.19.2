package me.squander.apocalypse.misc;

import net.minecraft.world.food.FoodProperties;

public class FoodInit {
    public final FoodProperties FRESH_APPLE = new FoodProperties.Builder().nutrition(4).saturationMod(0.7f).build();
    public final FoodProperties SNICKERS = new FoodProperties.Builder().nutrition(2).saturationMod(1.4f).build();
    public final FoodProperties MARS = new FoodProperties.Builder().nutrition(2).saturationMod(1f).build();
    public final FoodProperties RAW_RAT = new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build();
    public final FoodProperties COOKED_RAT = new FoodProperties.Builder().nutrition(5).saturationMod(5f).build();
}
