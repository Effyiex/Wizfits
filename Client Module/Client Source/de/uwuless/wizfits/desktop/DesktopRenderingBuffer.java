package de.uwuless.wizfits.desktop;

import de.uwuless.wizfits.rendering.buffering.IRenderingBuffer;
import de.uwuless.wizfits.utilities.Color;

import java.awt.image.BufferedImage;

public final class DesktopRenderingBuffer implements IRenderingBuffer {

    private BufferedImage buffer;

    @Override
    public void create() {
        this.buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void set(int x, int y, Color color) {
        DesktopRenderingBuffer.this.buffer.setRGB(x, y, color.toHex());
    }

    @Override
    public Color get(int x, int y) {
        return new Color(buffer.getRGB(x, y));
    }

    public BufferedImage getScreen() {
        return buffer;
    }

}
