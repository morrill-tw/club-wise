package app;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    ui.displayEvents(club);
  }

  @Override
  public void filterClubs(Filter criteria) {
    ui.refreshClubs(criteria.filter(getClubs()));
  }

  @Override
  public void addMember(Member member) {

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

        Member member = new Member(id, firstName, lastName, joinDate, roleName);
        members.add(member);
      }
    } catch (SQLException e) {
      System.out.println("Error fetching members.");
    }
    return members;
  }

}
