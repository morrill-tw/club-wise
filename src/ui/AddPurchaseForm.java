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

    setResizable(false);

    this.observer = observer;

    this.clubName = clubName;

    setLayout(new FlowLayout());

    title = new JTextField(25);
    cost = new JTextField(25);
    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    cost.setInputVerifier(new InputVerifier() {
      @Override
      public boolean verify(JComponent input) {
        try {
          Double.parseDouble(cost.getText());
          return true;
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(AddPurchaseForm.this, "Please enter a valid number for the cost.");
          return false;
        }
      }
    });

    add(new JLabel("Title:"));
    add(title);
    add(new JLabel("Cost:"));
    add(cost);
    add(submitButton);
    add(cancelButton);

    submitButton.addActionListener(e -> submitForm());
    cancelButton.addActionListener(e -> cancelForm());

    setSize(350, 250);
    setLocationRelativeTo(parent);
  }

  private void submitForm() {
    String titleText = title.getText();
    Double costDec = Double.parseDouble(cost.getText());

    observer.addPurchase(clubName, new Purchase(-1, titleText, costDec, clubName));

    dispose();
  }

  private void cancelForm() {
    dispose();
  }
}
