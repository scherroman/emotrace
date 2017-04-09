package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by romanscher on 4/1/17.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    //GRABS THE AJAX POST REQUEST FROM THE LOGIN PAGE
    //FORMAT OF PAYLOAD(SEPARATED BY A SPACE):
    //  FIRST_NAME LAST_NAME EMAIL
    //TOKENIZE PAYLOAD IN ELEMENTS
    @RequestMapping(value = "/login/loginPost", method = RequestMethod.POST)
    public void loginPost(@RequestBody String payload){
//        System.out.println("RETRIEVED POST REQUEST: " + payload);
        String[] elements = payload.split(" ");
        System.out.println(elements);
    }
}
