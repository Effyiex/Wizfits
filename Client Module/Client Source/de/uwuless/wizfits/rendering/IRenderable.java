package de.uwuless.wizfits.rendering;

public interface IRenderable {

    int PRE_RENDER_TICK = 0;
    int BETWEEN_RENDER_TICK = 1;
    int POST_RENDER_TICK = 2;

    default int getRenderingPhase() {
        return IRenderable.BETWEEN_RENDER_TICK;
    }

    void onRenderTick();

}
