package de.uwuless.wizfits.rendering.interfaces;

import de.uwuless.wizfits.input.WizfitsInputActions;
import de.uwuless.wizfits.rendering.IRenderable;
import de.uwuless.wizfits.rendering.WizfitsColor;
import de.uwuless.wizfits.rendering.WizfitsDisplay;

public final class WizfitsBrandingInterface
        extends WizfitsInterface implements IRenderable {

    public WizfitsBrandingInterface() {
        this.add((IRenderable) this);
    }

    private int x = 0, y = 0;

    @Override
    public void onRenderTick() {
        WizfitsDisplay.setFilling(new WizfitsColor("#F3A"));
        WizfitsDisplay.drawRectangle(x, y, 32, 32);
        if(WizfitsInputActions.PLAYER_UP.isActive()) y--;
        if(WizfitsInputActions.PLAYER_DOWN.isActive()) y++;
        if(WizfitsInputActions.PLAYER_LEFT.isActive()) x--;
        if(WizfitsInputActions.PLAYER_RIGHT.isActive()) x++;
    }

}
