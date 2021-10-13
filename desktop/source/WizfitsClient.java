
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker;

import javax.imageio.ImageIO;

import java.io.InputStream;

import java.util.concurrent.atomic.AtomicReference;

import org.w3c.dom.*;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class WizfitsClient {

  public static void main(String[] args) {

    JFrame frame = new JFrame("Wizfits Client by Effyiex");
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    JFXPanel panel = new JFXPanel();

    AtomicReference<WebView> view = new AtomicReference<WebView>();
    Platform.runLater(() -> {
      view.set(new WebView());
      panel.setScene(new Scene(view.get()));
      view.get().getEngine().load("http://127.0.0.1:3000");
    });

    try {
      InputStream stream = WizfitsClient.class.getResourceAsStream("WizfitsIcon");
      frame.setIconImage(ImageIO.read(stream));
      stream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    frame.add(panel);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(800, 480);
    frame.setLocation((screen.width - frame.getWidth()) / 2, (screen.height - frame.getHeight()) / 2);
    frame.setVisible(true);

    AtomicReference<KeyListener> fullscreenToggle = new AtomicReference<KeyListener>();
    fullscreenToggle.set(new KeyListener() {

      private boolean isFullscreenKeyPressed = false;

      public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_F11)
        if(!isFullscreenKeyPressed) {

          Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
          this.isFullscreenKeyPressed = true;
          boolean isFullScreen = frame.isUndecorated();

          frame.dispose();

          if(isFullScreen) {
            frame.setSize(800, 480);
            frame.setLocation((screen.width - frame.getWidth()) / 2, (screen.height - frame.getHeight()) / 2);
            frame.setUndecorated(false);
          } else {
            frame.setSize(screen);
            frame.setLocation(0, 0);
            frame.setUndecorated(true);
          }

          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          frame.add(panel);
          frame.setVisible(true);

        }
      }

      public void keyReleased(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_F11)
        this.isFullscreenKeyPressed = false;
      }

      public void keyTyped(KeyEvent event) { /* USELESS */ }

    });

    panel.addKeyListener(fullscreenToggle.get());

  }

}
