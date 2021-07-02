package de.uwuless.wizfits.rendering.buffering;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.rendering.WizfitsColor;

public interface IRenderingBuffer {

    int WIDTH = IWizfitsClient.RESOLUTION_WIDTH;
    int HEIGHT = IWizfitsClient.RESOLUTION_HEIGHT;

    void create();
    void set(int x, int y, WizfitsColor color);
    WizfitsColor get(int x, int y);

    default void setArea(int x, int y, int width, int height, WizfitsColor color) {
        for(int a = 0; a < width; a++)
        for(int b = 0; b < height; b++)
        set(x + a, y + b, color);
    }

}
