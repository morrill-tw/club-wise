package ui;

import java.awt.*;
import javax.swing.*;
import app.App;
import app.Event;

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

  public AddEventForm(JFrame parent, App observer, String clubName) {
    super(parent, "Add Event", true);

    System.out.println("Form created with clubName: " + clubName);

    setSize(350, 280);

    setResizable(false);

    this.observer = observer;

    this.clubName = clubName;

    setLayout(new FlowLayout());

    eventName = new JTextField(23);
    eventDescription = new JTextField(25);
    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    monthComboBox = new JComboBox<>(months);

    int currentYear = LocalDate.now().getYear();
    Integer[] years = new Integer[11];
    for (int i = 0; i < 11; i++) {
      years[i] = currentYear + i;
    }
    yearComboBox = new JComboBox<>(years);

    dayComboBox = new JComboBox<>();

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

    monthComboBox.addActionListener(e -> updateDays());
    yearComboBox.addActionListener(e -> updateDays());

    submitButton.addActionListener(e -> submitForm());
    cancelButton.addActionListener(e -> cancelForm());

    updateDays();

    setSize(350, 250);
    setLocationRelativeTo(parent);
  }

  private void updateDays() {
    int monthIndex = monthComboBox.getSelectedIndex();
    int year = (int) yearComboBox.getSelectedItem();

    Month month = Month.of(monthIndex + 1);
    int daysInMonth = month.length(LocalDate.of(year, month, 1).isLeapYear());

    dayComboBox.removeAllItems();
    for (int i = 1; i <= daysInMonth; i++) {
      dayComboBox.addItem(i);
    }
  }

  private void submitForm() {
    String eventNameText = eventName.getText();
    String eventDescriptionText = eventDescription.getText();
    int monthIndex = monthComboBox.getSelectedIndex();
    int day = (int) dayComboBox.getSelectedItem();
    int year = (int) yearComboBox.getSelectedItem();

    LocalDate eventDate = LocalDate.of(year, monthIndex + 1, day);

    Date sqlDate = Date.valueOf(eventDate);

    observer.addEvent(new Event(eventNameText, eventDescriptionText, sqlDate, clubName));

    dispose();
  }

  private void cancelForm() {
    dispose();
  }

}