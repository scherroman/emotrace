package emotrace.controllers;

import emotrace.models.Channel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by nashahzad on 4/4/17.
 */
@Controller
public class UserController {

    public static final int NUM_CHANNELS_PER_PAGE = 16;

    //SERVES A PAGE DISPLAYING ALL CHANNELS ASSOCIATED WITH A SPECIFIC USER
    @RequestMapping(value = "{user_id}", method = RequestMethod.GET)
    public String user(@PathVariable("user_id") String user_id, Model model){
        List<Channel> channels = Channel.get_channels_by_owner(user_id, NUM_CHANNELS_PER_PAGE, 0);

        model.addAttribute("user_id", user_id);
        model.addAttribute("channels", channels);
        model.addAttribute("form_channel", new Channel());

        return "user";
    }

    @RequestMapping(value = "{user_id}/channels/scroll", method = RequestMethod.GET)
    public String scroll_channels(@PathVariable("user_id") String user_id,
                                  @RequestParam("offset") int offset, Model model) {
        offset = offset * NUM_CHANNELS_PER_PAGE;
        List<Channel> channels = Channel.get_channels_by_owner(user_id, NUM_CHANNELS_PER_PAGE, offset);

        model.addAttribute("channels", channels);

        return "fragment_collections/channel_cards_editable";
    }


}
