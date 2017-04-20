package emotrace.models;

import com.googlecode.objectify.Ref;
import emotrace.models.Channel;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.lang.String;
import java.util.Date;
import java.util.List;

@Entity
public class Video {
  Key<Channel> channel;

  @Id public Long id;
  @Index public String name;
  @Index public String url;
  public String description;

  public Video () {}

  /**
   * Simple Video constructor
   **/
  public Video(Key<Channel> channel, Long id, String name, String url, String description) {
    this.channel = channel;
    this.id = id;
    this.name = name;
    this.url = url;
    this.description = description;
  }
}