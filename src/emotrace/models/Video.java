package emotrace.models;

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
  @Parent
  Channel channel;

  @Id public Long id;
  public String name;
  public String url;
  public String description;

  /**
   * Simple Video constructor
   **/
  public Video(Long id, String name, String url, Channel channel, String description) {
    this.channel = channel;
    this.id = id;
    this.name = name;
    this.url = url;
    this.description = description;
  }
}