package me.squander.apocalypse.entity;

import me.squander.apocalypse.item.ItemInit;
import me.squander.apocalypse.sound.SoundEventInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Bullet extends AbstractArrow {
    public Bullet(EntityType<Bullet> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Bullet(LivingEntity pShooter, Level pLevel) {
        super(EntityTypeInit.BULLET.get(), pShooter, pLevel);
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemInit.SHOTGUN_AMMO.get().getDefaultInstance();
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEventInit.SHOTGUN_HIT.get();
    }

    @Override
    protected boolean tryPickup(Player player) {
        return this.pickup == Pickup.CREATIVE_ONLY || player.isCreative();
    }
}
