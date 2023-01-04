package me.squander.apocalypse.misc;

import me.squander.apocalypse.item.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TabInit {
    public static final CreativeModeTab APOCALYPSE_TAB = new CreativeModeTab("apocalypse") {
        @Override
        public ItemStack makeIcon() {
            return ItemInit.OLD_SHOTGUN.get().getDefaultInstance();
        }
    };

    public static final CreativeModeTab TRADE_TAB = new CreativeModeTab("apocalypse_trade") {
        @Override
        public ItemStack makeIcon() {
            return ItemInit.OLD_WORLD_MONEY.get().getDefaultInstance();
        }
    };
}
