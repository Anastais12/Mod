package com.cl1ppz12.strike.util.menu;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;

@Environment(EnvType.CLIENT)
public class CustomMainMenuScreen {

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof TitleScreen titleScreen) {
                adjustButtonPositions(titleScreen);
                removeSplashText(titleScreen); // Remove the splash text
            }
        });
    }

    private static void adjustButtonPositions(TitleScreen titleScreen) {
        for (ButtonWidget button : titleScreen.children().stream()
                .filter(ButtonWidget.class::isInstance)
                .map(ButtonWidget.class::cast)
                .toList()) {

            button.setX(button.getX());
            button.setY(button.getY() + 150);
        }
    }

    private static void removeSplashText(TitleScreen titleScreen) {
        try {

            var splashTextField = TitleScreen.class.getDeclaredField("splashText");
            splashTextField.setAccessible(true);
            splashTextField.set(titleScreen, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}