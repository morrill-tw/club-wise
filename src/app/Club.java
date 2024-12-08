package app;

public class Club {
  private String name;
  private String description;
  private Boolean active;
  private String category;

  Club(String name, String description, Boolean active, String category) {
    this.name = name;
    this.description = description;
    this.active = active;
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Boolean getActiveStatus() {
    return active;
  }

  public String getCategory() {
    return category;
  }
}
