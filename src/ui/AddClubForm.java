package ui;

import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.*;

import app.App;
import app.Club;
import app.Event;

public class AddClubForm extends JDialog {
  private App observer;
  private JTextField clubName;
  private JTextField clubDescription;
  private JComboBox<Boolean> activeStatusComboBox;
  private JComboBox<String> categoryComboBox;
  private JButton submitButton;
  private JButton cancelButton;

  public AddClubForm(JFrame parent, App observer) {
    super(parent, "Add Club", true);

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

    add(new JLabel("Club Name:"));
    add(clubName);
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
    setLocationRelativeTo(parent);
  }

  private void submitForm() {
    String name = clubName.getText();
    String description = clubDescription.getText();
    Boolean status;
    if (activeStatusComboBox.getSelectedIndex() == 0) {
      status = true;
    } else {
      status = false;
    }
    String category = (String) categoryComboBox.getSelectedItem();

    observer.addClub(new Club(name, description, status, category));

    dispose();
  }

  private void cancelForm() {
    dispose();
  }

}
