package de.uwuless.wizfits.resources;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.rendering.WizfitsColor;
import de.uwuless.wizfits.rendering.buffering.IRenderingBuffer;
import de.uwuless.wizfits.utilities.Vector;

public class WizfitsTexture implements IWizfitsResource {

    public static final int RGBA_BYTE_COUNT = 4;

    public final String name;

    public final IRenderingBuffer buffer;

    public WizfitsTexture(String name) {
        this.name = name;
        this.buffer = (IRenderingBuffer) IWizfitsClient.Instances.create(IWizfitsClient.getInstance().getRenderingBufferType());
    }

    @Override
    public String getIdentifier() {
        return "texture_" + this.name;
    }

    public static Object[] calcAndCutSize(byte[] bytes) {
        Vector<Integer> size = new Vector(2);
        int widthIdentifier = 0;
        while(bytes[widthIdentifier] != Byte.MAX_VALUE) {
            size.update(0, size.get(0) + bytes[widthIdentifier]);
            widthIdentifier++;
        }
        size.update(1, (bytes.length - widthIdentifier) / RGBA_BYTE_COUNT / size.get(0));
        byte[] buffer = new byte[bytes.length - widthIdentifier];
        for(int i = 0; i < buffer.length; i++)
        buffer[i] = bytes[i + widthIdentifier];
        return new Object[] { size, buffer };
    }

    @Override
    public void construct(byte[] bytes) {
        Object[] calcData = calcAndCutSize(bytes);
        Vector<Integer> size = (Vector<Integer>) calcData[0];
        this.buffer.define(size.get(0), size.get(1));
        byte[] buffer = (byte[]) calcData[1];
        for(int i = 0; i < buffer.length; i += RGBA_BYTE_COUNT) {
            float r = 255.0F / (Byte.MAX_VALUE - Byte.MIN_VALUE) * buffer[i];
            float g = 255.0F / (Byte.MAX_VALUE - Byte.MIN_VALUE) * buffer[i + 1];
            float b = 255.0F / (Byte.MAX_VALUE - Byte.MIN_VALUE) * buffer[i + 2];
            float a = 255.0F / (Byte.MAX_VALUE - Byte.MIN_VALUE) * buffer[i + 3];
            int y = (int) Math.floor(i / RGBA_BYTE_COUNT / size.get(0));
            int x = i / RGBA_BYTE_COUNT - size.get(0) * y;
            this.buffer.set(x, y, new WizfitsColor(r, g, b, a));
        }
    }

}
