package app;

public class SocialMedia {
  private String platform;
  private String username;
  private String clubName;

  public SocialMedia(String platform, String username, String clubName) {
    this.platform = platform;
    this.username = username;
    this.clubName = clubName;
  }

  public String getPlatform() {
    return platform;
  }

  public String getUsername() {
    return username;
  }

  public String getClubName() {
    return clubName;
  }
}
