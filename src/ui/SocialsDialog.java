package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import app.App;
import app.SocialMedia;

public class SocialsDialog extends JDialog {
  private App observer;
  private JButton exitButton;
  private List<SocialMedia> socials;
  private String clubName;
  private static SocialsDialog currentDialog = null;

  public SocialsDialog(JFrame parent, App observer, List<SocialMedia> socials, String clubName) {
    super(parent, "Socials", true);

    setSize(350, 280);
    setResizable(false);

    this.clubName = clubName;
    this.socials = socials;
    this.observer = observer;

    setLayout(new BorderLayout());

    JPanel socialsPanel = new JPanel();
    socialsPanel.setLayout(new BoxLayout(socialsPanel, BoxLayout.Y_AXIS));

    for (SocialMedia social : socials) {
      socialsPanel.add(createSocialCard(social));
    }

    JScrollPane scrollPane = new JScrollPane(socialsPanel);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    add(scrollPane, BorderLayout.CENTER);

    JPanel addPanel = new JPanel();
    addPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    JButton addSocialButton = new JButton("+");
    addSocialButton.setFont(new Font("Arial", Font.BOLD, 20));
    addSocialButton.setForeground(new Color(100, 115, 225));
    addSocialButton.setSize(new Dimension(50, 20));
    addSocialButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 255), 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    addSocialButton.addActionListener(e -> {
      if (currentDialog != null) {
        currentDialog.dispose();
      }
      AddSocialForm addSocialForm = new AddSocialForm(parent, observer, clubName);
      addSocialForm.setVisible(true);
    });
    addPanel.add(addSocialButton);
    add(addPanel, BorderLayout.NORTH);

    JPanel exitPanel = new JPanel();
    exitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    exitButton = new JButton("Exit");
    exitButton.setPreferredSize(new Dimension(100, 30));
    exitButton.setMargin(new Insets(5, 10, 5, 10));
    exitButton.addActionListener(e -> {
      dispose();
    });

    exitPanel.add(exitButton);

    add(exitPanel, BorderLayout.SOUTH);

    setLocationRelativeTo(parent);

    currentDialog = this;
  }

  private JPanel createSocialCard(SocialMedia social) {
    JPanel socialCard = new JPanel();
    socialCard.setPreferredSize(new Dimension(300, 40));
    socialCard.setLayout(new BoxLayout(socialCard, BoxLayout.X_AXIS));
    socialCard.setBorder(new EmptyBorder(5, 10, 5, 10));

    JLabel socialLabel = new JLabel(social.getPlatform() + ": " + social.getUsername());
    socialLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    socialCard.add(socialLabel);

    socialCard.add(Box.createHorizontalStrut(10));

    JButton trashButton = new JButton("Delete");
    trashButton.setSize(new Dimension(100, 40));
    trashButton.setBackground(new Color(255, 130, 130));
    trashButton.setForeground(Color.WHITE);
    trashButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    trashButton.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(
              socialCard,
              "Are you sure you want to delete this member?",
              "Confirm Deletion",
              JOptionPane.YES_NO_OPTION
      );

      if (confirm == JOptionPane.YES_OPTION) {
        observer.deleteSocial(social);
        dispose();
        SwingUtilities.invokeLater(() -> {
          observer.openSocialsDialog(this, social.getClubName());
        });
      }
    });
    socialCard.add(trashButton);

    return socialCard;
  }

  public static void openSocialsDialog(JFrame parent, App observer, List<SocialMedia> socials, String clubName) {
    if (currentDialog != null) {
      currentDialog.dispose();
    }
    currentDialog = new SocialsDialog(parent, observer, socials, clubName);
    currentDialog.setVisible(true);
  }
}