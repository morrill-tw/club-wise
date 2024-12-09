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
import app.SocialMedia;

public class ClubWiseGUI extends JFrame implements UI {
  private final JPanel cards;
  private App observer;

  public ClubWiseGUI() {
    // Set up the frame
    setTitle("ClubWise Application");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 400);

    // Create cards panel
    cards = new JPanel(new CardLayout());

    // Set the cards panel as the content pane of the frame
    setContentPane(cards);

    // Make the frame visible
    setVisible(true);

    setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

  public void setObserver(App observer) {
    this.observer = observer;
  }

  @Override
  public String[] promptUserForCredentials() {
    // Implement the method to prompt the user for credentials, e.g., username and password
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
    // Create the main container for the clubs view
    JPanel mainContainer = new JPanel();
    mainContainer.setLayout(new BorderLayout());

    // Create the filter panel
    JPanel filterPanel = new JPanel();
    filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS)); // Stack filters vertically
    filterPanel.setPreferredSize(new Dimension(200, 600)); // Set a fixed width
    filterPanel.setBackground(new Color(200, 200, 215));
    filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Add filter options to the filter panel
    JLabel filterLabel = new JLabel("Filters:");
    filterLabel.setFont(new Font("Arial", Font.BOLD, 16));
    filterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    filterLabel.setOpaque(true);
    filterLabel.setBackground(new Color(200, 200, 215)); // Match the filter panel background
    filterPanel.add(filterLabel);

    JCheckBox activeFilter = new JCheckBox("Active Clubs Only");
    activeFilter.setFont(new Font("Arial", Font.PLAIN, 14));
    activeFilter.setAlignmentX(Component.LEFT_ALIGNMENT);
    activeFilter.setOpaque(false); // Transparent background to blend with panel
    filterPanel.add(activeFilter);

    activeFilter.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        observer.filterClubs(new ActiveFilter());
      } else {
        observer.filterClubs(new EmptyFilter());
      }
    });

    JScrollPane scrollPane = createClubsPanel(clubs);

    // Combine filter panel and scroll pane into a JSplitPane
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, filterPanel, scrollPane);
    splitPane.setDividerLocation(200); // Initial position of the divider
    splitPane.setResizeWeight(0.2); // Allocate 20% of space to the filter panel
    splitPane.setOneTouchExpandable(true); // Add buttons to toggle panel visibility

    // Add the split pane to the main container
    mainContainer.add(splitPane, BorderLayout.CENTER);

    // Add the main container to the cards panel
    cards.add(mainContainer, "Clubs");

    // Optionally switch to the clubs page if needed
    CardLayout cardLayout = (CardLayout) cards.getLayout();
    cardLayout.show(cards, "Clubs");

    revalidate();
    repaint();
  }

  private JScrollPane createClubsPanel(List<Club> clubs) {
    // Create the clubs container
    JPanel clubsContainer = new JPanel();
    clubsContainer.setLayout(new GridLayout(0, 1)); // Stack clubs vertically
    clubsContainer.setBackground(new Color(100, 100, 255));

    JButton addClubButton = new JButton("+");
    addClubButton.setFont(new Font("Arial", Font.BOLD, 48));
    addClubButton.setForeground(new Color(100, 115, 225));
    addClubButton.setPreferredSize(new Dimension(300, 300)); // Fixed size for the button
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

    // Loop through each club and create a panel for it
    for (Club club : clubs) {
      // Add this club panel to the container
      clubsContainer.add(createClubCard(club));
    }

    // Create a scroll pane for the clubs container
    JScrollPane scrollPane = new JScrollPane(clubsContainer);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
    return scrollPane;
  }

  private JPanel createClubCard(Club club) {
    JPanel clubPanel = new JPanel();
    clubPanel.setLayout(new BorderLayout());
    clubPanel.setPreferredSize(new Dimension(400, 200));

    // Add padding and borders to the club panel
    clubPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 115, 225), 10),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    // Info panel
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

    // Button panel
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

  @Override
  public void displayMembers(String clubName, List<Member> members) {
    // Clear existing components
    cards.removeAll();

    // Create a container panel using GridBagLayout
    JPanel membersContainer = new JPanel(new GridBagLayout());
    membersContainer.setBackground(new Color(100, 115, 225));
    membersContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Space between cards
    gbc.fill = GridBagConstraints.NONE; // No resizing
    gbc.anchor = GridBagConstraints.NORTHWEST; // Align components to top-left

    int row = 0; // Starting at the first row
    int col = 0; // Starting at the first column

    // Add "Add Member" button in the first row, first column
    gbc.gridwidth = 1; // Button takes up 1 column
    JButton addMemberButton = new JButton("+");
    addMemberButton.setFont(new Font("Arial", Font.BOLD, 48));
    addMemberButton.setForeground(new Color(100, 115, 225));
    addMemberButton.setPreferredSize(new Dimension(300, 300)); // Fixed size for the button
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
    col++; // Move to next column

    // Add the first two member cards to the same row, filling the rest of the row
    for (int i = 0; i < 2 && i < members.size(); i++) {
      gbc.gridwidth = 1;  // Each card takes up 1 column
      gbc.gridx = col;
      gbc.gridy = row;

      // Add the member card to the grid
      membersContainer.add(createMemberCard(members.get(i), clubName), gbc);

      col++;  // Move to next column
    }

    // Move to the next row if the first row is full
    if (col == 3) {
      col = 0;  // Reset column index to 0
      row++;  // Move to the next row
    }

    // Add remaining member cards after the first 3 columns are filled
    for (int i = 2; i < members.size(); i++) {
      gbc.gridwidth = 1;  // Each card takes up 1 column
      gbc.gridx = col;
      gbc.gridy = row;

      // Add the member card to the grid
      membersContainer.add(createMemberCard(members.get(i), clubName), gbc);

      col++;  // Move to next column

      // After every 3 columns, move to the next row
      if (col == 3) {
        col = 0;
        row++;
      }
    }

    // Wrap the container in a scroll pane
    JScrollPane scrollPane = new JScrollPane(membersContainer);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

    // Create a panel for the back button
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

    // Create the main members page
    JPanel membersPage = new JPanel();
    membersPage.setLayout(new BorderLayout());
    membersPage.add(backButtonPanel, BorderLayout.NORTH); // Back button at the top
    membersPage.add(scrollPane, BorderLayout.CENTER);     // Scrollable container

    // Add the members page to the cards panel
    cards.add(membersPage, "Members");

    // Switch to the members page
    CardLayout cardLayout = (CardLayout) cards.getLayout();
    cardLayout.show(cards, "Members");

    // Repaint and revalidate
    cards.revalidate();
    cards.repaint();
  }

  private JPanel createMemberCard(Member member, String clubName) {
    JPanel memberCard = new JPanel();
    memberCard.setLayout(new BorderLayout());
    memberCard.setPreferredSize(new Dimension(300, 300)); // Fixed size for each card
    memberCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 255), 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    // Info panel
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setOpaque(false);

    // Name label
    JLabel nameLabel = new JLabel(member.getFirstName() + " " + member.getLastName());
    nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(nameLabel);

    // Role label
    JLabel roleLabel = new JLabel(member.getRoleName());
    roleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
    roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(roleLabel);

    // Join date label
    JLabel joinDateLabel = new JLabel("Joined: " + member.getJoinDate());
    joinDateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    joinDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    infoPanel.add(joinDateLabel);

    // Trash button
    JButton trashButton = new JButton("Delete Member");
    trashButton.setPreferredSize(new Dimension(40, 40));
    trashButton.setBackground(new Color(255, 130, 130));
    trashButton.setForeground(Color.WHITE);
    trashButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Ensure button is centered
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
    eventCard.setPreferredSize(new Dimension(300, 300)); // Fixed size for each card
    eventCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 255), 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    // Info panel
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

    // Create a JTextArea for event description
    JTextArea roleTextArea = new JTextArea(event.getEventDescription());
    roleTextArea.setFont(new Font("Arial", Font.ITALIC, 16));
    roleTextArea.setLineWrap(true); // Enable line wrapping
    roleTextArea.setWrapStyleWord(true); // Wrap at word boundaries, not characters
    roleTextArea.setEditable(false); // Make it non-editable
    roleTextArea.setOpaque(false); // Keep background transparent
    roleTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
    roleTextArea.setPreferredSize(new Dimension(250, 60)); // Adjust height to fit more lines
    roleTextArea.setMaximumSize(new Dimension(250, 100)); // Allow it to expand vertically within the card

    // Center the text horizontally
    roleTextArea.setAlignmentY(Component.CENTER_ALIGNMENT);
    roleTextArea.setCaretPosition(0);  // Ensure text is aligned properly from the start

    // Make sure the text is centered horizontally and vertically within the JTextArea
    roleTextArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT); // Left to right for proper alignment

    // Create a JPanel for wrapping the JTextArea
    JPanel textPanel = new JPanel();
    textPanel.setLayout(new BorderLayout());
    textPanel.add(roleTextArea, BorderLayout.CENTER);

    // Center the text inside the text panel
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
    // Clear existing components
    cards.removeAll();
    // Create a container panel using GridBagLayout
    JPanel eventsContainer = new JPanel(new GridBagLayout());
    eventsContainer.setBackground(new Color(100, 115, 225));
    eventsContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Space between cards
    gbc.fill = GridBagConstraints.NONE; // No resizing
    gbc.anchor = GridBagConstraints.NORTHWEST; // Align components to top-left

    int row = 0; // Starting at the first row
    int col = 0; // Starting at the first column

    // Add "Add Member" button in the first row, first column
    gbc.gridwidth = 1; // Button takes up 1 column
    JButton addEventButton = new JButton("+");
    addEventButton.setFont(new Font("Arial", Font.BOLD, 48));
    addEventButton.setForeground(new Color(100, 115, 225));
    addEventButton.setPreferredSize(new Dimension(300, 300)); // Fixed size for the button
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
    col++; // Move to next column

    // Add the first two member cards to the same row, filling the rest of the row
    for (int i = 0; i < 2 && i < eventList.size(); i++) {
      gbc.gridwidth = 1;  // Each card takes up 1 column
      gbc.gridx = col;
      gbc.gridy = row;

      // Add the member card to the grid
      eventsContainer.add(createEventCard(eventList.get(i), clubName), gbc);

      col++;  // Move to next column
    }

    // Move to the next row if the first row is full
    if (col == 3) {
      col = 0;  // Reset column index to 0
      row++;  // Move to the next row
    }

    // Add remaining member cards after the first 3 columns are filled
    for (int i = 2; i < eventList.size(); i++) {
      gbc.gridwidth = 1;  // Each card takes up 1 column
      gbc.gridx = col;
      gbc.gridy = row;

      // Add the member card to the grid
      eventsContainer.add(createEventCard(eventList.get(i), clubName), gbc);

      col++;  // Move to next column

      // After every 3 columns, move to the next row
      if (col == 3) {
        col = 0;
        row++;
      }
    }

    // Wrap the container in a scroll pane
    JScrollPane scrollPane = new JScrollPane(eventsContainer);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

    // Create a panel for the back button
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

    // Create the main members page
    JPanel membersPage = new JPanel();
    membersPage.setLayout(new BorderLayout());
    membersPage.add(backButtonPanel, BorderLayout.NORTH); // Back button at the top
    membersPage.add(scrollPane, BorderLayout.CENTER);     // Scrollable container

    // Add the members page to the cards panel
    cards.add(membersPage, "Members");

    // Switch to the members page
    CardLayout cardLayout = (CardLayout) cards.getLayout();
    cardLayout.show(cards, "Members");

    // Repaint and revalidate
    cards.revalidate();
    cards.repaint();
  }

  @Override
  public void displayClubForm(String name) {
    EditClubForm editForm = new EditClubForm(this, observer, name);
    editForm.setVisible(true);
  }
}
