package de.uwuless.wizfits;

import de.uwuless.wizfits.logging.WizfitsLogger;
import de.uwuless.wizfits.rendering.WizfitsDisplay;
import de.uwuless.wizfits.rendering.buffering.IRenderingBuffer;
import de.uwuless.wizfits.logging.WizfitsError;
import de.uwuless.wizfits.rendering.interfaces.WizfitsBrandingInterface;
import de.uwuless.wizfits.rendering.interfaces.WizfitsInterface;

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
                new WizfitsError("Couldn't create the Instance of: \"" + clazz.getSimpleName() + '\"').crash();
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
        WizfitsLogger.INFO.print("Launching Display...");
        WizfitsDisplay.launch();
        WizfitsInterface.display(new WizfitsBrandingInterface());
    }

    default void stop() {
        WizfitsLogger.INFO.print("Stopping Display...");
        WizfitsDisplay.interrupt();
        WizfitsLogger.INFO.print("Exiting the game... We hope to see u back :)");
        System.exit(0);
    }

}
