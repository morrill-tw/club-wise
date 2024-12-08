package app;

import java.sql.Date;

public class Member {
  private int id;
  private String firstName;
  private String lastName;
  private Date joinDate;
  private String roleName;

  public Member(int id, String firstName, String lastName, Date joinDate, String roleName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.joinDate = joinDate;
    this.roleName = roleName;

  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Date getJoinDate() {
    return joinDate;
  }

  public String getRoleName() {
    return roleName;
  }

}
