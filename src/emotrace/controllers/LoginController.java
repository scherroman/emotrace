package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nashahzad on 4/8/17.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

//    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
//    public void loginPost(@RequestBody String payload){
//        System.out.println("RETRIEVED POST REQUEST: " + payload);
//    }
}
