package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nashahzad on 4/4/17.
 */
@Controller
@RequestMapping("/base")
public class BaseController {
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String base(){
        return "base";
    }
}
