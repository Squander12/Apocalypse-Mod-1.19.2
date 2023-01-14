package me.squander.apocalypse;

import com.mojang.logging.LogUtils;
import me.squander.apocalypse.block.BlockInit;
import me.squander.apocalypse.blockentity.BlockEntityInit;
import me.squander.apocalypse.client.screen.CabinetScreen;
import me.squander.apocalypse.client.screen.GeneratorScreen;
import me.squander.apocalypse.client.screen.SkillScreen;
import me.squander.apocalypse.entity.EntityTypeInit;
import me.squander.apocalypse.item.ItemInit;
import me.squander.apocalypse.menu.MenuTypeInit;
import me.squander.apocalypse.misc.ItemPropertiesInit;
import me.squander.apocalypse.network.PacketHandler;
import me.squander.apocalypse.skill.SkillsInit;
import me.squander.apocalypse.sound.SoundEventInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ApocalypseMod.MODID)
public class ApocalypseMod {
    public static final String MODID = "apocalypse";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ApocalypseMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITY.register(bus);
        MenuTypeInit.MENU.register(bus);
        EntityTypeInit.ENTITY_TYPE.register(bus);
        SoundEventInit.SOUND.register(bus);
        SkillsInit.SKILLS.register(bus);
        PacketHandler.register();
        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        //MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(MenuTypeInit.GENERATOR.get(), GeneratorScreen::new);
            MenuScreens.register(MenuTypeInit.CABINET.get(), CabinetScreen::new);
            MenuScreens.register(MenuTypeInit.SKILL.get(), SkillScreen::new);
        });
        ItemPropertiesInit.addCustomItemProperties();
    }
}
