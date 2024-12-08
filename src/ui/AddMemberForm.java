package ui;

import java.awt.*;
import javax.swing.*;
import app.App;
import app.Member;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

public class AddMemberForm extends JDialog {
  private App observer;
  private JTextField firstNameField;
  private JTextField lastNameField;
  private JTextField roleField;
  private JComboBox<String> monthComboBox;
  private JComboBox<Integer> dayComboBox;
  private JComboBox<Integer> yearComboBox;
  private JButton submitButton;
  private JButton cancelButton;
  private String clubName;

  public AddMemberForm(JFrame parent, App observer) {
    super(parent, "Add Member", true);

    setSize(350, 280);
    setResizable(false);

    this.observer = observer;
    setLayout(new FlowLayout());

    firstNameField = new JTextField(23);
    lastNameField = new JTextField(23);
    roleField = new JTextField(25);
    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    monthComboBox = new JComboBox<>(months);

    int currentYear = LocalDate.now().getYear();
    Integer[] years = new Integer[11];
    for (int i = 0; i < 11; i++) {
      years[i] = currentYear - i;
    }
    yearComboBox = new JComboBox<>(years);

    dayComboBox = new JComboBox<>();

    add(new JLabel("First Name:"));
    add(firstNameField);
    add(new JLabel("Last Name:"));
    add(lastNameField);
    add(new JLabel("Role:"));
    add(roleField);
    add(new JLabel("Date Joined:"));
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
    setLocationRelativeTo(parent);
  }

  private void updateDays() {
    int monthIndex = monthComboBox.getSelectedIndex();
    int year = (int) yearComboBox.getSelectedItem();

    Month month = Month.of(monthIndex + 1); // Month is 1-based
    int daysInMonth = month.length(LocalDate.of(year, 1, 1).isLeapYear());

    dayComboBox.removeAllItems();
    for (int i = 1; i <= daysInMonth; i++) {
      dayComboBox.addItem(i);
    }
  }

  private void submitForm() {
    String firstName = firstNameField.getText();
    String lastName = lastNameField.getText();
    String role = roleField.getText();
    int monthIndex = monthComboBox.getSelectedIndex();
    int day = (int) dayComboBox.getSelectedItem();
    int year = (int) yearComboBox.getSelectedItem();

    LocalDate localDate = LocalDate.of(year, monthIndex + 1, day);
    Date sqlDate = Date.valueOf(localDate); // Convert LocalDate to java.sql.Date

    observer.addMember(new Member(-1, firstName, lastName, sqlDate, role, clubName));
    dispose();
  }

  private void cancelForm() {
    dispose();
  }

  public void setClub(String clubName) {
    this.clubName = clubName;
  }
}