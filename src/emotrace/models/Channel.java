package emotrace.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Entity
public class Channel {
  @Index public String owner;

  @Id public Long id;
  @Index public String name;
  public String description;

  public Channel() {}

  /**
   * Simple Channel constructor
   **/
  public Channel(String owner, String name, String description) {
    this.owner = owner;
    this.name = name;
    this.description = description;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static Key<Channel> getKey(Long id) {
   return Key.create(Channel.class, id);
  }

  public static Channel get_channel_by_id(Long id) {
    return ofy().load().key(Channel.getKey(id)).now();
  }

  public static List<Channel> get_channels_by_owner(String owner, int num_channels, int offset) {
    List<Channel> channels = ofy().load().type(Channel.class).filter("owner", owner).order("name").limit(num_channels).offset(offset).list();

    return channels;
  }

  public static List<Channel> scroll_channels(int num_channels, int offset) {
    List<Channel> channels = ofy().load().type(Channel.class).order("name").limit(num_channels).offset(offset).list();

    return channels;
  }

  public Key<Channel> create() {
    return ofy().save().entity(this).now();
  }

  public void delete() {
    ofy().delete().entity(this).now();
  }
}