package emotrace.controllers;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by esamudio on 4/6/17.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    /**
     * This method serves a test page that uses Affectica's API
     * @return test.html
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String test(){
        return "test";
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String dataRetrieval(@RequestBody String payload) {
        try {
            JSONObject jsonObject = new JSONObject(payload);
            String joy = jsonObject.getString("joy");
            System.out.print(payload);
            return "";
        } catch(JSONException ex){
            System.out.println("Error in parsing JSON, in /test method in TestController.");
            return null;
        }
    }

}
