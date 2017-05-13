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
 * Root route: /channels/*
 */
@Controller
public class ChannelsController {

    public static final int NUM_CHANNELS_PER_PAGE = 16;

    /**
     * Serves page displaying the channels on the site
     * @param model Model of the page
     * @return Returns page displaying the channels
     */
    @RequestMapping(value = "channels", method = RequestMethod.GET)
    public String channels(Model model){
        List<Channel> channels = Channel.scroll_channels(NUM_CHANNELS_PER_PAGE, 0);

        model.addAttribute("channels", channels);
        LoginService.add_current_user_info_to_template(model);

        return "channels";
    }

    /**
     * Method for displaying more channel cards correctly on the page
     * @param offset Offset of channel cards
     * @param model Model of the page
     * @return Channel card fragment
     */
    @RequestMapping(value = "/scroll", method = RequestMethod.GET)
    public String scroll_channels(@RequestParam("offset") int offset, Model model) {
        List<Channel> channels = Channel.scroll_channels(NUM_CHANNELS_PER_PAGE, offset);

        model.addAttribute("channels", channels);

        return "fragment_collections/channel_cards";
    }
}