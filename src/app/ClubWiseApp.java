package app;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import ui.UI;

public class ClubWiseApp implements App {
  private final UI ui;
  private final String URL = "jdbc:mysql://localhost:3306/club_wise";
  private Connection conn;

  public ClubWiseApp(UI ui) {
    this.ui = ui;
    ui.setObserver(this);
  }

  public void run() {
    connectToDatabase();
    ui.displayClubs(getClubs());
  }

  public void openClubsPage() {
    ui.displayClubs(getClubs());
  }

  public void openMembersPage(Club club) {
    ui.displayMembers(club.getName(), getMembers(club.getName()));
  }

  public void openEventsPage(Club club) {
    ui.displayEvents(club.getName(), getEvents(club.getName()));
  }

  private List<Event> getEvents(String clubName) {
    List<Event> eventsList = new ArrayList<>();
    // Call the stored procedure
    String sql = "{ CALL get_events_by_club(?) }";
    try {
      CallableStatement callableStatement = conn.prepareCall(sql);
      // Set input parameter
      callableStatement.setString(1, clubName);

      // Execute the stored procedure
      try (ResultSet resultSet = callableStatement.executeQuery()) {

        // Process the result set
        while (resultSet.next()) {
          String eventTitle = resultSet.getString("event_title");
          String eventDescription = resultSet.getString("event_description");
          Date eventDate = resultSet.getDate("event_date");
          eventsList.add(new Event(eventTitle, eventDescription, eventDate, clubName));
        }
      }
    } catch (SQLException e) {
      System.err.println("SQL error: " + e.getMessage());
    }
    return eventsList;
  }

  @Override
  public void filterClubs(Filter criteria) {
    ui.refreshClubs(criteria.filter(getClubs()));
  }

  @Override
  public void addMember(Member member) {
    String firstName = member.getFirstName();
    String lastName = member.getLastName();
    Date joinDate = member.getJoinDate();
    String clubName = member.getClubName();
    String roleName = member.getRoleName();

    String sql = "{ CALL add_member_to_club(?, ?, ?, ?, ?) }";
    try {
      CallableStatement callableStatement = conn.prepareCall(sql);
      // Set input parameters
      callableStatement.setString(1, firstName);
      callableStatement.setString(2, lastName);
      callableStatement.setDate(3, joinDate);
      callableStatement.setString(4, clubName);
      callableStatement.setString(5, roleName);

      // Execute the stored procedure
      boolean hasResultSet = callableStatement.execute();

      // Handle any results
      if (hasResultSet) {
        try (ResultSet resultSet = callableStatement.getResultSet()) {
          while (resultSet.next()) {
            System.out.println("Error: " + resultSet.getString("ErrorMessage"));
          }
        }
      } else {
        System.out.println("Member added successfully.");
        ui.displayMembers(clubName, getMembers(clubName));
      }
    } catch (SQLException e) {
      System.err.println("SQL error: " + e.getMessage());
    }
  }

  @Override
  public void addEvent(Event event) {
    // SQL to call the stored procedure
    String sql = "{CALL add_event_to_club(?, ?, ?, ?)}";
    String eventTitle = event.getEventTitle();
    String eventDescription = event.getEventDescription();
    Date eventDate = event.getEventDate();
    String clubName = event.getClubName();

      // Create a CallableStatement to call the stored procedure
      try (CallableStatement callableStatement = conn.prepareCall(sql)) {
        // Set input parameters for the procedure
        callableStatement.setString(1, eventTitle);
        callableStatement.setString(2, eventDescription);
        callableStatement.setDate(3, eventDate);
        callableStatement.setString(4, clubName);

        // Execute the stored procedure
        try (ResultSet resultSet = callableStatement.executeQuery()) {
          // Process the result (the result message from the procedure)
          if (resultSet.next()) {
            String resultMessage = resultSet.getString("ResultMessage");
            System.out.println("Procedure Result: " + resultMessage);
          }

        }
        ui.displayEvents(clubName, getEvents(clubName));
    } catch (SQLException e) {
      System.err.println("SQL error: " + e.getMessage());
    }
  }

  // Gets the database connection
// Throws SQLException if username and password were incorrect
  private Connection getConnection() throws SQLException {
    Properties connectionProps = new Properties();
    String[] credentials = ui.promptUserForCredentials();
    System.out.println(credentials[0]);
    System.out.println(credentials[1]);
    connectionProps.put("user", credentials[0]);
    connectionProps.put("password", credentials[1]);
    return DriverManager.getConnection(URL
            + "?characterEncoding=UTF-8&useSSL=false", connectionProps);
  }

  // Connects to the database by repeatedly prompting user for
// username and password until connection is successful
  private void connectToDatabase() {
    boolean attemptingConnection = true;
    while (attemptingConnection) {
      try {
        this.conn = this.getConnection();
        System.out.println("Connected to database.\n");
        attemptingConnection = false;
      } catch (SQLException e) {
        System.out.println("ERROR: Could not connect to the database. Try again. " + e + "\n");
      }
    }
  }

