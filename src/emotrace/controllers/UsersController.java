package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by romanscher on 4/6/17.
 */
@Controller
public class UsersController {

    //SERVES A PAGE DISPLAYING ALL DIFFERENT USERS ON SITE
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String users(){
        return "users";
    }
}
