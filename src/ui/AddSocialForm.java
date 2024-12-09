package ui;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.*;

import app.App;
import app.Event;
import app.SocialMedia;

public class AddSocialForm extends JDialog {
  private App observer;
  private JTextField platform;
  private JTextField username;
  private JButton submitButton;
  private JButton cancelButton;
  private String clubName;

  public AddSocialForm(JFrame parent, App observer, String clubName) {
    super(parent, "Add Social", true);

    setSize(350, 280);

    // Prevent resizing
    setResizable(false);

    // Subscribe observer
    this.observer = observer;

    this.clubName = clubName;

    // Set the layout and initialize components
    setLayout(new FlowLayout());

    platform = new JTextField(25);
    username = new JTextField(25);
    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    // Add components to the form
    add(new JLabel("Platform:"));
    add(platform);
    add(new JLabel("Username:"));
    add(username);
    add(submitButton);
    add(cancelButton);

    submitButton.addActionListener(e -> submitForm());
    cancelButton.addActionListener(e -> cancelForm());

    setSize(350, 250);
    setLocationRelativeTo(parent); // Centers the form relative to the parent window
  }

  private void submitForm() {
    // Extract the information from the form
    String platformText = platform.getText();
    String usernameText = username.getText();

    // Process the data, like adding the event to a list or database
    observer.addSocial(clubName, new SocialMedia(platformText, usernameText, clubName));

    // Close the dialog after submission
    dispose();
  }

  private void cancelForm() {
    // Close the dialog without doing anything
    dispose();
  }
}
