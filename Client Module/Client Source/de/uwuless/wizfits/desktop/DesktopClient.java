package de.uwuless.wizfits.desktop;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.input.WizfitsInputActions;
import de.uwuless.wizfits.logging.WizfitsLogger;
import de.uwuless.wizfits.rendering.IRenderable;
import de.uwuless.wizfits.rendering.WizfitsDisplay;
import de.uwuless.wizfits.rendering.buffering.IRenderingBuffer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public final class DesktopClient extends JFrame implements IWizfitsClient {

    public static final Dimension INITIAL_SIZE = new Dimension(1024, 576);

    public static void main(String[] args) {
        WizfitsLogger.setBinding(str -> System.out.println(str));
        IWizfitsClient.create(DesktopClient.class);
        IWizfitsClient.getInstance().start();
        WizfitsDisplay.register(new IRenderable() {

            @Override
            public int getRenderingPhase() {
                return IRenderable.POST_RENDER_TICK;
            }

            @Override
            public void onRenderTick() {
                ((DesktopClient) IWizfitsClient.getInstance()).repaint();
            }

        });
    }

    @Override
    public Class<? extends IRenderingBuffer> getRenderingBufferType() {
        return DesktopRenderingBuffer.class;
    }

    private DesktopInput input = new DesktopInput();

    private void createFrame() {
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setTitle(GAME_NAME + " (" + GAME_BUILD + ") made by " + GAME_AUTHORS);
        this.addKeyListener(input);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        try {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream("wizfits_icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setResizable(false);
    }

    @Override
    public void setSize(int width, int height) {
        if(!isUndecorated()) height += 32;
        super.setSize(width, height);
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
        this.setSize(INITIAL_SIZE);
        this.createFrame();
        this.setVisible(true);
        WizfitsInputActions.FULLSCREEN_TOGGLE.addToggleListener(state -> {
            if(state) {
                DesktopClient.this.dispose();
                DesktopClient.this.setUndecorated(!isUndecorated());
                if(!isUndecorated()) setSize(INITIAL_SIZE);
                else setSize(Toolkit.getDefaultToolkit().getScreenSize());
                DesktopClient.this.createFrame();
                DesktopClient.this.setVisible(true);
            }
        });
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if(!b) this.stop();
    }

}
