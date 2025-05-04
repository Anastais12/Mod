package com.cl1ppz12.strike.events;

import com.cl1ppz12.strike.client.Overlays.DashOverlay;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class DoubleJumpHandler {
    public static final String KEY_CATEGORY = "key.category.strike.controls";
    public static final String KEY_DOUBLE_JUMP = "key.strike.jump";


    private static KeyBinding jumpKey;


    private static boolean isOnCooldown = false;
    private static int cooldownTicks = 0;
    private static final int COOLDOWN_DURATION = 30;
    private static final double JUMP_STRENGTH = 0.6;

    public static void register() {

        jumpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DOUBLE_JUMP,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KEY_CATEGORY
        ));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {

                if (isOnCooldown) {
                    cooldownTicks--;
                    if (cooldownTicks <= 0) {
                        isOnCooldown = false;
                    }
                }


                if (jumpKey.wasPressed()) {
                    if (!isOnCooldown) {
                        client.player.setVelocity(client.player.getVelocity().x, JUMP_STRENGTH, client.player.getVelocity().z);

                        Vec3d playerPos = client.player.getPos();
                        for (int i = 0; i < 10; i++) {
                            double offsetX = (Math.random() - 0.5) * 0.5;
                            double offsetY = (Math.random() - 0.5) * 0.5;
                            double offsetZ = (Math.random() - 0.5) * 0.5;

                            client.world.addParticleClient(
                                    ParticleTypes.CLOUD,
                                    playerPos.x + offsetX,
                                    playerPos.y + offsetY,
                                    playerPos.z + offsetZ,
                                    0.0,
                                    0.0,
                                    0.0
                            );
                        }


                        isOnCooldown = true;
                        cooldownTicks = COOLDOWN_DURATION;

                        DashOverlay.triggerOverlay(COOLDOWN_DURATION);

                    }
                }
            }
        });
    }
}