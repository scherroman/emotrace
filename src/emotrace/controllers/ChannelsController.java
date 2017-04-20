package emotrace.controllers;

import emotrace.models.Channel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by romanscher on 4/1/17.
 */
@Controller
@RequestMapping("/channels")
public class ChannelsController {

    public static final int NUM_CHANNELS_PER_PAGE = 32;

    //SERVES A PAGE DISPLAYING ALL CHANNELS ON SITE
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String channels(Model model){
        List<Channel> channels = Channel.scroll_channels(NUM_CHANNELS_PER_PAGE, 0);

        model.addAttribute("channels", channels);

        return "channels";
    }

//    @RequestMapping(value = "/scroll_channels", method = RequestMethod.GET)
//    public String scroll_channels(@RequestParam(value="offset", defaultValue="0") int offset) {
//        channels = Channel.scroll_channels(NUM_CHANNELS_PER_PAGE, offset);
//
//    }
}