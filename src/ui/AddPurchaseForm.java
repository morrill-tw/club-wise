package ui;

import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.*;

import app.App;
import app.Purchase;

public class AddPurchaseForm extends JDialog {
  private App observer;
  private JTextField title;
  private JTextField cost;
  private JButton submitButton;
  private JButton cancelButton;
  private String clubName;

  public AddPurchaseForm(JFrame parent, App observer, String clubName) {
    super(parent, "Add Purchase", true);

    setSize(350, 280);

    // Prevent resizing
    setResizable(false);

    // Subscribe observer
    this.observer = observer;

    this.clubName = clubName;

    // Set the layout and initialize components
    setLayout(new FlowLayout());

    title = new JTextField(25);
    cost = new JTextField(25);
    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    // Add input verifier to cost text field to only allow numbers
    cost.setInputVerifier(new InputVerifier() {
      @Override
      public boolean verify(JComponent input) {
        try {
          // Try to parse the text as a valid number
          Double.parseDouble(cost.getText());
          return true;
        } catch (NumberFormatException e) {
          // Show an error dialog if it's not a valid number
          JOptionPane.showMessageDialog(AddPurchaseForm.this, "Please enter a valid number for the cost.");
          return false;
        }
      }
    });

    // Add components to the form
    add(new JLabel("Title:"));
    add(title);
    add(new JLabel("Cost:"));
    add(cost);
    add(submitButton);
    add(cancelButton);

    submitButton.addActionListener(e -> submitForm());
    cancelButton.addActionListener(e -> cancelForm());

    setSize(350, 250);
    setLocationRelativeTo(parent); // Centers the form relative to the parent window
  }

  private void submitForm() {
    // Extract the information from the form
    String titleText = title.getText();
    Double costDec = Double.parseDouble(cost.getText()); // We now know the cost is a valid number

    // Process the data, like adding the event to a list or database
    observer.addPurchase(clubName, new Purchase(-1, titleText, costDec, clubName));

    // Close the dialog after submission
    dispose();
  }

  private void cancelForm() {
    // Close the dialog without doing anything
    dispose();
  }
}
