package ui;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.*;

import app.App;
import app.Club;
import app.Event;

public class EditClubForm extends JDialog {
  private final String name;
  private App observer;
  private JTextField clubName;
  private JTextField clubDescription;
  private JComboBox<Boolean> activeStatusComboBox;
  private JComboBox<String> categoryComboBox;
  private JButton submitButton;
  private JButton cancelButton;

  public EditClubForm(JFrame parent, App observer, String name) {
    super(parent, "Edit Club", true);
    this.name = name;
    setSize(350, 280);

    setResizable(false);

    this.observer = observer;

    setLayout(new FlowLayout());

    clubName = new JTextField(23);
    clubDescription = new JTextField(25);
    activeStatusComboBox = new JComboBox(new String[]{"Active", "Inactive"});
    String[] categories = {"STEM",
            "Arts and Culture",
            "Sports and Recreation",
            "Community Service and Social Impact",
            "Social and Special Interests",
            "Media and Communications",
            "Religious and Spiritual"};
    categoryComboBox = new JComboBox(categories);
    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    add(new JLabel("Club Description:"));
    add(clubDescription);
    add(new JLabel("Status:"));
    add(activeStatusComboBox);
    add(new JLabel("Category:"));
    add(categoryComboBox);
    add(submitButton);
    add(cancelButton);

    submitButton.addActionListener(e -> submitForm());
    cancelButton.addActionListener(e -> cancelForm());

    setSize(350, 250);
    setLocationRelativeTo(parent); // Centers the form relative to the parent window
  }

  private void submitForm() {
    // Extract the information from the form
    String description = clubDescription.getText();
    Boolean status;
    if (activeStatusComboBox.getSelectedIndex() == 0) {
      status = true;
    } else {
      status = false;
    }
    String category = (String) categoryComboBox.getSelectedItem();

    // Process the data, like adding the event to a list or database
    observer.editClub(new Club(this.name, description, status, category));

    dispose();
  }

  private void cancelForm() {
    dispose();
  }

}
