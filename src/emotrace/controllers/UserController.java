package emotrace.controllers;

import com.google.appengine.api.users.User;
import emotrace.models.Channel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by nashahzad on 4/4/17.
 */
@Controller
public class UserController {

    public static final int NUM_CHANNELS_PER_PAGE = 16;

    //SERVES A PAGE DISPLAYING ALL CHANNELS ASSOCIATED WITH A SPECIFIC USER
    @RequestMapping(value = "{user_id}", method = RequestMethod.GET)
    public String user(@PathVariable("user_id") String user_id, Model model, ModelMap modelMap){
        List<Channel> channels = Channel.get_channels_by_owner(user_id, NUM_CHANNELS_PER_PAGE, 0);

        model.addAttribute("user_id", user_id);
        model.addAttribute("channels", channels);
        model.addAttribute("form_channel", new Channel());

        //CHECK IF USER PAGE IS CURRENT USER'S OWN PAGE
        boolean is_owner = false;
        User current_user = LoginController.get_current_user();
        if (current_user != null) {
            String id = current_user.getUserId();
            is_owner = id.equals(user_id);
        }

        model.addAttribute("is_owner", is_owner);
        LoginController.add_current_user_info_to_template(modelMap);

        return "user";
    }

    @RequestMapping(value = "{user_id}/channels_scroll", method = RequestMethod.GET)
    public String scroll_channels(@PathVariable("user_id") String user_id,
                                  @RequestParam("offset") int offset, Model model) {
        offset = offset * NUM_CHANNELS_PER_PAGE;
        List<Channel> channels = Channel.get_channels_by_owner(user_id, NUM_CHANNELS_PER_PAGE, offset);

        model.addAttribute("channels", channels);

        return "fragment_collections/channel_cards_editable";
    }


}
