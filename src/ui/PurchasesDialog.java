package ui;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import app.App;
import app.Purchase;

public class PurchasesDialog extends JDialog {
  private App observer;
  private JButton exitButton;
  private List<Purchase> purchases;
  private String clubName;
  private static PurchasesDialog currentDialog = null; // Track the current dialog

  public PurchasesDialog(JFrame parent, App observer, List<Purchase> purchases, String clubName) {
    super(parent, "Purchases", true);

    setSize(350, 280);
    setResizable(false);

    this.clubName = clubName;
    this.purchases = purchases;
    this.observer = observer;

    setLayout(new BorderLayout());

    JPanel purchasesPanel = new JPanel();
    purchasesPanel.setLayout(new BoxLayout(purchasesPanel, BoxLayout.Y_AXIS));

    for (Purchase purchase : purchases) {
      purchasesPanel.add(createPurchaseCard(purchase));
    }

    JScrollPane scrollPane = new JScrollPane(purchasesPanel);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    add(scrollPane, BorderLayout.CENTER);

    // Create a panel for the "Add" button
    JPanel addPanel = new JPanel();
    addPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    JButton addPurchaseButton = new JButton("+");
    addPurchaseButton.setFont(new Font("Arial", Font.BOLD, 20));
    addPurchaseButton.setForeground(new Color(100, 115, 225));
    addPurchaseButton.setSize(new Dimension(50, 20));
    addPurchaseButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 255), 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    addPurchaseButton.addActionListener(e -> {
      // If a dialog is already open, close it before opening a new one
      if (currentDialog != null) {
        currentDialog.dispose();
      }
      AddPurchaseForm addPurchaseForm = new AddPurchaseForm(parent, observer, clubName);
      addPurchaseForm.setVisible(true);
    });
    addPanel.add(addPurchaseButton);
    add(addPanel, BorderLayout.NORTH);

    // Create a panel to center the exit button
    JPanel exitPanel = new JPanel();
    exitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    // Add exit button to the panel
    exitButton = new JButton("Exit");
    exitButton.setPreferredSize(new Dimension(100, 30));
    exitButton.setMargin(new Insets(5, 10, 5, 10));
    exitButton.addActionListener(e -> {
      dispose();
    });

    exitPanel.add(exitButton);

    // Add the exit panel to the bottom (SOUTH) of the dialog
    add(exitPanel, BorderLayout.SOUTH);

    setLocationRelativeTo(parent);

    // Save the reference of the current dialog instance
    currentDialog = this;
  }

  private JPanel createPurchaseCard(Purchase purchase) {
    JPanel purchaseCard = new JPanel();
    purchaseCard.setPreferredSize(new Dimension(300, 40));
    purchaseCard.setLayout(new BoxLayout(purchaseCard, BoxLayout.X_AXIS));
    purchaseCard.setBorder(new EmptyBorder(5, 10, 5, 10));

    JLabel purchaseLabel = new JLabel(purchase.getTitle() + ": $" + purchase.getCost());
    purchaseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    purchaseCard.add(purchaseLabel);

    purchaseCard.add(Box.createHorizontalStrut(10));

    JButton trashButton = new JButton("Delete");
    trashButton.setSize(new Dimension(100, 40));
    trashButton.setBackground(new Color(255, 130, 130));
    trashButton.setForeground(Color.WHITE);
    trashButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    trashButton.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(
              purchaseCard,
              "Are you sure you want to delete this member?",
              "Confirm Deletion",
              JOptionPane.YES_NO_OPTION
      );

      if (confirm == JOptionPane.YES_OPTION) {
        observer.deletePurchase(purchase);
        dispose(); // Close the current dialog
        SwingUtilities.invokeLater(() -> {
          observer.openPurchasesDialog(this, purchase.getClubName()); // Reopen the updated dialog
        });
      }
    });
    purchaseCard.add(trashButton);

    return purchaseCard;
  }

  // A method to update the current dialog with new data if needed
  public static void openPurchasesDialog(JFrame parent, App observer, List<Purchase> purchases, String clubName) {
    if (currentDialog != null) {
      currentDialog.dispose(); // Close the existing dialog
    }
    currentDialog = new PurchasesDialog(parent, observer, purchases, clubName);
    currentDialog.setVisible(true); // Open the new dialog
  }
}
