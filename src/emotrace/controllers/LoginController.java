package emotrace.controllers;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static emotrace.services.LoginService.get_current_user;


/**
 * Created by nashahzad on 4/8/17.
 * route urls:
 * * /loginreturn
 * * /login
 * * /logout
 */
@Controller
public class LoginController {

    /**
     * Returns and redirects User to google login page of the site
     * @param referer Page to redirect them back to after logging in
     * @return Returns the redirect/login url
     */
    @RequestMapping(value = "login", method = RequestMethod.GET, produces = "application/json")
    public String login(@RequestHeader(value = "referer") String referer){
        String new_referer = "/loginreturn?referer=" + referer;
        UserService userService = UserServiceFactory.getUserService();
        return "redirect:" + userService.createLoginURL(new_referer);
    }


    /**
     * On return from login page, register user if a new user
     * @param referer url redirecting user back to
     * @return Return the redirect url
     */
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


    /**
     * Serves the redirect url for logging a user out
     * @param referer Url to redirect them back to after logging out
     * @return the redirect/logout url
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET, produces = "application/json")
    public String logout(@RequestHeader(value = "referer") String referer){
        UserService userService = UserServiceFactory.getUserService();
        return "redirect:" + userService.createLogoutURL(referer);
    }
}
