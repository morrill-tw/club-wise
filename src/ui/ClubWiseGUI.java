package ui;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import app.ActiveFilter;
import app.App;
import app.CategoryFilter;
import app.Club;
import app.EmptyFilter;
import app.Event;
import app.Member;
import app.Purchase;
import app.SocialMedia;

public class ClubWiseGUI extends JFrame implements UI {
  private final JPanel cards;
  private App observer;

  public ClubWiseGUI() {
    setTitle("ClubWise Application");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 400);

    cards = new JPanel(new CardLayout());

    setContentPane(cards);

    setVisible(true);

    setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

  public void setObserver(App observer) {
    this.observer = observer;
  }

  @Override
  public String[] promptUserForCredentials() {
    String[] credentials = new String[2];
    credentials[0] = JOptionPane.showInputDialog(this, "Enter username:");
    credentials[1] = JOptionPane.showInputDialog(this, "Enter password:");
    return credentials;
  }

  @Override
  public void refreshClubs(List<Club> clubs) {
    Collections.reverse(clubs);
    JSplitPane splitPane = ((JSplitPane) ((JPanel) cards.getComponent(0)).getComponent(0));
    splitPane.setRightComponent(createClubsPanel(clubs));
    splitPane.setDividerLocation(splitPane.getDividerLocation());
  }

