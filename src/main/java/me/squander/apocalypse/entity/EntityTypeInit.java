package me.squander.apocalypse.entity;

import me.squander.apocalypse.ApocalypseMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ApocalypseMod.MODID);

    public static RegistryObject<EntityType<Bullet>> BULLET = register("bullet", EntityType.Builder.<Bullet>of(Bullet::new, MobCategory.MISC).sized(0.3F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static RegistryObject<EntityType<Biter>> BITER = register("biter", EntityType.Builder.<Biter>of(Biter::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPE.register(name, () -> builder.build(name));
    }
}
