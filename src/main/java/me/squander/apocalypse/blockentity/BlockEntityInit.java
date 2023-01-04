package me.squander.apocalypse.blockentity;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.block.BlockInit;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ApocalypseMod.MODID);

    public static RegistryObject<BlockEntityType<MilitaryChestBlockEntity>> MILITARY_CHEST = BLOCK_ENTITY.register("military_chest", () -> BlockEntityType.Builder.of(MilitaryChestBlockEntity::new, BlockInit.MILITARY_CHEST.get()).build(null));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> supplier, Block... blocks){
        return BLOCK_ENTITY.register(name, () -> BlockEntityType.Builder.of(supplier, blocks).build(null));
    }
}
