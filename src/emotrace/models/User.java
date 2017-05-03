package emotrace.models;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * A channel created by a user, containing videos and other content with a common theme
 */
@Entity
public class User {

    @Id public Long id;
    @Index String googleID;
    @Index public String nickName;
    public String email;
    public String description;
    @Index public Date dateCreated;

    public User() {}

    public User(String googleID, String nickName, String email, String description) {
        this.googleID = googleID;
        this.nickName = nickName;
        this.email = email;
        this.description = description;
    }

    // GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public static Key<User> getKey(Long id) {
        return Key.create(User.class, id);
    }


    /**
     * Retrieves a user by id
     */
    public static User get_user_by_id(Long id) {
        return ofy().load().key(User.getKey(id)).now();
    }

    /**
     * Retrieves a user by googleID
     * @param gid The googleID of the user
     */
    public static User get_user_by_googleID(String gid) {
        return ofy().load().type(User.class).filter("googleID", gid).now();
    }

    /**
     * Retrieves a list of existing users, sorted by name
     * @param num_users Number of users to retrieve at a time
     * @param offset Number of users to offset retrieval by
     */
    public static List<User> scroll_channels(int num_users, int offset) {
        return ofy().load().type(User.class).order("nickName").limit(num_users).offset(offset).list();
    }

    /**
     * Creates this channel
     */
    public Key<User> create() {
        this.dateCreated = new Date();
        return ofy().save().entity(this).now();
    }

    /**
     * Deletes this user
     */
    public void delete() {
        ofy().delete().entity(this).now();
    }

}