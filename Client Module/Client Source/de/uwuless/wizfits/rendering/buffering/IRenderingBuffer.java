package de.uwuless.wizfits.rendering.buffering;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.utilities.Color;

public interface IRenderingBuffer {

    int WIDTH = IWizfitsClient.RESOLUTION_WIDTH;
    int HEIGHT = IWizfitsClient.RESOLUTION_HEIGHT;

    void create();
    void set(int x, int y, Color color);
    Color get(int x, int y);

}
