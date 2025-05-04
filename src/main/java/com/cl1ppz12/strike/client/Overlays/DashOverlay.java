package com.cl1ppz12.strike.client.Overlays;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public abstract class DashOverlay implements ClientModInitializer {

    private static final Identifier[] TEXTURES = {
        Identifier.of("strike", "textures/gui/cooldown_bar_full.png"),
        Identifier.of("strike", "textures/gui/cooldown_bar_1.png"),
        Identifier.of("strike", "textures/gui/cooldown_bar_2.png"),
        Identifier.of("strike", "textures/gui/cooldown_bar_3.png"),
        Identifier.of("strike", "textures/gui/cooldown_bar_4.png"),
        Identifier.of("strike", "textures/gui/cooldown_bar_5.png"),
        Identifier.of("strike", "textures/gui/cooldown_bar_6.png"),
        Identifier.of("strike", "textures/gui/cooldown_bar_8.png"),
        Identifier.of("strike", "textures/gui/cooldown_bar_empty.png")
    };


    private enum OverlayState {
        ACTIVE, NONE
    }


    private static OverlayState currentState = OverlayState.NONE;


    private static int ticksPerFrame = 0;
    private static int overlayTickCounter = 0;


    private static int currentTextureIndex = 0;


    public static void triggerOverlay(int totalTicks) {
        currentState = OverlayState.ACTIVE;
        overlayTickCounter = totalTicks;
        ticksPerFrame = totalTicks / TEXTURES.length;
        currentTextureIndex = 0;
    }

    public static void onTick() {
        if (currentState == OverlayState.ACTIVE) {
            if (overlayTickCounter > 0) {
                overlayTickCounter--;


                if (overlayTickCounter % ticksPerFrame == 0 && currentTextureIndex < TEXTURES.length - 1) {
                    currentTextureIndex++;
                }
            } else {

                currentState = OverlayState.NONE;
            }
        }
    }

    public static void renderOverlay(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (currentState == OverlayState.NONE) return;

        Identifier textureToRender = TEXTURES[currentTextureIndex];

        int screenWidth = drawContext.getScaledWindowWidth();
        int screenHeight = drawContext.getScaledWindowHeight();


        int textureSize = 16;
        int centerX = (screenWidth / 2) - (textureSize / 2);
        int centerY = (screenHeight / 2) + 10;


        drawContext.drawTexture(
                RenderLayer::getGuiTextured,
                textureToRender,
                centerX,
                centerY,
                0,
                0,
                textureSize,
                textureSize,
                textureSize,
                textureSize
        );
    }
}