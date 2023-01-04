package me.squander.apocalypse.misc;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;

public class TierInit {
    public static final ForgeTier CROWBAR = new ForgeTier(0, 100, 5, 0.0f, 2, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
    public static final ForgeTier KATANA = new ForgeTier(0, 350, 5, 10.0f, 10, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
}
