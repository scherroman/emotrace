package emotrace.controllers;

import com.google.appengine.repackaged.com.google.gson.Gson;
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
        System.out.println("RETRIEVED POST REQUEST: " + payload);
        Gson gson = new Gson();
        UserJson user = gson.fromJson(payload, UserJson.class);
        System.out.println(user.full_name);
    }

    static class UserJson{
        String full_name;
        String email;

        public String getFull_name()
        {
            return full_name;
        }

        public String getEmail()
        {
            return email;
        }

        public void setFull_name(String full_name)
        {
            this.full_name = full_name;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }
    }
}
