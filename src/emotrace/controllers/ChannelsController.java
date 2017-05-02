package emotrace.controllers;

import emotrace.models.Channel;
import emotrace.services.LoginService;
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
public class ChannelsController {

    public static final int NUM_CHANNELS_PER_PAGE = 16;

    // EXTERNAL ROUTES

    //SERVES A PAGE DISPLAYING ALL CHANNELS ON SITE
    @RequestMapping(value = "channels", method = RequestMethod.GET)
    public String channels(Model model){
        List<Channel> channels = Channel.scroll_channels(NUM_CHANNELS_PER_PAGE, 0);

        model.addAttribute("channels", channels);
        LoginService.add_current_user_info_to_template(model);

        return "channels";
    }

    @RequestMapping(value = "/scroll", method = RequestMethod.GET)
    public String scroll_channels(@RequestParam("offset") int offset, Model model) {
        offset = offset * NUM_CHANNELS_PER_PAGE;
        List<Channel> channels = Channel.scroll_channels(NUM_CHANNELS_PER_PAGE, offset);

        model.addAttribute("channels", channels);

        return "fragment_collections/channel_cards";
    }
}