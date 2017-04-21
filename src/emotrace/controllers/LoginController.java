package emotrace.controllers;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void loginPost(@RequestBody String payload){
        System.out.println("RETRIEVED POST REQUEST: " + payload);
        try {
            JSONObject jsonObject = new JSONObject(payload);
            User user = new User(null, jsonObject.getString("googleAccount"), jsonObject.getString("name"));
        }catch (JSONException ex){
            System.out.println("Exception thrown whilst attempting to parse the JSON in IndexController's loginPost method.");
        }

    }

//    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
//    public void loginPost(@RequestBody String payload){
//        System.out.println("RETRIEVED POST REQUEST: " + payload);
//    }
}
