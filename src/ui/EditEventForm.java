package ui;

import java.awt.*;
import javax.swing.*;
import app.App;
import app.Event;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

public class EditEventForm extends JDialog {
  private Date eventDate;
  private final String eventTitle;
  private App observer;
  private JTextField eventName;
  private JTextField eventDescription;
  private JComboBox<String> monthComboBox;
  private JComboBox<Integer> dayComboBox;
  private JComboBox<Integer> yearComboBox;
  private JButton submitButton;
  private JButton cancelButton;
  private String clubName;

  public EditEventForm(JFrame parent, App observer, String clubName, Date eventDate, String eventTitle) {
    super(parent, "Add Event", true);
    setSize(350, 280);

    this.clubName = clubName;
    this.eventDate = eventDate;
    this.eventTitle = eventTitle;
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

    add(new JLabel("Event Description:"));
    add(eventDescription);
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
    int monthIndex = monthComboBox.getSelectedIndex(); // Zero-based index
    int year = (int) yearComboBox.getSelectedItem();

    // Get the number of days in the selected month
    Month month = Month.of(monthIndex + 1); // Convert zero-based index to one-based Month
    int daysInMonth = month.length(LocalDate.of(year, month, 1).isLeapYear());

    // Update the day combo box with valid days
    dayComboBox.removeAllItems();
    for (int i = 1; i <= daysInMonth; i++) {
      dayComboBox.addItem(i);
    }
  }

  private void submitForm() {
    // Extract the information from the form
    String eventNameText = eventName.getText();
    String eventDescriptionText = eventDescription.getText();
    int monthIndex = monthComboBox.getSelectedIndex(); // Zero-based
    int day = (int) dayComboBox.getSelectedItem();
    int year = (int) yearComboBox.getSelectedItem();

    // Construct a proper LocalDate object
    LocalDate eventDate = LocalDate.of(year, monthIndex + 1, day); // Month is one-based in LocalDate

    // Convert to java.sql.Date for database storage
    Date sqlDate = Date.valueOf(eventDate);

    // Process the data, like adding the event to a list or database
    observer.editEvent(new Event(eventTitle, eventDescriptionText, this.eventDate, clubName));

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