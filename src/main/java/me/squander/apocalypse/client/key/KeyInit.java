package me.squander.apocalypse.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class KeyInit {
    public static final Lazy<KeyMapping> RELOAD_KEY = register("key.apocalypse.reload", GLFW.GLFW_KEY_R, KeyMapping.CATEGORY_INVENTORY);
    public static final Lazy<KeyMapping> SKILL_SCREEN_KEY = register("key.apocalypse.skill_screen", GLFW.GLFW_KEY_B, KeyMapping.CATEGORY_INVENTORY);

    private static Lazy<KeyMapping> register(String key, int keyCode, String category){
        return Lazy.of(() -> new KeyMapping(key, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, keyCode, category));
    }
}
