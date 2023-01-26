package me.squander.apocalypse.client.renderer.entity;

import me.squander.apocalypse.client.model.ModelLayersInit;
import me.squander.apocalypse.helper.Helper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class BiterRender extends ZombieRenderer {
    public static final ResourceLocation TEXTURE = Helper.getRc("textures/entity/biter.png");

    public BiterRender(EntityRendererProvider.Context pContext) {
        super(pContext, ModelLayersInit.BITER, ModelLayersInit.BITER_INNER, ModelLayersInit.BITER_OUTER);
    }

    @Override
    public ResourceLocation getTextureLocation(Zombie pEntity) {
        return TEXTURE;
    }
}
