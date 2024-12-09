package app;

import java.util.List;

import ui.PurchasesDialog;
import ui.SocialsDialog;

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

  void openSocialsDialog(SocialsDialog prev, String name);

  void editMember(Member member, String clubName);

  void editEvent(Event event);

  void deleteSocial(SocialMedia social);

  void addSocial(String clubName, SocialMedia socialMedia);

  void openClubForm(String name);

  void editClub(Club club);

  void deletePurchase(Purchase purchase);

  void openPurchasesDialog(PurchasesDialog purchasesDialog, String clubName);

  void addPurchase(String clubName, Purchase purchase);
}
