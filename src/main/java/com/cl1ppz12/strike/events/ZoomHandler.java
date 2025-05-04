package com.cl1ppz12.strike.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ZoomHandler {
    public static final String KEY_CATEGORY = "key.category.strike.controls";
    public static final String KEY_ZOOM = "key.strike.zoom";


    private static KeyBinding zoomKey;

    private static double originalFov = 70.0;

    public static void register() {
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_ZOOM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                KEY_CATEGORY
        ));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.options != null) {
                

                if (zoomKey.isPressed()) {
                    if (originalFov == 70.0) {
                        originalFov = client.options.getFov().getValue();
                    }
                    

                    client.options.getFov().setValue((int) 30.0);
                } else {

                    if (originalFov != 70.0) {
                        client.options.getFov().setValue((int) originalFov);
                        originalFov = 70.0;
                    }
                }
            }
        });
    }
}