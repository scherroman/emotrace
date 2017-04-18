package emotrace.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.lang.String;
import java.util.Date;
import java.util.List;

@Entity
public class User {
  @Id public Long id;
  public String googleAccount;
  public String name;

  /**
   * Simple User constructor
   **/
  public User(Long id, String googleAccount, String name) {
    this.id = id;
    this.googleAccount = googleAccount;
    this.name = name;
  }

  public User(){}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGoogleAccount() {
    return googleAccount;
  }

  public void setGoogleAccount(String googleAccount) {
    this.googleAccount = googleAccount;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}