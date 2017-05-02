package emotrace.controllers;

import com.google.appengine.api.users.User;
import emotrace.models.Channel;
import emotrace.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by nashahzad on 4/4/17.
 */
@Controller
public class UserController {

    public static final int NUM_CHANNELS_PER_PAGE = 16;

    // EXTERNAL ROUTES

    //SERVES A PAGE DISPLAYING ALL CHANNELS ASSOCIATED WITH A SPECIFIC USER
    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public String user(@PathVariable("user_id") String user_id, Model model){
        List<Channel> channels = Channel.get_channels_by_owner(user_id, NUM_CHANNELS_PER_PAGE, 0);

        model.addAttribute("user_id", user_id);
        model.addAttribute("channels", channels);
        model.addAttribute("form_channel", new Channel());
        model.addAttribute("is_owner", LoginService.is_current_user(user_id));
        LoginService.add_current_user_info_to_template(model);

        return "user";
    }

    @RequestMapping(value = "/{user_id}/channels_scroll", method = RequestMethod.GET)
    public String scroll_channels(@PathVariable("user_id") String user_id,
                                  @RequestParam("offset") int offset, Model model) {
        offset = offset * NUM_CHANNELS_PER_PAGE;
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
