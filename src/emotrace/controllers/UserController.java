package emotrace.controllers;

import emotrace.models.Channel;
import emotrace.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by nashahzad on 4/4/17.
 * Root route: /user/*
 */
@Controller
public class UserController {

    public static final int NUM_CHANNELS_PER_PAGE = 16;

    // EXTERNAL ROUTES

    /**
     * Serves a page displaying all the channels for a specific user
     * @param user_id id of the specific user
     * @param model Model of the page
     * @return returns page of the specific user
     */
    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public String user(@PathVariable("user_id") String user_id, Model model){
        List<Channel> channels = Channel.get_channels_by_owner(user_id, NUM_CHANNELS_PER_PAGE, 0);

        model.addAttribute("user", emotrace.models.User.get_user_by_googleID(user_id));
        model.addAttribute("channels", channels);
        model.addAttribute("form_channel", new Channel());
        model.addAttribute("is_owner", LoginService.is_current_user(user_id));
        LoginService.add_current_user_info_to_template(model);

        return "user";
    }

    /**
     * Helps with scrolling down the page and displaying more channel cards for user's channels
     * @param user_id id of the specific user
     * @param offset offset of the channel cards
     * @param model Model of the page
     * @return returns the channel card fragment
     */
    @RequestMapping(value = "/{user_id}/channels_scroll", method = RequestMethod.GET)
    public String scroll_channels(@PathVariable("user_id") String user_id,
                                  @RequestParam("offset") int offset, Model model) {
        List<Channel> channels = Channel.get_channels_by_owner(user_id, NUM_CHANNELS_PER_PAGE, offset);

        model.addAttribute("channels", channels);

        if (LoginService.is_current_user(user_id)) {
            return "fragment_collections/channel_cards_editable";
        }
        else {
            return "fragment_collections/channel_cards";
        }
    }
}
