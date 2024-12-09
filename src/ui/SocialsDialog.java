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

    // Use BoxLayout on the dialog itself
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Make sure to use getContentPane()

    // Add the social media labels
    for (SocialMedia social : socials) {
      JLabel socialLabel = new JLabel(social.getPlatform() + ": " + social.getUsername());
      socialLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center-align the label
      add(socialLabel);
    }

    // Add exit button with centered alignment
    exitButton = new JButton("Exit");
    exitButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the button
    exitButton.addActionListener(e -> {
      dispose();
    });

    add(exitButton);

    setLocationRelativeTo(parent);
  }
}