package de.uwuless.wizfits.rendering;

import de.uwuless.wizfits.IWizfitsClient;
import de.uwuless.wizfits.logging.WizfitsError;
import de.uwuless.wizfits.logging.WizfitsLogger;
import de.uwuless.wizfits.utilities.LoopThread;
import de.uwuless.wizfits.utilities.Vector;

public final class WizfitsDisplay {

    public static final int MAX_FRAMES_PER_SECOND = 60;

    private static Vector<IRenderable> preRenderables = new Vector();
    private static Vector<IRenderable> betweenRenderables = new Vector();
    private static Vector<IRenderable> postRenderables = new Vector();

    private static WizfitsColor filling;

    private static int debugFPS = MAX_FRAMES_PER_SECOND, debugFPSCount = 0;

    public static final LoopThread THREAD = new LoopThread(() -> {
        preRenderables.forEach(renderable -> renderable.onRenderTick());
        betweenRenderables.forEach(renderable -> renderable.onRenderTick());
        postRenderables.forEach(renderable -> renderable.onRenderTick());
        debugFPSCount++;
    }, debugFPS);

    private static final LoopThread CONTROL_THREAD = new LoopThread(() -> {
        debugFPS = debugFPSCount + 1;
        debugFPSCount = 0;
    }, 1);

    public static void setMaxFPS(int fps) {
        THREAD.setTps(fps);
    }

    public static int getDebugFPS() {
        return debugFPS;
    }

    public static void launch() {
        THREAD.start();
        CONTROL_THREAD.start();
    }

    public static void interrupt() {
        THREAD.stop();
        CONTROL_THREAD.stop();
    }

    public static void register(IRenderable renderable) {
        switch (renderable.getRenderingPhase()) {

            case IRenderable.PRE_RENDER_TICK:
                preRenderables.add(renderable);
                break;

            case IRenderable.BETWEEN_RENDER_TICK:
                betweenRenderables.add(renderable);
                break;

            case IRenderable.POST_RENDER_TICK:
                postRenderables.add(renderable);
                break;

            default: new WizfitsError("Unknown Rendering Phase.").crash();

        }
    }

    public static void dispose(IRenderable renderable) {
        switch (renderable.getRenderingPhase()) {

            case IRenderable.PRE_RENDER_TICK:
                preRenderables.remove(renderable);
                break;

            case IRenderable.BETWEEN_RENDER_TICK:
                betweenRenderables.remove(renderable);
                break;

            case IRenderable.POST_RENDER_TICK:
                postRenderables.remove(renderable);
                break;

            default: new WizfitsError("Unknown Rendering Phase.").crash();

        }
    }

    public static void setFilling(WizfitsColor color) {
        WizfitsDisplay.filling = color;
    }

    public static void drawPixel(int x, int y, WizfitsColor color) {
        if(0 <= x && 0 <= y && x < IWizfitsClient.RESOLUTION_WIDTH && y < IWizfitsClient.RESOLUTION_HEIGHT)
        IWizfitsClient.getRenderingBuffer().set(x, y, color);
    }

    public static void drawRectangle(int x, int y, int width, int height) {
        if(x > IWizfitsClient.RESOLUTION_WIDTH || y > IWizfitsClient.RESOLUTION_HEIGHT) return;
        if(x + width > IWizfitsClient.RESOLUTION_WIDTH) width = IWizfitsClient.RESOLUTION_WIDTH;
        if(y + height > IWizfitsClient.RESOLUTION_HEIGHT) height = IWizfitsClient.RESOLUTION_HEIGHT;
        if(x < 0 && x + width > 0) {
            width += x;
            x = 0;
        }
        if(y < 0 && y + height > 0) {
            height += y;
            y = 0;
        }
        if(width > 0 && height > 0 && 0 <= x && 0 <= y)
        IWizfitsClient.getRenderingBuffer().setArea(x, y, width, height, filling);
    }

}
