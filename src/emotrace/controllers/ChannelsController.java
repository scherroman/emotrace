package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by romanscher on 4/1/17.
 */
@Controller
@RequestMapping("/channels")
public class ChannelsController {

    //SERVES A PAGE DISPLAYING ALL CHANNELS ON SITE
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String channels(){
        return "channels";
    }
}