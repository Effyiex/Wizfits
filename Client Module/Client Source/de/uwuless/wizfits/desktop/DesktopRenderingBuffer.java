package de.uwuless.wizfits.desktop;

import de.uwuless.wizfits.rendering.buffering.IRenderingBuffer;
import de.uwuless.wizfits.rendering.WizfitsColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class DesktopRenderingBuffer implements IRenderingBuffer {

    private BufferedImage buffer;

    @Override
    public void create() {
        this.buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void set(int x, int y, WizfitsColor color) {
        DesktopRenderingBuffer.this.buffer.setRGB(x, y, color.toHex());
    }

    @Override
    public void setArea(int x, int y, int width, int height, WizfitsColor color) {
        Graphics2D gl = this.buffer.createGraphics();
        gl.setColor(new java.awt.Color(color.toHex(), true));
        gl.fillRect(x, y, width, height);
        gl.dispose();
    }

    @Override
    public WizfitsColor get(int x, int y) {
        return new WizfitsColor(buffer.getRGB(x, y));
    }

    public BufferedImage getScreen() {
        return buffer;
    }

}
