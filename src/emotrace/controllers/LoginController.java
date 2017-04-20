package emotrace.controllers;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import emotrace.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nashahzad on 4/8/17.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    /**
     * If user is logged in add their username to the template
     * otherwise, indicate to template that there is no logged in user
     * @param model ModelMap on which to add the attribute of the user's name
     */
    public static void addUsernameToTemplate(ModelMap model){
        UserService userService = UserServiceFactory.getUserService();
        if(userService.isUserLoggedIn())
            model.addAttribute("name", userService.getCurrentUser().getNickname());
        else
            model.addAttribute("name", "none");
    }

    /**
     * Indicates if there is a user currently logged in or not
     * @return true if there is a logged in user, false if not
     */
    public static boolean isUserLoggedIn(){
        UserService userService = UserServiceFactory.getUserService();
        return userService.isUserLoggedIn();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String login(ModelMap model){
        model.addAttribute("name", "Napoleon");

        return "login";
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String loginPost(@RequestBody String payload){
        try {
            JSONObject jsonObject = new JSONObject(payload);
            String login = jsonObject.getString("login");
            UserService userService = UserServiceFactory.getUserService();
            if(login.equals("yes"))
                return "{\"url\":\"" + userService.createLoginURL(jsonObject.getString("url")) + "\"}";
            else
                return "{\"url\":\"" + userService.createLogoutURL(jsonObject.getString("url")) + "\"}";
        }catch (JSONException ex){
            System.out.println("Error in parsing JSON, in loginPost method in LoginController.");
            return null;
        }

    }

//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public void loginPost(@RequestBody String payload){
//        System.out.println("RETRIEVED POST REQUEST: " + payload);
//        try {
//            JSONObject jsonObject = new JSONObject(payload);
//            User user = new User(null, jsonObject.getString("googleAccount"), jsonObject.getString("name"));
//        }catch (JSONException ex){
//            System.out.println("Exception thrown whilst attempting to parse the JSON in IndexController's loginPost method.");
//        }
//
//    }

//    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
//    public void loginPost(@RequestBody String payload){
//        System.out.println("RETRIEVED POST REQUEST: " + payload);
//    }
}
