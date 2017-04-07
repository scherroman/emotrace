package emotrace.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
