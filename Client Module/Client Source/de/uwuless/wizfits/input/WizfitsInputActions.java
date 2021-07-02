package de.uwuless.wizfits.input;

import de.uwuless.wizfits.utilities.IListener;
import de.uwuless.wizfits.utilities.Vector;

public enum WizfitsInputActions {

    CLICK, PLAYER_LEFT, PLAYER_UP, PLAYER_RIGHT, PLAYER_DOWN, FULLSCREEN_TOGGLE;

    private Vector<IListener<Boolean>> listeners = new Vector();

    public void addToggleListener(IListener<Boolean> listener) {
        this.listeners.add(listener);
    }

    public void removeToggleListener(IListener<Boolean> listener) {
        this.listeners.remove(listener);
    }

    private boolean state = false;

    public void simulate(boolean state) {
        this.state = state;
        this.listeners.forEach(listener -> listener.handle(state));
    }

    public boolean isActive() {
        return state;
    }

}
