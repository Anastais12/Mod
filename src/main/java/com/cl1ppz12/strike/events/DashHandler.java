package com.cl1ppz12.strike.events;

import com.cl1ppz12.strike.client.Overlays.DashOverlay;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class DashHandler {
    public static final String KEY_CATEGORY = "key.category.strike.controls";
    public static final String KEY_DASH = "key.strike.dash";

    public static KeyBinding dashKey;
    private static boolean isOnCooldown = false;
    private static int cooldownTicks = 0;
    private static final int COOLDOWN_DURATION = 30;
    private static final double DASH_STRENGTH = 0.9;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            DashOverlay.onTick();

            if (client.player != null) {

                if (isOnCooldown) {
                    cooldownTicks--;
                    if (cooldownTicks <= 0) {
                        isOnCooldown = false;
                    }
                }


                if (dashKey.wasPressed()) {
                    if (!isOnCooldown) {

                        float yaw = client.player.getYaw();


                        double x = -Math.sin(Math.toRadians(yaw));
                        double z = Math.cos(Math.toRadians(yaw));


                        Vec3d direction = new Vec3d(x, 0.5, z).normalize();
                        Vec3d velocity = direction.multiply(DASH_STRENGTH);

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


                        client.player.setVelocity(velocity.x, 0.5, velocity.z);


                        isOnCooldown = true;
                        cooldownTicks = COOLDOWN_DURATION;


                        DashOverlay.triggerOverlay(30);
                    }
                }
            }
        });
    }

    public static void register() {
        dashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DASH,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}