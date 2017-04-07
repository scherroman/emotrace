package emotrace.models;

import emotrace.models.Video;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.lang.String;
import java.util.Date;
import java.util.List;

@Entity
public class RawEmotion {
  @Parent
  Video video;
  @Id public Long id;

  @Index public double timestamp;
  public String appearance;
  public String emotions;
  public String expressions;
  public String emoji;

  /**
   * Simple RawEmotion constructor
   **/
  public RawEmotion(Video video, Long id, String googleAccount, String name) {
  	this.video = video;
    this.id = id;
    this.timestamp = timestamp;
    this.appearance = appearance;
    this.emotions = emotions;
    this.expressions = expressions;
    this.emoji = emoji;
  }
}