package me.squander.apocalypse.item;

import me.squander.apocalypse.capabilities.CapabilityInit;
import me.squander.apocalypse.capabilities.WeaponHandler;
import me.squander.apocalypse.entity.Bullet;
import me.squander.apocalypse.sound.SoundEventInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeaponItem extends Item {
    public WeaponItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag isAdvanced) {
        stack.getCapability(CapabilityInit.WEAPON_HANDLER_CAPABILITY).ifPresent(data -> {
            list.add(Component.literal("Ammo: " + data.getCurrentAmmo() + "/" + data.getMaxAmmo()));
        });
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (livingEntity instanceof Player player) {
            boolean flag = player.getAbilities().instabuild;

            int i = this.getUseDuration(stack) - timeLeft;
            if (i < 0) return;

            stack.getCapability(CapabilityInit.WEAPON_HANDLER_CAPABILITY).ifPresent(data -> {
                if(data.getCurrentAmmo() > 0 || flag){
                    float f = getPowerForTime(i);
                    if (!((double)f < 0.1D)){
                        if (!level.isClientSide) {
                            Bullet bullet = new Bullet(player, level);
                            bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
                            if (f == 1.0F) {
                                bullet.setCritArrow(true);
                            }

                            stack.hurtAndBreak(1, player, (player1) -> {
                                player1.broadcastBreakEvent(player.getUsedItemHand());
                            });

                            level.addFreshEntity(bullet);
                        }

                        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEventInit.SHOTGUN_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                        if (!flag && !player.getAbilities().instabuild) {
                            data.takeAmmo(1);
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                    }
                }
            });
        }
    }

    public static float getPowerForTime(int pCharge) {
        float f = (float)pCharge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        int currentAmmo = stack.getCapability(CapabilityInit.WEAPON_HANDLER_CAPABILITY).map(WeaponHandler::getCurrentAmmo).orElse(0);

        if (!player.getAbilities().instabuild && currentAmmo <= 0) {
            return InteractionResultHolder.fail(stack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new WeaponHandler();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 7200;
    }
}
