package emotrace.setup;

import com.googlecode.objectify.ObjectifyService;
import emotrace.models.Channel;
import emotrace.models.RawEmotion;
import emotrace.models.User;
import emotrace.models.Video;
import emotrace.services.YouTubeService;

import java.util.logging.Logger;


/**
 * Created by romanscher on 4/18/17.
 */
public class Setup {
    private static final Logger log = Logger.getLogger(Setup.class.getName());

    protected void init() {
        log.info("Starting app...");

        registerEntities();
        YouTubeService.init();
    }

    private void registerEntities() {
        log.info("Registering Objectify entities...");

        ObjectifyService.register(Channel.class);
        ObjectifyService.register(Video.class);
        ObjectifyService.register(RawEmotion.class);
        ObjectifyService.register(User.class);

        log.info("Done registering entities.");
    }
}
