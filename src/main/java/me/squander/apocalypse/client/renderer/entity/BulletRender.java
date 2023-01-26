package me.squander.apocalypse.client.renderer.entity;

import me.squander.apocalypse.entity.Bullet;
import me.squander.apocalypse.helper.Helper;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BulletRender extends ArrowRenderer<Bullet> {
    public static final ResourceLocation BULLET_TEXTURE = Helper.getRc("textures/entity/bullet.png");

    public BulletRender(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Bullet bullet) {
        return BULLET_TEXTURE;
    }
}
