package me.squander.apocalypse.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class SkillMenu extends BaseContainer{
    public SkillMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(pContainerId, inventory);
    }

    public SkillMenu(int pContainerId, Inventory inventory) {
        super(MenuTypeInit.SKILL.get(), pContainerId);
    }
}
