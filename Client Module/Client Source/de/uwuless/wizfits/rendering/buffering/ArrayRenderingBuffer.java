package de.uwuless.wizfits.rendering.buffering;

import de.uwuless.wizfits.rendering.WizfitsColor;

public class ArrayRenderingBuffer implements IRenderingBuffer {

    protected WizfitsColor[] buffer;

    @Override
    public void define(int width, int height) {
        this.buffer = new WizfitsColor[width * height];
    }

    @Override
    public void set(int x, int y, WizfitsColor color) {
        this.buffer[x + y * HEIGHT] = color;
    }

    @Override
    public WizfitsColor get(int x, int y) {
        return buffer[x + y * WIDTH];
    }

}
