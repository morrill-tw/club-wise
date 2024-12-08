import javax.swing.*;

import app.App;
import app.ClubWiseApp;
import ui.ClubWiseGUI;
import ui.UI;

/**
 * Used to run the app.
 */
public class Main {
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    UI gui = new ClubWiseGUI();
    App clubWise = new ClubWiseApp(gui);
    clubWise.run();
  }
}