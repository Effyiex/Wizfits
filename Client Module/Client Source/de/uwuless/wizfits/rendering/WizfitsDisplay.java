package de.uwuless.wizfits.rendering;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.utilities.Color;

public final class WizfitsDisplay {

    private static Color filling;

    public static void setFilling(Color color) {
        WizfitsDisplay.filling = color;
    }

    public static void drawPixel(int x, int y, Color color) {
        if(0 <= x && 0 <= y && x < IWizfitsClient.RESOLUTION_WIDTH && y < IWizfitsClient.RESOLUTION_HEIGHT)
        IWizfitsClient.getRenderingBuffer().set(x, y, color);
    }

    public static void drawRectangle(int x, int y, int width, int height) {
        for(int a = 0; a < width; a++)
        for(int b = 0; b < height; b++)
        WizfitsDisplay.drawPixel(x + a, y + b, filling);
    }

}
