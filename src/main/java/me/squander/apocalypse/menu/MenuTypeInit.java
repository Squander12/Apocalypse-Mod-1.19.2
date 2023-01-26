package me.squander.apocalypse.menu;

import me.squander.apocalypse.ApocalypseMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> MENU = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ApocalypseMod.MODID);

    public static RegistryObject<MenuType<GeneratorMenu>> GENERATOR = register("generator", GeneratorMenu::new);
    public static RegistryObject<MenuType<CabinetMenu>> CABINET = register("cabinet", CabinetMenu::new);
    public static RegistryObject<MenuType<SkillMenu>> SKILL = register("skill", SkillMenu::new);
    public static RegistryObject<MenuType<BackpackMenu>> BACKPACK = register("backpack", BackpackMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String name, IContainerFactory<T> factory){
        return MENU.register(name, () -> IForgeMenuType.create(factory));
    }
}
