package de.uwuless.wizfits.desktop;

import de.uwuless.wizfits.input.WizfitsInputActions;
import de.uwuless.wizfits.utilities.Vector;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class DesktopInput implements KeyListener {

    private final Vector<Integer> holdKeys = new Vector();

    @Override
    public void keyTyped(KeyEvent e) { /* UwUseless */ }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!holdKeys.contains(e.getKeyCode())) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_F11:
                    WizfitsInputActions.FULLSCREEN_TOGGLE.simulate(true);
                    break;

                case KeyEvent.VK_W:
                    WizfitsInputActions.PLAYER_UP.simulate(true);
                    break;

                case KeyEvent.VK_A:
                    WizfitsInputActions.PLAYER_LEFT.simulate(true);
                    break;

                case KeyEvent.VK_S:
                    WizfitsInputActions.PLAYER_DOWN.simulate(true);
                    break;

                case KeyEvent.VK_D:
                    WizfitsInputActions.PLAYER_RIGHT.simulate(true);
                    break;

            }
            holdKeys.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(holdKeys.contains(e.getKeyCode())) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_F11:
                    WizfitsInputActions.FULLSCREEN_TOGGLE.simulate(false);
                    break;

                case KeyEvent.VK_W:
                    WizfitsInputActions.PLAYER_UP.simulate(false);
                    break;

                case KeyEvent.VK_A:
                    WizfitsInputActions.PLAYER_LEFT.simulate(false);
                    break;

                case KeyEvent.VK_S:
                    WizfitsInputActions.PLAYER_DOWN.simulate(false);
                    break;

                case KeyEvent.VK_D:
                    WizfitsInputActions.PLAYER_RIGHT.simulate(false);
                    break;

            }
            holdKeys.remove(e.getKeyCode());
        }
    }

}
