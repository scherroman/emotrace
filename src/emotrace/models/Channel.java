package emotrace.models;

import emotrace.models.User;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.lang.String;
import java.util.Date;
import java.util.List;

@Entity
public class Channel {
  @Parent
  User owner;

  @Id Long id;
  String name;
  String description;

  /**
   * Simple Channel constructor
   **/
  public Channel(User owner, Long id, String name, String description) {
    this.owner = owner;
    this.id = id;
    this.name = name;
    this.description = description;
  }
}