package emotrace.controllers;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nashahzad on 4/8/17.
 */
@Controller
public class LoginController {

    // INTERNAL ROUTES

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
