package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nashahzad on 4/5/17.
 */
@Controller
@RequestMapping("/channel/*")
public class ChannelController {

    //SERVES A PAGE DISPLAYING ALL CHANNELS ON SITE
    @RequestMapping(value = "{channel_id}", method = RequestMethod.GET)
    public String channel(@PathVariable("channel_id") String channel_id, Model model){
        model.addAttribute("channel_id", channel_id);
        return "channel";
    }
}
