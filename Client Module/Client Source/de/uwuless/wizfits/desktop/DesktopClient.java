package de.uwuless.wizfits.desktop;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.WizfitsLogger;
import de.uwuless.wizfits.rendering.buffering.IRenderingBuffer;

import javax.swing.*;
import java.awt.*;

public final class DesktopClient extends JFrame implements IWizfitsClient {

    public static void main(String[] args) {
        WizfitsLogger.setBinding(str -> System.out.println(str[0]));
        IWizfitsClient.create(DesktopClient.class);
        IWizfitsClient.getInstance().start();
    }

    @Override
    public Class<? extends IRenderingBuffer> getRenderingBufferType() {
        return DesktopRenderingBuffer.class;
    }

    @Override
    public void start() {
        IWizfitsClient.super.start();
        this.add(new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(((DesktopRenderingBuffer) IWizfitsClient.getRenderingBuffer()).getScreen(), 0, 0, getWidth(), getHeight(), null);
            }

        });
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setTitle(GAME_NAME + " (" + GAME_BUILD + ") made by " + GAME_AUTHORS);
        this.setSize(800, 480);
        this.setVisible(true);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if(!b) this.stop();
    }

}
