package app;

import java.sql.Date;

public class Event {
  private final String clubName;
  private String eventTitle;
    private String eventDescription;
    private Date eventDate;

  public Event(String eventTitle, String eventDescription, Date eventDate, String clubName) {
    this.eventTitle = eventTitle;
    this.eventDescription = eventDescription;
    this.eventDate = eventDate;
    this.clubName = clubName;
  }

  public String getEventTitle() {
    return eventTitle;
  }

  public Date getEventDate() {
    return eventDate;
  }

  public String getEventDescription() {
    return eventDescription;
  }

  public String getClubName() {
    return clubName;
  }
}
