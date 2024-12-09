package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import app.App;
import app.SocialMedia;

public class SocialsDialog extends JDialog {
  private App observer;
  private JButton exitButton;

  public SocialsDialog(JFrame parent, App observer, List<SocialMedia> socials) {
    super(parent, "Add Member", true);

    setSize(350, 280);
    setResizable(false);

    this.observer = observer;

    // Use BorderLayout on the dialog's content pane
    setLayout(new BorderLayout()); // Make sure to use getContentPane()

    // Create a panel for social media labels
    JPanel socialsPanel = new JPanel();
    socialsPanel.setLayout(new BoxLayout(socialsPanel, BoxLayout.Y_AXIS)); // Stack labels vertically

    // Add the social media labels
    for (SocialMedia social : socials) {
      JLabel socialLabel = new JLabel(social.getPlatform() + ": " + social.getUsername());
      socialLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center-align the label
      socialsPanel.add(socialLabel);
    }

    // Add the socials panel to the top (NORTH) of the dialog
    add(socialsPanel, BorderLayout.NORTH);

    // Create a panel to center the exit button
    JPanel exitPanel = new JPanel();
    exitPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the button within the panel

    // Add exit button to the panel
    exitButton = new JButton("Exit");
    exitButton.setPreferredSize(new Dimension(100, 30)); // Set the preferred size to make it smaller
    exitButton.addActionListener(e -> {
      dispose();
    });

    exitPanel.add(exitButton);  // Add button to the panel

    // Add the exit panel to the bottom (SOUTH) of the dialog
    add(exitPanel, BorderLayout.SOUTH);

    setLocationRelativeTo(parent);
  }
}