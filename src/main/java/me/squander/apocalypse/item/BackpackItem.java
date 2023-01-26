package me.squander.apocalypse.item;

import me.squander.apocalypse.capabilities.BackpackHandler;
import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.menu.BackpackMenu;
import me.squander.apocalypse.misc.BackpackType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BackpackItem extends Item {
    private final BackpackType backpackType;

    public BackpackItem(BackpackType backpackType, Properties pProperties) {
        super(pProperties);
        this.backpackType = backpackType;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> toolTip, TooltipFlag isAdvanced) {
        stack.getCapability(CapabilityInit.BACKPACK_HANDLER).ifPresent(h -> {
            for (int i = 0; i < h.getSlots(); i++) {
                ItemStack stack1 = h.getStackInSlot(i);
                if(!stack1.isEmpty()){
                    MutableComponent component = stack1.getHoverName().copy();
                    component.append(" x").append(String.valueOf(stack1.getCount()));
                    toolTip.add(component);
                }
            }
        });
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(!level.isClientSide()){
            NetworkHooks.openScreen(((ServerPlayer) player), this.getMenuProvider(stack), buf -> buf.writeItem(stack));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private MenuProvider getMenuProvider(ItemStack stack){
        return new SimpleMenuProvider((id, inv, p) -> new BackpackMenu(id, inv, stack), stack.getHoverName());
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new BackpackHandler(this.backpackType);
    }
}
