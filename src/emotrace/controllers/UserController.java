package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nashahzad on 4/4/17.
 */
@Controller
public class UserController {

    //SERVES A PAGE DISPLAYING ALL CHANNELS ASSOCIATED WITH A SPECIFIC USER
    @RequestMapping(value = "{user_id}", method = RequestMethod.GET)
    public String user(@PathVariable("user_id") String user_id, Model model){
        model.addAttribute("user_id", user_id);
        return "user";
    }
}
