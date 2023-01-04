package me.squander.apocalypse.misc;

import me.squander.apocalypse.item.ItemInit;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemPropertiesInit {
    public static void addCustomItemProperties(){
        makeItemProp(ItemInit.OLD_SHOTGUN.get());
    }

    private static void makeItemProp(Item item){
        ItemProperties.register(item, new ResourceLocation("pull"), (stack, clientLevel, livingEntity, p_174638_) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (stack, clientLevel, livingEntity, p_174633_) -> {
            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }
}
