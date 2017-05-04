package emotrace.services;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.ui.Model;

/**
 * Created by romanscher on 5/1/17.
 */
public class LoginService {

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

    public static boolean is_current_user(String id) {
        User current_user = get_current_user();

        if (current_user == null) {
            return false;
        }

        return id.equals(current_user.getUserId());
    }

    /**
     * If user is logged in add their username to the template
     * otherwise, indicate to template that there is no logged in user
     * @param model ModelMap on which to add the attribute of the user's name
     */
    public static void add_current_user_info_to_template(Model model){
        UserService userService = UserServiceFactory.getUserService();

        boolean user_is_logged_in = userService.isUserLoggedIn();
        model.addAttribute("user_is_logged_in", user_is_logged_in);
        if (user_is_logged_in) {
            model.addAttribute("user_name", userService.getCurrentUser().getNickname());
            model.addAttribute("my_channel_url", "/user/" + userService.getCurrentUser().getUserId());
        }
    }
}
