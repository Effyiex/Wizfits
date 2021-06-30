package de.uwuless.wizfits;

import de.uwuless.wizfits.desktop.DesktopClient;
import de.uwuless.wizfits.rendering.WizfitsDisplay;
import de.uwuless.wizfits.rendering.buffering.IRenderingBuffer;
import de.uwuless.wizfits.utilities.Color;
import de.uwuless.wizfits.utilities.GameError;

public interface IWizfitsClient {

    String GAME_NAME = "Wizfits";
    String GAME_BUILD = "indev";
    String GAME_AUTHORS = "UwUseless Studio";

    int RESOLUTION_WIDTH = 640;
    int RESOLUTION_HEIGHT = 360;

    class Instances {

        private static IWizfitsClient gameClient;
        private static IRenderingBuffer renderingBuffer;

        private static Object create(Class<?> clazz, Object... args) {
            try {
                return clazz.getDeclaredConstructors()[0].newInstance(args);
            } catch (Exception exception) {
                new GameError("Couldn't create the Instance of: \"" + clazz.getSimpleName() + '\"').crash();
            }
            return null;
        }

    }

    static void create(Class<? extends IWizfitsClient> type) {
        Instances.gameClient = (IWizfitsClient) Instances.create(type);
    }

    static IWizfitsClient getInstance() {
        return Instances.gameClient;
    }

    static IRenderingBuffer getRenderingBuffer() {
        return Instances.renderingBuffer;
    }

    Class<? extends IRenderingBuffer> getRenderingBufferType();

    default void start() {
        WizfitsLogger.INFO.print("Launching " + GAME_NAME + "...");
        WizfitsLogger.INFO.print("> Version/Build: " + GAME_BUILD);
        WizfitsLogger.INFO.print("> Made by: " + GAME_AUTHORS);
        Instances.renderingBuffer = (IRenderingBuffer) Instances.create(getRenderingBufferType());
        WizfitsLogger.INFO.print("Creating Rendering-Buffer...");
        Instances.renderingBuffer.create();
        new Thread(() -> {
            int x = 0;
            while(true) {
                try {
                    Thread.sleep(16L);
                    WizfitsDisplay.setFilling(new Color("#000000"));
                    WizfitsDisplay.drawRectangle(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
                    WizfitsDisplay.setFilling(new Color("#FF55AA"));
                    WizfitsDisplay.drawRectangle(x, 32, 64, 64);
                    ((DesktopClient) this).repaint();
                    x++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    default void stop() {
        WizfitsLogger.INFO.print("Exiting the game... We hope to see u back :)");
        System.exit(0);
    }

}
