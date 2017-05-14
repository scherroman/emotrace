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

import static com.googlecode.objectify.ObjectifyService.ofy;

@Entity
public class RawEmotion {
  @Index public String video;

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

  public RawEmotion () {}

  public RawEmotion(double timestamp, int joy, int sadness, int disgust, int contempt,
                    int anger, int fear, int surprise, int valence, int engagement) {
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
  }

  public RawEmotion(String video_id, double timestamp, int joy, int sadness, int disgust, int contempt,
                    int anger, int fear, int surprise, int valence, int engagement) {
  	this.video = video_id;
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
  }

  /**
   * Retrieves a list of channels by owner, sorted by name
   * @param video Video to use to query for RawEmotion data
   * @param timestamp Timestamp to use to query for RawEmotion data
   */
  public static RawEmotion get_raw_emotion_by_video_timestamp(String video, double timestamp) {
    return ofy().load().type(RawEmotion.class).filter("video", video).filter("timestamp", timestamp).first().now();
  }

  /***
   * Creates this channel
   * */
  public Key<RawEmotion> create() {
      return ofy().save().entity(this).now();
  }

}