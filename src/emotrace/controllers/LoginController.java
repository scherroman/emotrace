package emotrace.controllers;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nashahzad on 4/8/17.
 */
@Controller
public class LoginController {

    /**
     * Indicates if there is a user currently logged in or not
     * @return true if there is a logged in user, false if not
     */
    public static boolean is_user_logged_in(){
        UserService userService = UserServiceFactory.getUserService();
        return userService.isUserLoggedIn();
    }

    public static User get_current_user() {
        UserService userService = UserServiceFactory.getUserService();
        return userService.getCurrentUser();
    }

    /**
     * If user is logged in add their username to the template
     * otherwise, indicate to template that there is no logged in user
     * @param model ModelMap on which to add the attribute of the user's name
     */
    public static void add_current_user_info_to_template(ModelMap model){
        UserService userService = UserServiceFactory.getUserService();

        boolean user_is_logged_in = userService.isUserLoggedIn();
        model.addAttribute("user_is_logged_in", user_is_logged_in);
        if (user_is_logged_in) {
            model.addAttribute("user_name", userService.getCurrentUser().getNickname());
            model.addAttribute("my_channel_url", "/user/" + userService.getCurrentUser().getUserId());
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.GET, produces = "application/json")
    public String login(@RequestHeader(value = "referer") String referer){
        UserService userService = UserServiceFactory.getUserService();
        return "redirect:" + userService.createLoginURL(referer);
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET, produces = "application/json")
    public String logout(@RequestHeader(value = "referer") String referer){
        UserService userService = UserServiceFactory.getUserService();
        return "redirect:" + userService.createLogoutURL(referer);
    }
}
