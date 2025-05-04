package com.cl1ppz12.strike.client;


import com.cl1ppz12.strike.Strike;
import com.cl1ppz12.strike.client.Overlays.DashOverlay;
import com.cl1ppz12.strike.events.ZoomHandler;
import com.cl1ppz12.strike.util.menu.CustomMainMenuScreen;
import net.fabricmc.api.ClientModInitializer;
import com.cl1ppz12.strike.events.DashHandler;
import com.cl1ppz12.strike.events.DoubleJumpHandler;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.EndRodParticle;

public class StrikeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        DashHandler.register();
        DoubleJumpHandler.register();
        ZoomHandler.register();

        CustomMainMenuScreen.register();

        // For this example, we will use the end rod particle behaviour.
        ParticleFactoryRegistry.getInstance().register(Strike.BLOOD_BUBBLE_PARTICLE, EndRodParticle.Factory::new);

        HudRenderCallback.EVENT.register(DashOverlay::renderOverlay);

    }
}
