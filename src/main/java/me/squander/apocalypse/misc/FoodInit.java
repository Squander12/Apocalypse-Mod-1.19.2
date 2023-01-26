package me.squander.apocalypse.misc;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodInit {
    public static final FoodProperties FRESH_APPLE = new FoodProperties.Builder().nutrition(4).saturationMod(0.7f).build();
    public static final FoodProperties CHOCOLATE = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build();
    public static final FoodProperties RAW_RAT = new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build();
    public static final FoodProperties COOKED_RAT = new FoodProperties.Builder().nutrition(5).saturationMod(0.5f).build();

    public static final FoodProperties CANNED_BEANS = new FoodProperties.Builder().nutrition(7).saturationMod(1.2f).build();
    public static final FoodProperties CANNED_CARROTS = new FoodProperties.Builder().nutrition(6).saturationMod(0.9f).build();
    public static final FoodProperties CANNED_CORN = new FoodProperties.Builder().nutrition(6).saturationMod(0.7f).build();
    public static final FoodProperties CAN_OF_DOG_FOOD = new FoodProperties.Builder().nutrition(8).saturationMod(0.2f).meat().effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200, 1), 0.5f).build();

    public static final FoodProperties RAW_LIZARD = new FoodProperties.Builder().nutrition(3).saturationMod(0.4f).meat().build();
    public static final FoodProperties COOKED_LIZARD = new FoodProperties.Builder().nutrition(6).saturationMod(0.8f).meat().build();
    public static final FoodProperties CAN_OF_LAMB = new FoodProperties.Builder().nutrition(7).saturationMod(1.1f).meat().build();
    public static final FoodProperties CAN_OF_SALTED_LAMB = new FoodProperties.Builder().nutrition(5).saturationMod(0.9f).meat().build();
}