  @Override
  public void displayClubs(List<Club> clubs) {
    Collections.reverse(clubs);
    cards.removeAll();
    JPanel mainContainer = new JPanel();
    mainContainer.setLayout(new BorderLayout());

    JPanel filterPanel = new JPanel();
    filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
    filterPanel.setPreferredSize(new Dimension(200, 600));
    filterPanel.setBackground(new Color(200, 200, 215));
    filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel filterLabel = new JLabel("Filters:");
    filterLabel.setFont(new Font("Arial", Font.BOLD, 16));
    filterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    filterLabel.setOpaque(true);
    filterLabel.setBackground(new Color(200, 200, 215));
    filterPanel.add(filterLabel);

    JCheckBox activeFilter = new JCheckBox("Active Clubs Only");
    activeFilter.setFont(new Font("Arial", Font.PLAIN, 14));
    activeFilter.setAlignmentX(Component.LEFT_ALIGNMENT);
    activeFilter.setOpaque(false);
    filterPanel.add(activeFilter);

    activeFilter.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        observer.filterClubs(new ActiveFilter());
      } else {
        observer.filterClubs(new EmptyFilter());
      }
    });

    JScrollPane scrollPane = createClubsPanel(clubs);

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filterPanel, scrollPane);
    splitPane.setDividerLocation(200);
    splitPane.setResizeWeight(0.2);
    splitPane.setOneTouchExpandable(true);

    mainContainer.add(splitPane, BorderLayout.CENTER);

    cards.add(mainContainer, "Clubs");

    CardLayout cardLayout = (CardLayout) cards.getLayout();
    cardLayout.show(cards, "Clubs");

    revalidate();
    repaint();
  }

  private JScrollPane createClubsPanel(List<Club> clubs) {
    JPanel clubsContainer = new JPanel();
    clubsContainer.setLayout(new GridLayout(0, 1));
    clubsContainer.setBackground(new Color(100, 100, 255));

    JButton addClubButton = new JButton("+");
    addClubButton.setFont(new Font("Arial", Font.BOLD, 48));
    addClubButton.setForeground(new Color(100, 115, 225));
    addClubButton.setPreferredSize(new Dimension(300, 300));
    addClubButton.setFocusPainted(false);
    addClubButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 115, 225), 10),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    addClubButton.addActionListener(e -> {
      AddClubForm addClubForm = new AddClubForm(this, observer);
      addClubForm.setVisible(true);
    });
    clubsContainer.add(addClubButton);

    for (Club club : clubs) {
      clubsContainer.add(createClubCard(club));
    }

    JScrollPane scrollPane = new JScrollPane(clubsContainer);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
    return scrollPane;
  }

  private JPanel createClubCard(Club club) {
    JPanel clubPanel = new JPanel();
    clubPanel.setLayout(new BorderLayout());
    clubPanel.setPreferredSize(new Dimension(400, 200));

    clubPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 115, 225), 10),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setOpaque(false);

    JLabel nameLabel = new JLabel(club.getName());
    nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
    infoPanel.add(nameLabel);

    JLabel activeStatusLabel = new JLabel((club.getActiveStatus() ? "Active" : "Inactive"));
    activeStatusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
    if (club.getActiveStatus()) {
      activeStatusLabel.setForeground(new Color(50, 175, 50));
    } else {
      activeStatusLabel.setForeground(Color.RED);
    }
    infoPanel.add(activeStatusLabel);

    JLabel categoryLabel = new JLabel(club.getCategory());
    categoryLabel.setFont(new Font("Arial", Font.ITALIC, 18));
    infoPanel.add(categoryLabel);

    JLabel descriptionLabel = new JLabel(club.getDescription());
    descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
    infoPanel.add(descriptionLabel);

    clubPanel.add(infoPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.setOpaque(false);

    JButton editClub = new JButton("Edit Club");
    editClub.setBackground(new Color(255, 200, 0));

    editClub.addActionListener(e -> {
      observer.openClubForm(club.getName());
    });
    buttonPanel.add(editClub);


    JButton socialsButton = new JButton("View Socials");

    socialsButton.addActionListener(e -> {
      observer.openSocialsDialog(null, club.getName());
    });
    buttonPanel.add(socialsButton);

    JButton purchasesButton = new JButton("View Purchases");

    purchasesButton.addActionListener(e -> {
      observer.openPurchasesDialog(null, club.getName());
    });
    buttonPanel.add(purchasesButton);

    JButton membersButton = new JButton("View Members");

    membersButton.addActionListener(e -> {
      observer.openMembersPage(club);
    });
    buttonPanel.add(membersButton);

    JButton eventsButton = new JButton("View Events");
    eventsButton.addActionListener(e -> {
      observer.openEventsPage(club);
    });
    buttonPanel.add(eventsButton);

    clubPanel.add(buttonPanel, BorderLayout.SOUTH);

    return clubPanel;
  }

  public void displaySocials (String clubName, List<SocialMedia> socials) {
    SocialsDialog socialsDialog = new SocialsDialog(this, observer, socials, clubName);
    socialsDialog.setVisible(true);
  }

  public void displayPurchases (String clubName, List<Purchase> purchases) {
    PurchasesDialog purchaseDialog = new PurchasesDialog(this, observer, purchases, clubName);
    purchaseDialog.setVisible(true);
  }

  @Override
  public void displayMembers(String clubName, List<Member> members) {
    cards.removeAll();

    JPanel membersContainer = new JPanel(new GridBagLayout());
    membersContainer.setBackground(new Color(100, 115, 225));
    membersContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.NORTHWEST;

    int row = 0;
    int col = 0;

    gbc.gridwidth = 1;
    JButton addMemberButton = new JButton("+");
    addMemberButton.setFont(new Font("Arial", Font.BOLD, 48));
    addMemberButton.setForeground(new Color(100, 115, 225));
    addMemberButton.setPreferredSize(new Dimension(300, 300));
    addMemberButton.setFocusPainted(false);
    addMemberButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 255), 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    addMemberButton.addActionListener(e -> {
      AddMemberForm addMemberForm = new AddMemberForm(this, observer);
      addMemberForm.setClub(clubName);
      addMemberForm.setVisible(true);
    });

    membersContainer.add(addMemberButton, gbc);
    col++;

    for (int i = 0; i < 2 && i < members.size(); i++) {
      gbc.gridwidth = 1;
      gbc.gridx = col;
      gbc.gridy = row;

      membersContainer.add(createMemberCard(members.get(i), clubName), gbc);

      col++;
    }

    if (col == 3) {
      col = 0;
      row++;
    }

    for (int i = 2; i < members.size(); i++) {
      gbc.gridwidth = 1;
      gbc.gridx = col;
      gbc.gridy = row;

      membersContainer.add(createMemberCard(members.get(i), clubName), gbc);

      col++;

      if (col == 3) {
        col = 0;
        row++;
      }
    }

    JScrollPane scrollPane = new JScrollPane(membersContainer);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

    JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    backButtonPanel.setBackground(new Color(100, 115, 225));

    JButton backButton = new JButton("Back to Clubs");
    backButton.setFont(new Font("Arial", Font.BOLD, 14));
    backButton.setFocusPainted(false);
    backButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

    backButton.addActionListener(e -> {
      observer.openClubsPage();
    });

    backButtonPanel.add(backButton);

    JLabel clubLabel = new JLabel(clubName + " - Members");
    clubLabel.setFont(new Font("Arial", Font.BOLD, 18));
    clubLabel.setForeground(Color.WHITE);
    clubLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    backButtonPanel.add(clubLabel);

    JPanel membersPage = new JPanel();
    membersPage.setLayout(new BorderLayout());
    membersPage.add(backButtonPanel, BorderLayout.NORTH);
    membersPage.add(scrollPane, BorderLayout.CENTER);

    cards.add(membersPage, "Members");

    CardLayout cardLayout = (CardLayout) cards.getLayout();
    cardLayout.show(cards, "Members");

    cards.revalidate();
    cards.repaint();
  }

  private JPanel createMemberCard(Member member, String clubName) {
    JPanel memberCard = new JPanel();
    memberCard.setLayout(new BorderLayout());
    memberCard.setPreferredSize(new Dimension(300, 300));
    memberCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 255), 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setOpaque(false);

    JLabel nameLabel = new JLabel(member.getFirstName() + " " + member.getLastName());
    nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(nameLabel);

    JLabel roleLabel = new JLabel(member.getRoleName());
    roleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
    roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(roleLabel);

    JLabel joinDateLabel = new JLabel("Joined: " + member.getJoinDate());
    joinDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    joinDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(joinDateLabel);

    JButton trashButton = new JButton("Delete Member");
    trashButton.setPreferredSize(new Dimension(40, 40));
    trashButton.setBackground(new Color(255, 130, 130));
    trashButton.setForeground(Color.WHITE);
    trashButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    trashButton.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(memberCard,
              "Are you sure you want to delete this member?",
              "Confirm Deletion",
              JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        observer.deleteMember(member);
        JOptionPane.showMessageDialog(memberCard, "Member deleted: " + member.getFirstName() + " " + member.getLastName());
      }
    });

    infoPanel.add(trashButton);


    JButton editButton = new JButton("Edit Member");
    editButton.setPreferredSize(new Dimension(40, 40));
    editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    nameLabel.setAlignmentY(Component.CENTER_ALIGNMENT);


    editButton.addActionListener(e -> {
      EditMemberForm memberForm = new EditMemberForm(this, observer, member.getId(), member.getRoleName(), member.getClubName());
      memberForm.setVisible(true);
    });
    infoPanel.add(editButton);

    memberCard.add(infoPanel, BorderLayout.CENTER);
    return memberCard;
  }

  private JPanel createEventCard(Event event, String clubName) {
    JPanel eventCard = new JPanel();
    eventCard.setLayout(new BorderLayout());
    eventCard.setPreferredSize(new Dimension(300, 300));
    eventCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 255), 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setOpaque(false);

    JLabel nameLabel = new JLabel(event.getEventTitle());
    nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(nameLabel);

    JLabel dateLabel = new JLabel("Date: " + event.getEventDate());
    dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(dateLabel);

    JTextArea roleTextArea = new JTextArea(event.getEventDescription());
    roleTextArea.setFont(new Font("Arial", Font.ITALIC, 16));
    roleTextArea.setLineWrap(true);
    roleTextArea.setWrapStyleWord(true);
    roleTextArea.setEditable(false);
    roleTextArea.setOpaque(false);
    roleTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
    roleTextArea.setPreferredSize(new Dimension(250, 60));
    roleTextArea.setMaximumSize(new Dimension(250, 100));

    roleTextArea.setAlignmentY(Component.CENTER_ALIGNMENT);
    roleTextArea.setCaretPosition(0);

    roleTextArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

    JPanel textPanel = new JPanel();
    textPanel.setLayout(new BorderLayout());
    textPanel.add(roleTextArea, BorderLayout.CENTER);

    textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    textPanel.setOpaque(false);
    infoPanel.add(textPanel);

    JButton trashButton = new JButton("Delete Event");
    trashButton.setPreferredSize(new Dimension(40, 40));
    trashButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    trashButton.setBackground(new Color(255, 130, 130));
    trashButton.setForeground(Color.WHITE);

    trashButton.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(eventCard,
              "Are you sure you want to delete this event?",
              "Confirm Deletion",
              JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        observer.deleteEvent(event);
        JOptionPane.showMessageDialog(eventCard, "Event deleted: " + event.getEventTitle());
      }
    });
    infoPanel.add(trashButton);


    JButton editButton = new JButton("Edit Event");
    editButton.setPreferredSize(new Dimension(40, 40));
    editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    nameLabel.setAlignmentY(Component.CENTER_ALIGNMENT);


    editButton.addActionListener(e -> {
      EditEventForm editForm = new EditEventForm(this, observer, event.getClubName(), event.getEventDate(), event.getEventTitle());
      editForm.setVisible(true);
    });
    infoPanel.add(editButton);

    eventCard.add(infoPanel, BorderLayout.CENTER);
    return eventCard;
  }

  @Override
  public void displayEvents(String clubName, List<Event> eventList) {
    cards.removeAll();
    JPanel eventsContainer = new JPanel(new GridBagLayout());
    eventsContainer.setBackground(new Color(100, 115, 225));
    eventsContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.NORTHWEST;

    int row = 0;
    int col = 0;


    gbc.gridwidth = 1;
    JButton addEventButton = new JButton("+");
    addEventButton.setFont(new Font("Arial", Font.BOLD, 48));
    addEventButton.setForeground(new Color(100, 115, 225));
    addEventButton.setPreferredSize(new Dimension(300, 300));
    addEventButton.setFocusPainted(false);
    addEventButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 255), 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    addEventButton.addActionListener(e -> {
      AddEventForm addEventForm = new AddEventForm(this, observer, clubName);
      addEventForm.setVisible(true);
    });

    eventsContainer.add(addEventButton, gbc);
    col++;

    for (int i = 0; i < 2 && i < eventList.size(); i++) {
      gbc.gridwidth = 1;
      gbc.gridx = col;
      gbc.gridy = row;

      eventsContainer.add(createEventCard(eventList.get(i), clubName), gbc);

      col++;
    }

    if (col == 3) {
      col = 0;
      row++;
    }

    for (int i = 2; i < eventList.size(); i++) {
      gbc.gridwidth = 1;
      gbc.gridx = col;
      gbc.gridy = row;

      eventsContainer.add(createEventCard(eventList.get(i), clubName), gbc);

      col++;

      if (col == 3) {
        col = 0;
        row++;
      }
    }

    JScrollPane scrollPane = new JScrollPane(eventsContainer);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

    JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    backButtonPanel.setBackground(new Color(100, 115, 225));

    JButton backButton = new JButton("Back to Clubs");
    backButton.setFont(new Font("Arial", Font.BOLD, 14));
    backButton.setFocusPainted(false);
    backButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

    backButton.addActionListener(e -> {
      observer.openClubsPage();
    });

    backButtonPanel.add(backButton);

    JLabel clubLabel = new JLabel(clubName + " - Events");
    clubLabel.setFont(new Font("Arial", Font.BOLD, 18));
    clubLabel.setForeground(Color.WHITE);
    clubLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    backButtonPanel.add(clubLabel);

    JPanel membersPage = new JPanel();
    membersPage.setLayout(new BorderLayout());
    membersPage.add(backButtonPanel, BorderLayout.NORTH);
    membersPage.add(scrollPane, BorderLayout.CENTER);

    cards.add(membersPage, "Members");

    CardLayout cardLayout = (CardLayout) cards.getLayout();
    cardLayout.show(cards, "Members");

    cards.revalidate();
    cards.repaint();
  }

  @Override
  public void displayClubForm(String name) {
    EditClubForm editForm = new EditClubForm(this, observer, name);
    editForm.setVisible(true);
  }
}
