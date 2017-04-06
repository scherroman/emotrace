package emotrace.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nashahzad on 4/5/17.
 */
@RequestMapping("/channel")
public class ChannelController {

    //SERVES A PAGE DISPLAYING ALL CHANNELS ON SITE
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String channels(){
        return "channel";
    }

    @RequestMapping(value = "/{channel_id}", method = RequestMethod.GET)
    public String channel(@PathVariable("channel_id") String channel_id){
        return "channel?=" + channel_id;
    }
}
