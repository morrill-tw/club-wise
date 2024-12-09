package app;

import java.util.List;

public interface App {

  void run();

  void openClubsPage();

  void openMembersPage(Club club);

  void openEventsPage(Club club);

  void filterClubs(Filter criteria);

  void addMember(Member member);

  void addEvent(Event event);

  void deleteEvent(Event event);

  void deleteMember(Member member);

  void addClub(Club club);

  void openSocialsDialog(String name);
}
