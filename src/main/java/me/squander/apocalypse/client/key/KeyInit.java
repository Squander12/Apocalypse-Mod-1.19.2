package me.squander.apocalypse.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class KeyInit {
    public static final Lazy<KeyMapping> RELOAD_KEY = Lazy.of(() ->
            new KeyMapping("key.apocalypse.reload", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KeyMapping.CATEGORY_INVENTORY));
}
