package emotrace.controllers;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static emotrace.services.LoginService.get_current_user;


/**
 * Created by nashahzad on 4/8/17.
 */
@Controller
public class LoginController {

    // INTERNAL ROUTES

    @RequestMapping(value = "login", method = RequestMethod.GET, produces = "application/json")
    public String login(@RequestHeader(value = "referer") String referer){
        // TODO new_referer = /loginreturn?referer="referer"
        String new_referer = "/loginreturn?referer=" + referer;
        UserService userService = UserServiceFactory.getUserService();
        return "redirect:" + userService.createLoginURL(new_referer);
    }


    // return url from google login
    @RequestMapping(value = "loginreturn", method = RequestMethod.GET, produces = "application/json")
    public String login_return(@RequestParam String referer){
        // with login info check if user exists, if not insert login data into table
        UserService userService = UserServiceFactory.getUserService();

        //Get Current user using LoginService class
        User current_google_user = get_current_user();

        String user_id = current_google_user.getUserId();
        //check for user in db
        if(null == emotrace.models.User.get_user_by_googleID(user_id)){
            //make new user
            emotrace.models.User current_user = new emotrace.models.User(
                    current_google_user.getUserId(),
                    current_google_user.getNickname(),
                    current_google_user.getEmail()
            );
            //insert into db
            current_user.create();

        }

        return "redirect:" + referer;
    }


    @RequestMapping(value = "logout", method = RequestMethod.GET, produces = "application/json")
    public String logout(@RequestHeader(value = "referer") String referer){
        UserService userService = UserServiceFactory.getUserService();
        return "redirect:" + userService.createLogoutURL(referer);
    }
}
