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
 * A channel created by a user, containing videos and other content with a common theme
 */
@Entity
public class Channel {
    @Index public String owner;

    @Id public Long id;
    @Index public String name;
    public String description;
    @Index public Date dateCreated;

    public Channel() {}

    public Channel(String owner, String name, String description) {
        this.owner = owner;
        this.name = name;
        this.description = description;
    }

    // GETTERS & SETTERS

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    // METHODS

    public static Key<Channel> getKey(Long id) {
        return Key.create(Channel.class, id);
    }

    /**
     * Retrieves a channel by id
     */
    public static Channel get_channel_by_id(Long id) {
        return ofy().load().key(Channel.getKey(id)).now();
    }

    public static Channel get_channel_by_id(String channel_id) {
        return get_channel_by_id(Long.parseLong(channel_id));
    }

    /**
     * Retrieves a list of channels by owner, sorted by name
     * @param num_channels Number of channels to retrieve at a time
     * @param offset Number of channels to offset retrieval by
     */
    public static List<Channel> get_channels_by_owner(String owner, int num_channels, int offset) {
        return ofy().load().type(Channel.class).filter("owner", owner).order("name").limit(num_channels).offset(offset).list();
    }

    /**
     * Retrieves a list of existing channels, sorted by name
     * @param num_channels Number of channels to retrieve at a time
     * @param offset Number of channels to offset retrieval by
     */
    public static List<Channel> scroll_channels(int num_channels, int offset) {
        return ofy().load().type(Channel.class).order("name").limit(num_channels).offset(offset).list();
    }

    /**
     * Creates this channel
     */
    public Key<Channel> create() {
        this.dateCreated = new Date();
        return ofy().save().entity(this).now();
    }

    /**
     * Deletes this channel
     */
    public void delete() {
        ofy().delete().entity(this).now();
    }
}