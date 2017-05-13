package emotrace.controllers;

import emotrace.models.Channel;
import emotrace.models.DisplayVideo;
import emotrace.models.Video;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romanscher on 4/1/17.
 */
@Controller
public class IndexController {

    // EXTERNAL ROUTES

    /**
     * Reroutes user to /videos page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("redirect:/videos");
    }
}
