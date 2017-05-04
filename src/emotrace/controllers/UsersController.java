package emotrace.controllers;

import emotrace.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by romanscher on 4/6/17.
 */
@Controller
public class UsersController {

    // EXTERNAL ROUTES

    //SERVES A PAGE DISPLAYING ALL DIFFERENT USERS ON SITE
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String users(Model model){
        LoginService.add_current_user_info_to_template(model);

        return "users";
    }
}
