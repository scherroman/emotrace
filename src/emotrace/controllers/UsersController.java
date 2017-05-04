package emotrace.controllers;

import emotrace.models.User;
import emotrace.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by romanscher on 4/6/17.
 */
@Controller
public class UsersController {

    // EXTERNAL ROUTES

    public static final int NUM_USERS_PER_PAGE = 16;

    //SERVES A PAGE DISPLAYING ALL DIFFERENT USERS ON SITE
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String users(Model model){
        List<User> users = User.scroll_users(NUM_USERS_PER_PAGE, 0);

        model.addAttribute("users", users);
        LoginService.add_current_user_info_to_template(model);

        return "users";
    }
    
    @RequestMapping(value = "/scroll", method = RequestMethod.GET)
    public String scroll_users(@RequestParam("offset") int offset, Model model) {
        offset = offset * NUM_USERS_PER_PAGE;
        List<User> users = User.scroll_users(NUM_USERS_PER_PAGE, offset);

        model.addAttribute("users", users);

        return "fragment_collections/user_cards";
    }
}
