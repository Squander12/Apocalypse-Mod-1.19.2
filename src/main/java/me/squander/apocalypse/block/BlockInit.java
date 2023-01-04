package me.squander.apocalypse.block;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.item.ChestBlockItem;
import me.squander.apocalypse.item.ItemInit;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ApocalypseMod.MODID);

    public static RegistryObject<Block> MILITARY_CHEST = registerChest("military_chest", () -> new MilitaryChest(BlockBehaviour.Properties.of(Material.METAL).strength(50f, 1200f).sound(SoundType.METAL)));
    public static RegistryObject<Block> HOUSE_DOOR = register("house_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)));
    public static RegistryObject<Block> STEEL_DOOR = register("steel_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)));
    public static RegistryObject<Block> STORE_DOOR = register("store_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)));
    public static RegistryObject<Block> MEDICAL_KIT = register("medical_kit", () -> new MedicalKitBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));

    public static RegistryObject<Block> WHITE_BRICKS = register("white_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static RegistryObject<Block> WHITE_BRICK_STAIRS = register("white_brick_stairs", () -> new StairBlock(() -> WHITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BRICK_STAIRS)));
    public static RegistryObject<Block> WHITE_BRICK_SLAB = register("white_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BRICK_SLAB)));
    public static RegistryObject<Block> WHITE_BRICK_WALL = register("white_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.BRICK_WALL)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier){
        RegistryObject<T> r = BLOCKS.register(name, supplier);
        ItemInit.ITEMS.register(name, () -> new BlockItem(r.get(), ItemInit.prop()));
        return r;
    }

    private static <T extends Block> RegistryObject<T> registerChest(String name, Supplier<T> supplier){
        RegistryObject<T> r = BLOCKS.register(name, supplier);
        ItemInit.ITEMS.register(name, () -> new ChestBlockItem(r.get(), ItemInit.prop()));
        return r;
    }
}
