package me.squander.apocalypse.block;

import me.squander.apocalypse.blockentity.BlockEntityInit;
import me.squander.apocalypse.blockentity.MilitaryChestBlockEntity;
import me.squander.apocalypse.item.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Optional;

public class MilitaryChest extends ChestBlock {
    public static final Component NO_CROWBAR_MESSAGE = Component.translatable("military_chest.no_crowbar_message");

    public MilitaryChest(Properties properties) {
        super(properties, () -> BlockEntityInit.MILITARY_CHEST.get());
    }

    private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> MENU_PROVIDER_COMBINER = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>>() {
        public Optional<MenuProvider> acceptDouble(final ChestBlockEntity chestLeft, final ChestBlockEntity chestRight) {
            final Container container = new CompoundContainer(chestLeft, chestRight);
            return Optional.of(new MenuProvider() {
                @Nullable
                public AbstractContainerMenu createMenu(int id, Inventory pPlayerInventory, Player player) {
                    if (chestLeft.canOpen(player) && chestRight.canOpen(player)) {
                        chestLeft.unpackLootTable(pPlayerInventory.player);
                        chestRight.unpackLootTable(pPlayerInventory.player);
                        return ChestMenu.sixRows(id, pPlayerInventory, container);
                    } else {
                        return null;
                    }
                }

                public Component getDisplayName() {
                    if (chestLeft.hasCustomName()) {
                        return chestLeft.getDisplayName();
                    } else {
                        return chestRight.hasCustomName() ? chestRight.getDisplayName() : MilitaryChestBlockEntity.CONTAINER_NAME;
                    }
                }
            });
        }

        public Optional<MenuProvider> acceptSingle(ChestBlockEntity chestBlockEntity) {
            return Optional.of(chestBlockEntity);
        }

        public Optional<MenuProvider> acceptNone() {
            return Optional.empty();
        }
    };

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHit) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if(stack.getItem() == ItemInit.CROWBAR.get() || player.isCreative()){
                MenuProvider menuprovider = this.getMenuProvider(state, level, pos);
                if (menuprovider != null) {
                    NetworkHooks.openScreen((ServerPlayer) player, menuprovider);
                }

                if(!stack.isEmpty() && !player.getAbilities().instabuild){
                    stack.hurtAndBreak(3, player, x -> x.broadcastBreakEvent(hand));
                }

                return InteractionResult.CONSUME;
            }else{
                player.displayClientMessage(NO_CROWBAR_MESSAGE, true);
                return InteractionResult.FAIL;
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.hasBlockEntity() && (!pState.is(pNewState.getBlock()) || !pNewState.hasBlockEntity())) {
            pLevel.removeBlockEntity(pPos);
        }
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return this.combine(state, level, pos, false).apply(MENU_PROVIDER_COMBINER).orElse(null);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MilitaryChestBlockEntity(pos, state);
    }
}
