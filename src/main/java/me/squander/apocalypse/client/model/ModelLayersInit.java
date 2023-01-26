package me.squander.apocalypse.client.model;

import me.squander.apocalypse.helper.Helper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class ModelLayersInit {
    public static final ModelLayerLocation BITER = register("biter", "main");
    public static final ModelLayerLocation BITER_INNER = register("biter", "inner_armor");
    public static final ModelLayerLocation BITER_OUTER = register("biter", "outer_armor");

    private static ModelLayerLocation register(String path, String layer){
        return new ModelLayerLocation(Helper.getRc(path), layer);
    }

    public static class Layer{
        public static final LayerDefinition NORMAL = LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0f), 64, 64);
        public static final LayerDefinition ARMOR_INNER = LayerDefinition.create(HumanoidModel.createMesh(LayerDefinitions.INNER_ARMOR_DEFORMATION, 0.0f), 64, 32);
        public static final LayerDefinition ARMOR_OUTER = LayerDefinition.create(HumanoidModel.createMesh(LayerDefinitions.OUTER_ARMOR_DEFORMATION, 0.0f), 64, 32);
    }
}
