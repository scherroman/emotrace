package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nashahzad on 4/4/17.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //SERVES A PAGE DISPLAYING ALL DIFFERENT USERS ON SITE
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String channels(){
        return "user";
    }

    //SERVES A PAGE DISPLAYING ALL CHANNELS ASSOCIATED WITH A SPECIFIC USER
    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public String mychannel(@PathVariable("user_id") String user_id){
        return "mychannels?=" + user_id;
    }

    //SERVES A PAGE EXPANDING UPON A SPECIFIC CHANNEL
    @RequestMapping(value = "/channel/{channel_id}", method = RequestMethod.GET)
    public String channel(@PathVariable("channel_id") String channel_id){
        return "channel?=" + channel_id;
    }
}
