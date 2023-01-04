package me.squander.apocalypse.sound;

import me.squander.apocalypse.ApocalypseMod;
import me.squander.apocalypse.helper.Helper;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundEventInit {
    public static final DeferredRegister<SoundEvent> SOUND = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ApocalypseMod.MODID);

    public static RegistryObject<SoundEvent> SHOTGUN_SHOOT = register("shotgun_shoot");
    public static RegistryObject<SoundEvent> SHOTGUN_HIT = register("shotgun_hit");
    public static RegistryObject<SoundEvent> SHOTGUN_RELOAD = register("shotgun_reload");

    private static RegistryObject<SoundEvent> register(String name){
        return SOUND.register(name, () -> new SoundEvent(Helper.getRc(name)));
    }
}
