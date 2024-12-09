package ui;

import java.util.List;

import app.App;
import app.Club;
import app.Event;
import app.Member;
import app.SocialMedia;

public interface UI {

  /**
   * Displays a screen for the user to enter their MySQL login credentials
   * @return a String array with two items, the first being the user's username
   * and the second being the user's password
   */
  String[] promptUserForCredentials();

  void setObserver(App observer);

  void displayClubs(List<Club> clubs);

  void displayMembers(String clubName, List<Member> members);

  void displayEvents(String clubName, List<Event> events);

  void refreshClubs(List<Club> clubs);
  
  void displaySocials(String clubName, List<SocialMedia> socials);

  void displayClubForm(String name);
}
