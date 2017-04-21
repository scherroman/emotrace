package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by romanscher on 4/1/17.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(ModelMap model){
        LoginController.add_current_user_info_to_template(model);
        return "index";
    }
}