  // Returns the next String input from the user
  private static String tryNext() {
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNext()) {
      return scanner.next();
    } else {
      throw new IllegalStateException("No available input");
    }
  }

  private List<Club> getClubs() {
    ResultSet rs;
    List<Club> clubs = new ArrayList<Club>();
    try {
      PreparedStatement ps = conn.prepareStatement("SELECT * FROM club");
      rs = ps.executeQuery();
      while (rs.next()) {
        String name = rs.getString("club_name");
        String description = rs.getString("club_description");
        Boolean active = rs.getBoolean("active");
        String category = rs.getString("category");

        Club club = new Club(name, description, active, category);
        clubs.add(club);
      }
    } catch  (SQLException e) {
      System.out.println("Error fetching clubs.");
    }
    return clubs;
  }

  private List<Member> getMembers(String clubName) {
    ResultSet rs;
    List<Member> members = new ArrayList<Member>();
    try {
      PreparedStatement ps =
              conn.prepareStatement("CALL get_members_by_club_name( " + "\"" + clubName +"\"" + " )");
      rs = ps.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("member_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        Date joinDate = rs.getDate("join_date");
        String roleName = rs.getString("role_name");

        Member member = new Member(id, firstName, lastName, joinDate, roleName, clubName);
        members.add(member);
      }
    } catch (SQLException e) {
      System.out.println("Error fetching members.");
    }
    return members;
  }

  public void deleteEvent(Event event) {
    try {
      // SQL to call the stored procedure
      String sql = "{CALL delete_events_from_club(?, ?, ?)}";

      // Prepare the callable statement
      CallableStatement stmt = conn.prepareCall(sql);
      stmt.setString(1, event.getClubName());   // Set the club name
      stmt.setString(2, event.getEventTitle()); // Set the event title
      stmt.setDate(3, event.getEventDate());    // Set the event date

      // Execute the procedure
      boolean hasResultSet = stmt.execute();
      ui.displayEvents(event.getClubName(), getEvents(event.getClubName()));
    } catch (SQLException e) {
      // Handle any SQL exceptions
      e.printStackTrace();
      System.out.print("Error occurred while deleting events: " + e.getMessage());
    }
  }

  public void deleteMember(Member member) {
    int memberId = member.getId();
    try {
    // SQL to call the stored procedure
    String sql = "{CALL remove_member_from_club(?)}";

    // Prepare the callable statement
    CallableStatement stmt = conn.prepareCall(sql);
    stmt.setInt(1, memberId);  // Set the member ID

    // Execute the procedure
    boolean hasResultSet = stmt.execute();

    // Process the result (Message from the procedure)
    if (hasResultSet) {
      ResultSet rs = stmt.getResultSet();
      while (rs.next()) {
        String resultMessage = rs.getString(1);  // Get the message returned by the procedure
      }
    }
      ui.displayMembers(member.getClubName(), getMembers(member.getClubName()));
  } catch (SQLException e) {
    // Handle any SQL exceptions
    e.printStackTrace();
    System.out.print("Error occurred while removing member: " + e.getMessage());
  }
  }

  @Override
  public void addClub(Club club) {
    // SQL to call the stored procedure
    String sql = "{CALL create_club(?, ?, ?, ?)}";
    String clubName = club.getName();
    String clubDescription = club.getDescription();
    Boolean clubStatus = club.getActiveStatus();
    String clubCategory = club.getCategory();

    // Create a CallableStatement to call the stored procedure
    try (CallableStatement callableStatement = conn.prepareCall(sql)) {
      // Set input parameters for the procedure
      callableStatement.setString(1, clubName);
      callableStatement.setString(2, clubDescription);
      callableStatement.setBoolean(3, clubStatus);
      callableStatement.setString(4, clubCategory);

      // Execute the stored procedure
      callableStatement.executeQuery();
      ui.refreshClubs(getClubs());
    } catch (SQLException e) {
      System.err.println("SQL error: " + e.getMessage());
    }
  }

  public List<SocialMedia> getSocials(String clubName) {
    ResultSet rs;
    List<SocialMedia> socials = new ArrayList<SocialMedia>();
    try {
      PreparedStatement ps =
              conn.prepareStatement("CALL get_socials_by_club( " + "\"" + clubName +"\"" + " )");
      rs = ps.executeQuery();
      while (rs.next()) {
        String platform = rs.getString("platform");
        String username = rs.getString("username");

        SocialMedia social = new SocialMedia(platform, username, clubName);
        socials.add(social);
      }
    } catch (SQLException e) {
      System.out.println("Error fetching socials.");
    }
    return socials;
  }

  @Override
  public void openSocialsDialog(String name) {
    ui.displaySocials(getSocials(name));
  }
}