package emotrace.controllers;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import emotrace.models.Channel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by nashahzad on 4/4/17.
 */
@Controller
@RequestMapping("/user/*")
public class UserController {

    public static final int NUM_CHANNELS_PER_PAGE = 16;

    //SERVES A PAGE DISPLAYING ALL CHANNELS ASSOCIATED WITH A SPECIFIC USER
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String user(Model model, ModelMap modelMap){
        UserService userService = UserServiceFactory.getUserService();
        String user_id = userService.getCurrentUser().getUserId();

        List<Channel> channels = Channel.get_channels_by_owner(user_id, NUM_CHANNELS_PER_PAGE, 0);

        model.addAttribute("user_id", user_id);
        model.addAttribute("channels", channels);
        model.addAttribute("form_channel", new Channel());

        LoginController.addUsernameToTemplate(modelMap);

        return "user";
    }

    @RequestMapping(value = "{user_id}/channels/scroll", method = RequestMethod.GET)
    public String scroll_channels(@PathVariable("user_id") String user_id,
                                  @RequestParam("offset") int offset, Model model) {
        offset = offset * NUM_CHANNELS_PER_PAGE;
        List<Channel> channels = Channel.get_channels_by_owner(user_id, NUM_CHANNELS_PER_PAGE, offset);

        model.addAttribute("channels", channels);

        return "fragment_collections/channel_cards";
    }
}
