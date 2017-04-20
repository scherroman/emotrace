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
  Key<Video> video;

  @Id public Long id;
  @Index public double timestamp;
  public int joy;
  public int sadness;
  public int disgust;
  public int contempt;
  public int anger;
  public int fear;
  public int surprise;
  public int valence;
  public int engagement;
  public String emoji;

  public RawEmotion () {}

  /**
   * Simple RawEmotion constructor
   **/
  public RawEmotion(Key<Video> video, Long id, double timestamp, int joy, int sadness, int disgust, int contempt,
                    int anger, int fear, int surprise, int valence, int engagement, String emoji) {
  	this.video = video;
    this.id = id;
    this.timestamp = timestamp;
    this.joy = joy;
    this.sadness = sadness;
    this.disgust = disgust;
    this.contempt = contempt;
    this.anger = anger;
    this.fear = fear;
    this.surprise = surprise;
    this.valence = valence;
    this.engagement = engagement;
    this.emoji = emoji;
  }
}