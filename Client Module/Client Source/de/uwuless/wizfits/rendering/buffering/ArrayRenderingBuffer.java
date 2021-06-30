package de.uwuless.wizfits.rendering.buffering;

import de.uwuless.wizfits.utilities.Color;

public class ArrayRenderingBuffer implements IRenderingBuffer {

    protected Color[] buffer;

    @Override
    public void create() {
        this.buffer = new Color[WIDTH * HEIGHT];
    }

    @Override
    public void set(int x, int y, Color color) {
        this.buffer[x + y * HEIGHT] = color;
    }

    @Override
    public Color get(int x, int y) {
        return buffer[x + y * WIDTH];
    }

}
