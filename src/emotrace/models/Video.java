package emotrace.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.lang.String;
import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * A video added to a channel from an external source (i.e. YouTube)
 */
@Entity
public class Video {
    @Index Key<Channel> channel;

    @Id public Long id;
    @Index public String externalId;
    @Index public int numViews;
    @Index public Date dateCreated;

    public Video() {}

    public Video(Long channelId, String externalId) {
        this.channel = Channel.getKey(channelId);
        this.externalId = externalId;
    }

    // GETTERS & SETTERS

    public Key<Channel> getChannel() {
        return channel;
    }

    public void setChannel(Key<Channel> channel) {
        this.channel = channel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public int getNumViews() {
        return numViews;
    }

    public void setNumViews(int numViews) {
        this.numViews = numViews;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    // METHODS

    public static Key<Video> getKey(Long id) {
        return Key.create(Video.class, id);
    }

    /**
     * Retrieves a video by id
     */
    public static Video get_video_by_id(Long id) {
        return ofy().load().key(Video.getKey(id)).now();
    }

    public static Video get_video_by_id(String id) {
        return get_video_by_id(Long.parseLong(id));
    }

    /**
     * Retrieves a list of videos by owner, sorted by name
     * @param num_videos Number of videos to retrieve at a time
     * @param offset Number of videos to offset retrieval by
     */
    public static List<Video> get_videos_by_channel_id(Long channel_id, int num_videos, int offset) {
        return ofy().load().type(Video.class).filter("channel", Channel.getKey(channel_id)).order("-dateCreated").limit(num_videos).offset(offset).list();
    }

    public static List<Video> get_videos_by_channel_id(String channel_id, int num_videos, int offset) {
        return get_videos_by_channel_id(Long.parseLong(channel_id), num_videos, offset);
    }

    /**
     * Retrieves a list of existing videos, sorted by date created
     * @param num_videos Number of videos to retrieve at a time
     * @param offset Number of videos to offset retrieval by
     */
    public static List<Video> scroll_videos(int num_videos, int offset) {
        return ofy().load().type(Video.class).order("-dateCreated").limit(num_videos).offset(offset).list();
    }
    
    /**
     * Creates this video
     */
    public Key<Video> create() {
        this.dateCreated = new Date();
        return ofy().save().entity(this).now();
    }

    /**
     * Deletes this video
     */
    public void delete() {
        ofy().delete().entity(this).now();
    }
}