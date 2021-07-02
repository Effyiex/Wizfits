package de.uwuless.wizfits.rendering.interfaces;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.logging.WizfitsLogger;
import de.uwuless.wizfits.rendering.IRenderable;
import de.uwuless.wizfits.rendering.WizfitsColor;
import de.uwuless.wizfits.rendering.WizfitsDisplay;
import de.uwuless.wizfits.utilities.Vector;

public class WizfitsInterface extends Vector<IRenderable> {

    protected static int WIDTH = IWizfitsClient.RESOLUTION_WIDTH;
    protected static int HEIGHT = IWizfitsClient.RESOLUTION_HEIGHT;

    private static WizfitsInterface current, previous;

    public static void display(WizfitsInterface gui) {
        if(current != null) current.forEach(WizfitsDisplay::dispose);
        previous = current;
        current = gui;
        if(current != null) current.forEach(WizfitsDisplay::register);
        WizfitsLogger.INFO.print("Now displaying: \"" + formatInterfaceName(gui) + '\"');
    }

    public static String formatInterfaceName(WizfitsInterface gui) {
        StringBuffer output = new StringBuffer();
        for(char ch : gui.getClass().getSimpleName().toCharArray()) {
            if(Character.isUpperCase(ch)) output.append(' ');
            output.append(ch);
        }
        return output.toString().substring(1);
    }

    protected WizfitsColor background = new WizfitsColor("#111115");

    public WizfitsInterface() {
        this.add(new IRenderable() {

            @Override
            public int getRenderingPhase() {
                return IRenderable.PRE_RENDER_TICK;
            }

            @Override
            public void onRenderTick() {
                WizfitsDisplay.setFilling(background);
                WizfitsDisplay.drawRectangle(0, 0, WIDTH, HEIGHT);
            }

        });
    }

}
