package app;

import java.sql.Date;

public class Purchase {
  private int purchaseId;
  private String title;
  private double cost;
  private String clubName;

  public Purchase(int purchaseId, String title, double cost, String clubName) {
    this.purchaseId = purchaseId;
    this.title = title;
    this.cost = cost;
    this.clubName = clubName;
  }

  public int getPurchaseId() {
    return purchaseId;
  }

  public String getTitle() {
    return title;
  }

  public double getCost() {
    return cost;
  }

  public String getClubName() {
    return clubName;
  }

}
