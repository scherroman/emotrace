package emotrace.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Entity
/**
 * Emotion data for a specific timestamp in a video, averaged across all views
 */
public class RawEmotion {
    @Index public String video_id;

    @Id public Long id;
    @Index public int timestamp;
    public float joy;
    public float sadness;
    public float disgust;
    public float contempt;
    public float anger;
    public float fear;
    public float surprise;
    public float valence;
    public float engagement;
    public float times_seen;

    public RawEmotion () {}

    public RawEmotion(int timestamp, float joy, float sadness, float disgust, float contempt,
                    float anger, float fear, float surprise, float valence, float engagement) {
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

    public RawEmotion(String video_id, int timestamp, float joy, float sadness, float disgust, float contempt,
                    float anger, float fear, float surprise, float valence, float engagement) {
        this.video_id = video_id;
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
     * Retrieves all RawEmotion objects for a video
     * @param video_id Video ID used to query for RawEmotion data
     */
    public static List<RawEmotion> get_raw_emotions_by_video_id(String video_id) {
        return ofy().load().type(RawEmotion.class).filter("video_id", video_id).list();
    }

    /**
    * Retrieves a RawEmotion object for a video by timestamp
    * @param video_id Video ID used to query for RawEmotion data
    * @param timestamp Timestamp to use to query for RawEmotion data
    */
    public static RawEmotion get_raw_emotion_by_video_timestamp(String video_id, int timestamp) {
        return ofy().load().type(RawEmotion.class).filter("video_id", video_id).filter("timestamp", timestamp).first().now();
    }

    /***
    * Creates this RawEmotion object
    * */
    public Key<RawEmotion> create() {
      return ofy().save().entity(this).now();
    }

    /**
     * Updates an existing RawEmotion with new emotion data, by averaging based on times_seen
     * @param prev_emo existing emotion data
     * @param new_emo new emotion data
     */
    public static void update_values(RawEmotion prev_emo, RawEmotion new_emo){
        // assign same to overwrite entry in datastore
        new_emo.id = prev_emo.id;

        // increment times seen by one
        new_emo.times_seen = prev_emo.times_seen + 1;

        // aggregate values
        new_emo.anger = ((prev_emo.anger * prev_emo.times_seen) + new_emo.anger)/new_emo.times_seen;
        new_emo.joy = ((prev_emo.joy * prev_emo.times_seen) + new_emo.joy)/new_emo.times_seen;
        new_emo.sadness = ((prev_emo.sadness * prev_emo.times_seen) + new_emo.sadness)/new_emo.times_seen;
        new_emo.disgust = ((prev_emo.disgust * prev_emo.times_seen) + new_emo.disgust)/new_emo.times_seen;
        new_emo.contempt = ((prev_emo.contempt * prev_emo.times_seen) + new_emo.contempt)/new_emo.times_seen;
        new_emo.fear = ((prev_emo.fear * prev_emo.times_seen) + new_emo.fear)/new_emo.times_seen;
        new_emo.surprise = ((prev_emo.surprise * prev_emo.times_seen) + new_emo.surprise)/new_emo.times_seen;
        new_emo.valence = ((prev_emo.valence * prev_emo.times_seen) + new_emo.valence)/new_emo.times_seen;
        new_emo.engagement = ((prev_emo.engagement * prev_emo.times_seen) + new_emo.engagement)/new_emo.times_seen;
    }
}