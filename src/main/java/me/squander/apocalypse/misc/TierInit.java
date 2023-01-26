package me.squander.apocalypse.misc;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;

public class TierInit {
    public static final ForgeTier CROWBAR = new ForgeTier(1, 100, 2.0f, 0.0f, 2, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
    public static final ForgeTier KATANA = new ForgeTier(1, 350, 2.0f, 10.0f, 10, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
    public static final ForgeTier MILITARY = new ForgeTier(10, 2431, 12.5f, 7.0f, 17, Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.EMPTY);

    public static final ForgeTier TABLE_LEG = new ForgeTier(1, 160, 2f, 2.0f, 5, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
    public static final ForgeTier GAS_TUBE = new ForgeTier(1, 180, 2f, 2.0f, 5, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
    public static final ForgeTier SLEDGEHAMMER = new ForgeTier(1, 200, 2f, 7.0f, 5, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
    public static final ForgeTier POLICE_TASER = new ForgeTier(1, 175, 2f, 2.0f, 5, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
    public static final ForgeTier ELECTRIC_BATON = new ForgeTier(1, 380, 5f, 2.0f, 5, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
    public static final ForgeTier HYDRAULIC_WRENCH = new ForgeTier(1, 220, 2f, 2.0f, 5, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
    public static final ForgeTier WRENCH = new ForgeTier(1, 180, 2f, 2.0f, 5, Tags.Blocks.NEEDS_WOOD_TOOL, () -> Ingredient.EMPTY);
}
