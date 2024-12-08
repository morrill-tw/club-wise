package ui;

import java.awt.*;
import javax.swing.*;
import app.App;
import app.Event;
import app.Member;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

public class AddEventForm extends JDialog {
  private App observer;
  private JTextField eventName;
  private JTextField eventDescription;
  private JComboBox<String> monthComboBox;
  private JComboBox<Integer> dayComboBox;
  private JComboBox<Integer> yearComboBox;
  private JButton submitButton;
  private JButton cancelButton;
  private String clubName;

  public AddEventForm(JFrame parent, App observer) {
    super(parent, "Add Event", true);

    setSize(350, 280);

    // Prevent resizing
    setResizable(false);

    // Subscribe observer
    this.observer = observer;

    // Set the layout and initialize components
    setLayout(new FlowLayout());

    eventName = new JTextField(23);
    eventDescription = new JTextField(25);
    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    // Create Month ComboBox
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    monthComboBox = new JComboBox<>(months);

    // Create Year ComboBox (current year and next 10 years)
    int currentYear = LocalDate.now().getYear();
    Integer[] years = new Integer[11];
    for (int i = 0; i < 11; i++) {
      years[i] = currentYear + i;
    }
    yearComboBox = new JComboBox<>(years);

    // Create Day ComboBox
    dayComboBox = new JComboBox<>();

    // Add components to the form
    add(new JLabel("Event Name:"));
    add(eventName);
    add(new JLabel("Event Description:"));
    add(eventDescription);
    add(new JLabel("Date of Event:                                                                 "));
    add(new JLabel("Month:"));
    add(monthComboBox);
    add(new JLabel("Day:"));
    add(dayComboBox);
    add(new JLabel("Year:"));
    add(yearComboBox);
    add(submitButton);
    add(cancelButton);

    // Action listeners
    monthComboBox.addActionListener(e -> updateDays());
    yearComboBox.addActionListener(e -> updateDays());

    submitButton.addActionListener(e -> submitForm());
    cancelButton.addActionListener(e -> cancelForm());

    updateDays(); // Initialize days based on the default month and year

    setSize(350, 250);
    setLocationRelativeTo(parent); // Centers the form relative to the parent window
  }

  private void updateDays() {
    int monthIndex = monthComboBox.getSelectedIndex();
    int year = (int) yearComboBox.getSelectedItem();

    // Get the number of days in the selected month
    Month month = Month.of(monthIndex + 1);
    int daysInMonth = month.length(LocalDate.of(year, month, 1).isLeapYear());

    // Update the day combo box with valid days
    dayComboBox.removeAllItems();
    for (int i = 1; i <= daysInMonth; i++) {
      dayComboBox.addItem(i);
    }
  }

  private void submitForm() {
    // Extract the information from the form
    String firstName = eventName.getText();
    String eventDescriptionText = eventDescription.getText();
    int monthIndex = monthComboBox.getSelectedIndex();
    int day = (int) dayComboBox.getSelectedItem();
    int year = (int) yearComboBox.getSelectedItem();

    // Process the data, like adding the member to a list or database
    observer.addEvent(new Event(firstName, eventDescriptionText, new Date(day, monthIndex, year),
            this.clubName));

    // Close the dialog after submission
    dispose();
  }

  private void cancelForm() {
    // Close the dialog without doing anything
    dispose();
  }

  public void setClub(String clubName) {
    this.clubName = clubName;
    System.out.print(clubName);
  }
}