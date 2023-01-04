package me.squander.apocalypse.item;

import me.squander.apocalypse.helper.Helper;
import me.squander.apocalypse.misc.ArmorMaterialInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ArmorItemInit extends ArmorItem {
    public static final ResourceLocation MASK_BLUR = Helper.getRc("textures/misc/radiation_mask_blur.png");

    public ArmorItemInit(ArmorMaterial armorMaterial, EquipmentSlot slot, Properties properties) {
        super(armorMaterial, slot, properties);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return Helper.getArmorTextureLocation(this.material, slot == EquipmentSlot.LEGS);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTick) {
                if(material == ArmorMaterialInit.RADIATION_SUIT){
                    Helper.renderTextureOverlay(MASK_BLUR, 1.0F, width, height);
                }
            }
        });
    }
}
